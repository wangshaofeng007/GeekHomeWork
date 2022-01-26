package week3.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public class HttpHeadResponsetFilter implements HttpResponseFilter {

    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("kk","java-1-nio");
    }
}
