package com.zx.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * 使用通道transferFrom 拷贝文件
 *
 * @author Zx
 * @date 2020/9/13 13:30
 * @modified By:
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception {
        //读取文件+++++++++++++++++++
        FileInputStream fileInputStream = new FileInputStream("1.jpg");
        //获取input通道输入
        FileChannel inputStreamChannel = fileInputStream.getChannel();

        //写入文件+++++++++++++++++++
        FileOutputStream fileOutputStream = new FileOutputStream("2.jpg");
        //获取output输出通道
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        //复制通道数据
        outputStreamChannel.transferFrom(inputStreamChannel, 0, inputStreamChannel.size());

        fileOutputStream.close();
        fileInputStream.close();
    }
}
