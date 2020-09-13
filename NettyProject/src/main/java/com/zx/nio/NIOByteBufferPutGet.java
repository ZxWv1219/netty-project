package com.zx.nio;

import java.nio.ByteBuffer;

/**
 * @author Zx
 * @date 2020/9/13 13:43
 * @modified By:
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        //类型化方式放入数据
        byteBuffer.putInt(100);
        byteBuffer.putInt(9);
        byteBuffer.putChar('哈');
        byteBuffer.putShort((short) 4);

        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());

    }
}
