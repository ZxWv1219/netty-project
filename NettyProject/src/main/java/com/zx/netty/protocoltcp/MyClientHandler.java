package com.zx.netty.protocoltcp;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;


/**
 * @author Zx
 * @date 2020/11/16 15:28
 * @modified By:
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println(new String(content, StandardCharsets.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 10; i++) {
            String msg = "哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈";
            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
            int length = bytes.length;

            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(bytes);

            ctx.writeAndFlush(messageProtocol);
        }
    }
}
