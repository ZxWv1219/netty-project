package com.zx.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Zx
 * @date 2020/11/16 15:12
 * @modified By:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        System.out.println("从客户端 " + ctx.channel().remoteAddress() + " " + new String(bytes, StandardCharsets.UTF_8));
        System.out.println("从客户端接收到 " + (++this.count));
        ByteBuf buf = Unpooled.copiedBuffer("我是server收到数据了 " + UUID.randomUUID().toString(), StandardCharsets.UTF_8);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
