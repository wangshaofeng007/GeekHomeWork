package week3.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import week3.filter.HttpHeadRequestFilter;
import week3.filter.HttpHeadResponsetFilter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;


public class NettyHandler extends ChannelInboundHandlerAdapter {
    HttpHeadRequestFilter httpHeadRequestFilter = new HttpHeadRequestFilter();
    HttpHeadResponsetFilter httpHeadResponsetFilter =  new HttpHeadResponsetFilter();
    @Override
    public  void channelReadComplete(ChannelHandlerContext ctx){ctx.close();}

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        try {
            FullHttpRequest request =  (FullHttpRequest)msg;
            String uri = request.getUri();
            if (uri.contains("/test")) {
                handlerTest(request, ctx, "hello,kimmking");
            } else {
                handlerTest(request, ctx, "hello,others");
            }
        }catch (Exception e){
            e.printStackTrace();;
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void handlerTest(FullHttpRequest request,ChannelHandlerContext ctx,String body)  {
        httpHeadRequestFilter.filter(request,ctx);
        FullHttpResponse response = null;
        try {
            String url = "http://localhost:8803";
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-type", "application/html; charset=utf-8");
            httpPost.addHeader("Accept","application/html");
            HttpResponse httpResponse  = httpClient.execute(httpPost);
            String result="";
            if(httpResponse != null){
                if(httpResponse.getStatusLine().getStatusCode()!=200){
                    System.out.println("返回状态非200");
                }
                InputStream inputStream = httpResponse.getEntity().getContent();
                result = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
            }

            String value = result;
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().set("Content-Length", response.content().readableBytes());
            httpHeadResponsetFilter.filter(response);

        }catch (Exception e){
            System.out.println("处理出错:"+e.getMessage());
            response  = new DefaultFullHttpResponse(HTTP_1_1,NO_CONTENT);
        }finally {
            if(request!=null){
//                if (!HttpUtil.isKeepAlive(request)) {
//                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//                }else {
//                    response.headers().set(CONNECTION,KEEP_ALIVE);
//                    ctx.write(response);
//                }
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                ctx.flush();
            }
        }
    }
}
