package com.zx.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 将字符串写到本地文件中
 *
 * @author Zx
 * @date 2020/9/12 13:06
 * @modified By:
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception {
        //思路
        //1 定义字符串
        //2 将字符串写入ByteBuffer中
        //3 将buffer写入FileChannel中
        //4 将数据输出到文件中

        String str = "hello world";
        //创建一个输出流->channel
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");

        //通过文件输出流得到一个 FileChannel
        // FileChannel 的实现类为 FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        //切换读写
        byteBuffer.flip();
        //将byteBuffer 写入 fileChannel
        fileChannel.write(byteBuffer);

        //关闭流
        fileOutputStream.close();
    }
}
