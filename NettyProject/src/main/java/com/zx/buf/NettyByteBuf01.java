package com.zx.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author Zx
 * @date 2020/11/11 15:12
 * @modified By:
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {
        //创建一个ByteBuf
        //说明
        //1.创建对象,该对象包含一个数组arr,是一个byte[10]
        //2.netty中的buffer 不需要使用flip进行反转
        // 底层维护了readerIndex和 writerIndex
        // 通过 readerIndex 和 writerIndex 和 capacity 将buffer分成三个区域
        //0--readIndex 已经读取的区域
        //readIndex--writerIndex 可读的区域
        //writerIndex--capacity 可写的区域
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

//        for (int i = 0; i < buffer.capacity(); i++) {
//            System.out.println(buffer.getByte(i));
//        }
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
    }
}
