package com.zx.netty.http;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author Zx
 * @date 2020/9/28 10:25
 * @modified By:
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //添加管道处理器
        ChannelPipeline pipeline = socketChannel.pipeline();
        //netty 提供的HTTP编码解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //添加自定义编码器
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
    }
}
