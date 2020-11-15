package com.zx.netty.codec;

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
        //发送一人student对象到服务器
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("张三").build();
        ctx.writeAndFlush(student);
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
