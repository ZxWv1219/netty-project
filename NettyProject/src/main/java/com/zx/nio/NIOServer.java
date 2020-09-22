package com.zx.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO非阻塞网络编程案例(server)
 *
 * @author Zx
 * @date 2020/9/18 19:26
 * @modified By:
 */
public class NIOServer {
    public static void main(String[] args) throws Exception {
        //1.服务器ServerSocketChannel
        //2.绑定IP端口,设置非阻塞模式
        //3.获取selector
        //4.将ServerSocketChannel 注册(register)到selector
        //5.监听到selector事件 select 看是否有事件返回
        //6.取到相关事件的selectionKeys
        //7.遍历SelectionKeys生成客户端SocketChannel通道
        //8.将SocketChannel注册到selector中
        //9.监听判断读取事件OP_READ
        //10.从SocketChannel中读取数据
        //11.从集合中移除selectionKey,防止重复操作

        //创建ServerSocketChannel-> SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9898);
        //绑定端口,并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        //设置成非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //获取Selector对象
        Selector selector = Selector.open();
        //把创建ServerSocketChannel注册到selector上 关联事件为accept
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("selectionKey数量:" + selector.keys().size());//1
        while (true) {
            //等待连接事件 1秒 ,如果没有事件发生 返回
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待1秒,无连接");
                continue;
            }
            //获取到相关事件的 SelectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            //通过selectionKeys 反向获取通道
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                //如果事件是accept,生成客户端SocketChannel
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    try {
                        //将通道改成非阻塞模式
                        socketChannel.configureBlocking(false);
                        System.out.println("有客户端接入:" + socketChannel.hashCode());
                        // 将客户端SocketChannel 注册到selector中, 从中获取数据OP_READ
                        // 同时给SocketChannel关联一个buffer
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                        //打印注册后的selectionKey数量
                        System.out.println("selectionKey数量:" + selector.keys().size());//2,3,4...
                    } catch (Exception ex) {
                        selectionKey.cancel();
                        socketChannel.socket().close();
                        socketChannel.close();
                    }
                } else if (selectionKey.isReadable()) {
                    //通过selectionKey 获取到客户端socketChannel,从中读取数据
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    try {
                        //取到attachment 附加参数里的ByteBuffer
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        channel.read(byteBuffer);
                        System.out.println("from 客户端:" + new String(byteBuffer.array()));
                    } catch (Exception ex) {
                        selectionKey.cancel();
                        channel.socket().close();
                        channel.close();
                    }
                }
                //从集合中移除selectionKey,防止重复操作
                keyIterator.remove();
            }

        }


    }
}
