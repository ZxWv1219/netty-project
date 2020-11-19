package com.zx.netty.dubborpc.netty;

import com.zx.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Zx
 * @date 2020/11/19 21:17
 * @modified By:
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息并调用服务
        System.out.println("msg=" + msg);
        //客户端在调用服务器的api时,我们需要定义一个协议
        //比如我们要求,每次发消息都必须以自定义格式传
        if (msg.toString().startsWith("HelloService#hello#")) {
            String content = msg.toString().substring(msg.toString().lastIndexOf("#") + 1);
            String result = new HelloServiceImpl().hello(content);
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
