package com.zx.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * NIO非阻塞网络编程案例(client)
 *
 * @author Zx
 * @date 2020/9/18 20:28
 * @modified By:
 */
public class NIOClient {
    public static void main(String[] args) throws Exception {
        //创建客户端通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞模式
        socketChannel.configureBlocking(false);
        //连接server服务端
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9898);
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("连接中...");
            }
        }
        String str = "hello 你好";
        //创建buffer使用wrap自动获取字节大小
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //将数据写入socketChannel
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}
