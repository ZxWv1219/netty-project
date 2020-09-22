package com.zx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author Zx
 * @date 2020/9/21 9:53
 * @modified By:
 */
public class GroupChatServer {
    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 9898;

    public GroupChatServer() {
        try {
            //设置选择器
            selector = Selector.open();
            //设置服务端通道
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //将通道设置成非阻塞模式
            listenChannel.configureBlocking(false);
            //将通道注册到selector中
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {//有事件

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //获取selector中的selectionKey
                        SelectionKey selectionKey = iterator.next();
                        //是否为连接事件
                        if (selectionKey.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            //设置成非阻塞模式
                            socketChannel.configureBlocking(false);
                            //将客户端通道注册到selector中
                            socketChannel.register(selector, SelectionKey.OP_READ);

                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }
                        //是否为读取事件
                        if (selectionKey.isReadable()) {
                            readClient(selectionKey);
                        }

                        //从集合中移除selectionKey,防止重复操作
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待连接...");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void readClient(SelectionKey selectionKey) {
        //取到channel
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        try {
            //创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = channel.read(byteBuffer);
            if (count > 0) {
                String msg = channel.getRemoteAddress().toString().substring(1) + "说 : "
                        + new String(byteBuffer.array());
                System.out.println("from 客户端:" + msg);

                //转发消息给其它客户端
                sendMsgToOtherClient(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                selectionKey.cancel();//取消注册
                channel.socket().close();
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }

    private void sendMsgToOtherClient(String msg, SocketChannel selfChannel) throws IOException {
        System.out.println("服务器转发消息中...");
        //遍历所有注册到selector 上的 socketChannel 并排除自己
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel && channel != selfChannel) {
                SocketChannel targetChannel = (SocketChannel) channel;
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                //将数据写到通道中
                targetChannel.write(byteBuffer);
            }
        }

    }

    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.listen();
    }
}
