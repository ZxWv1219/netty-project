package com.zx.netty.inboundhandelerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Zx
 * @date 2020/11/16 15:20
 * @modified By:
 */
public class MyByteToLongEncoder extends MessageToByteEncoder<Long> {


    /**
     * 编码方法
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println(" MyByteToLongEncoder encode 被调用");
        System.out.println("msg=" + msg);
        out.writeLong(msg);
    }
}
