package com.zx.netty.inboundhandelerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Zx
 * @date 2020/11/16 14:57
 * @modified By:
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("encoder", new MyByteToLongEncoder());
        pipeline.addLast("decoder", new MyByteToLongDecoder());
        pipeline.addLast(new MyServerHandler());
    }
}
