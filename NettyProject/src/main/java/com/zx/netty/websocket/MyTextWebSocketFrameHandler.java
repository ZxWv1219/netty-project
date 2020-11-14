package com.zx.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * TextWebSocketFrame 表示一个文本帧(frame)
 *
 * @author Zx
 * @date 2020/11/14 12:57
 * @modified By:
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器接收到消息: " + msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间 " + LocalDateTime.now() + " " + msg.text()));
    }

    /**
     * web 客户端连接后触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id表示唯一值,LongText是唯一的
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asShortText());
        System.out.println("有用户连接 " + LocalDateTime.now() + ctx.channel().remoteAddress());
    }

    /**
     * web客户端断开连接后被调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asShortText());
    }

    /**
     * 通讯异常处理
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught 被调用" + cause.getMessage());
        System.out.println("exceptionCaught 被调用" + ctx.channel().id().asLongText());
        System.out.println("exceptionCaught 被调用" + ctx.channel().id().asShortText());
        ctx.close();
    }
}
