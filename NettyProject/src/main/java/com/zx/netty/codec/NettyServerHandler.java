package com.zx.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义Handler
 *
 * @author Zx
 * @date 2020/9/26 11:06
 * @modified By:
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    //读取通道数据(读取客户端发送消息)

    /**
     * @param ctx 上下文对象 ,含有管道pipeline ,通道channel, 地址
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        System.out.println("server ctx = " + ctx);
        //转换msg
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("client send msg : " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("Client ipAddress : " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);

        //对发送的数据进行编码
        //将数据写入缓冲并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端", CharsetUtil.UTF_8));
    }

    /**
     * 发关异常,关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
