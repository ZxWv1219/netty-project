package com.zx.netty.inboundhandelerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author Zx
 * @date 2020/11/16 15:05
 * @modified By:
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    /**
     * 会根据接收到的数据被调用多次,直到确定数据被读取完
     *
     * @param ctx 上下文对象
     * @param in  入站的ByteBuf
     * @param out 将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println(" MyByteToLongDecoder2 decode 被调用");
        //ReplayingDecoder不需要判断
        out.add(in.readLong());
    }
}
