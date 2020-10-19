package com.zx.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Zx
 * @date 2020/9/28 10:22
 * @modified By:
 */
public class TestServer {
    public static void main(String[] args) throws Exception {
        //1.bossGroup 处理连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //2.workerGroup 业务处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器端的启动的实例
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());

            //绑定一个端口并且同步处理,并启动服务
            ChannelFuture channelFuture = serverBootstrap.bind(9898).sync();

            //添加监听
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("端口9898监听成功");
                }
            });

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
