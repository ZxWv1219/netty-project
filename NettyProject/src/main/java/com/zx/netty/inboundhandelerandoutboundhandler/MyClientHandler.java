package com.zx.netty.inboundhandelerandoutboundhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Zx
 * @date 2020/11/16 15:28
 * @modified By:
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("从服务器读到数据 " + ctx.channel().remoteAddress() + " " + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 发送数据");
        ctx.writeAndFlush(5500000000000000000L);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("44444444654s65f4asdfasdfasdfasd", StandardCharsets.UTF_8));
    }
}
