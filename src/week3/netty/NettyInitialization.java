package week3.netty;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class NettyInitialization extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
       ChannelPipeline channelPipeline =  ch.pipeline();
        channelPipeline.addLast(new HttpServerCodec());
        channelPipeline.addLast(new HttpObjectAggregator(1024*1024));
        channelPipeline.addLast(new NettyHandler());
    }
}
