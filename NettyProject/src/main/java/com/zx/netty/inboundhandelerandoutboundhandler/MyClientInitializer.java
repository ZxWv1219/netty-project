package com.zx.netty.inboundhandelerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Zx
 * @date 2020/11/16 15:19
 * @modified By:
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入编码器
        pipeline.addLast("encoder", new MyByteToLongEncoder());
//        pipeline.addLast("decoder", new MyByteToLongDecoder());
        pipeline.addLast("decoder", new MyByteToLongDecoder2());
        pipeline.addLast(new MyClientHandler());
    }
}
