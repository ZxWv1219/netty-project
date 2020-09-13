package com.zx.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用FileChannel read write 拷贝文件
 *
 * @author Zx
 * @date 2020/9/13 12:00
 * @modified By:
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {

        //思路
        //1 将文件内容读到buffer中
        //2 将读取到的内容写到FileChannel中

//        method01();

        method02();
    }

    private static void method01() throws Exception {
        //读取文件+++++++++++++++++++
        File file = new File("1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        //获取input通道输入
        FileChannel inputStreamChannel = fileInputStream.getChannel();

        //定义buffer接收数据
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //读取通道中的数据到buffer中
        inputStreamChannel.read(byteBuffer);

        inputStreamChannel.close();

        //写入文件+++++++++++++++++++
        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");

        //获取output输出通道
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        //切换读取模式
        byteBuffer.flip();

        //将buffer写入到通道中
        outputStreamChannel.write(byteBuffer);

        outputStreamChannel.close();
    }


    private static void method02() throws Exception {
        //读取文件+++++++++++++++++++
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        //获取input通道输入
        FileChannel inputStreamChannel = fileInputStream.getChannel();

        //写入文件+++++++++++++++++++
        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        //获取output输出通道
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        //定义buffer接收数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

//        //读取通道中的数据到buffer中
//        while (inputStreamChannel.read(byteBuffer) != -1) {
//            //切换读取模式
//            byteBuffer.flip();
//            //将buffer写入到通道中
//            outputStreamChannel.write(byteBuffer);
//            byteBuffer.clear();
//        }

        //读取通道中的数据到buffer中
        while (true) {
            int read = inputStreamChannel.read(byteBuffer);
            System.out.println(read);
            if (read == -1) break;
            //切换读取模式
            byteBuffer.flip();
            //将buffer写入到通道中
            outputStreamChannel.write(byteBuffer);
            //write过后limit == position 调用clear
            byteBuffer.clear();
        }

        inputStreamChannel.close();
        outputStreamChannel.close();
    }
}
