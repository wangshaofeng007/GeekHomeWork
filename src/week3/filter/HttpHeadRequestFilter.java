package week3.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HttpHeadRequestFilter implements  HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest request, ChannelHandlerContext ctx) {
        request.headers().set("mao","soul");
    }
}
