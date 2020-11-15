package com.zx.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @author Zx
 * @date 2020/11/11 16:14
 * @modified By:
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        //创建
        ByteBuf buf = Unpooled.copiedBuffer("hello world 1", StandardCharsets.UTF_8);
        //使用
        if (buf.hasArray()) {
            byte[] array = buf.array();

            //转成字符串
            System.out.println(new String(array, StandardCharsets.UTF_8));
            System.out.println("buf=" + buf);
        }
    }
}
