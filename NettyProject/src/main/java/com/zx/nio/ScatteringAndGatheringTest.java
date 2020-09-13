package com.zx.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering: 将数据写入到Buffer时, 可以采用Buffer数据依次写入[分散]
 * Gathering: 从Buffer读取数据时, 可以采用Buffer数据依次读取[分散]
 *
 * @author Zx
 * @date 2020/9/13 14:17
 * @modified By:
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception {

        //使用ServerSocketChannel 和 SocketChannel

        server();
    }

    private static void server() throws Exception {
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9898);

        //绑定端口,并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();

        //循环读取
        int msgLength = 8; //允许从客户端接收8个字节
        while (true) {
            int byteRead = 0;
            while (byteRead < msgLength) {
                long read = socketChannel.read(byteBuffers);
                byteRead += read;//累计读取到的字节数
                System.out.println("byteRead = " + byteRead);
                //使用流打印,看看当前buffer 的 position 与 limit
//                Arrays.stream(byteBuffers)
//                        .map(byteBuffer -> "position = " + byteBuffer.position()
//                                + ", limit = " + byteBuffer.limit())
//                        .forEach(System.out::println);

                for (ByteBuffer byteBuffer : byteBuffers) {
                    System.out.println("position = " + byteBuffer.position() + ", limit = " + byteBuffer.limit());
                    System.out.println();
                    byteBuffer.flip();
                }

                //将数据 回显给客户端
                long byteWrite = 0;
//                while (byteWrite < msgLength) {
//                    long write = socketChannel.write(byteBuffers);
//                    byteWrite += write;
//                }

                //清除一下数据
                Arrays.asList(byteBuffers).forEach(ByteBuffer::clear);

                System.out.println("byteRead = " + byteRead + ", byteWrite = " + byteWrite + ", msgLength = " + msgLength);

            }
        }
    }
}
