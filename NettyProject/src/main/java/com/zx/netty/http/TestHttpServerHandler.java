package com.zx.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 说明
 * 1.SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 的子类
 * 2.HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
 *
 * @author Zx
 * @date 2020/9/28 10:23
 * @modified By:
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端数据时 触发
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {

        //判断msg 是不是httpRequest请求
        if (httpObject instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) httpObject;
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求favicon.ico 不做处理");
                return;
            }

            System.out.println("httpObject 类型: " + httpObject.getClass());
            System.out.println("客户端地址: " + channelHandlerContext.channel().remoteAddress());


            //回复信息给浏览器 (http协议)
            ByteBuf buf = Unpooled.copiedBuffer("hello http!", CharsetUtil.UTF_8);

            //http响应
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    buf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());

            channelHandlerContext.writeAndFlush(response);

        }
    }
}
