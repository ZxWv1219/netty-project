package com.zx.nio;

import java.nio.IntBuffer;

/**
 * 简单的Buffer案例
 *
 * @author Zx
 * @date 2020/9/11 16:35
 * @modified By:
 */
public class BasicBuffer {
    public static void main(String[] args) {

        //举例说明Buffer的使用

        //创建一个Buffer ,大小为5 即存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向Buffer中存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i);
        }

        //从buffer中读取数据
        //读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
