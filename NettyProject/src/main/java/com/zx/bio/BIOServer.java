package com.zx.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一个简单的BIO例子
 *
 * @author Zx
 * @date 2020/9/11 10:01
 * @modified By:
 */
public class BIOServer {
    public static void main(String[] args) throws Exception {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动");
        while (true) {
            System.out.println("线程信息 id=" + Thread.currentThread().getId());
            System.out.println("线程信息 name=" + Thread.currentThread().getName());

            //监听 等待客户端连接
            System.out.println("等待(阻塞)连接.........");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            //创建一个线程,通讯
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //可以和客户端通讯
                    handler(socket);
                }
            });
        }
    }

    /**
     * 与客户端通讯
     *
     * @param socket
     */
    private static void handler(Socket socket) {
        try {
            System.out.println("线程信息 id=" + Thread.currentThread().getId());
            System.out.println("线程信息 name=" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                System.out.println("等待(阻塞)读取数据.........");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println("线程信息 id=" + Thread.currentThread().getId());
                    System.out.println("线程信息 name=" + Thread.currentThread().getName());
                    System.out.println(new String(bytes, 0, read));//输出客户端发送的数据
                } else {
                    break;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("关闭client连接");
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
