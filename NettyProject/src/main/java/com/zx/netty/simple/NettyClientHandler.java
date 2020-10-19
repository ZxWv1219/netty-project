package com.zx.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author Zx
 * @date 2020/9/26 15:16
 * @modified By:
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    //当通道就绪时 触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        System.out.println("Client " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server !!", CharsetUtil.UTF_8));
    }

    //当通道有读取事件时 触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        //转换msg
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("receive server msg : " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("server ipAddress : " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
