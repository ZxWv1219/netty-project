package com.zx.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 将本地文件中读取到字符串中
 *
 * @author Zx
 * @date 2020/9/12 16:44
 * @modified By:
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception {
        //思路
        //1 定义一个输入流
        //2 获取一个FileChannel
        //3 定义一个byteBuffer接收数据
        //4 将FileChannel中数据写到byteBuffer中
        //5 将数据字节转成字符串
        File file = new File("d:\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        //获取channel
        FileChannel fileChannel = fileInputStream.getChannel();
        //定义buffer接收数据
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //将FileChannel中数据写到byteBuffer中
        fileChannel.read(byteBuffer);

        //将数据字节转成字符串
        System.out.println(new String(byteBuffer.array()));

//        //切换读取模式
//        byteBuffer.flip();
//        //将数据字节转成字符串
//        if (byteBuffer.hasRemaining()) {
//            System.out.println(new String(byteBuffer.array()));
//        }
        fileInputStream.close();
    }
}
