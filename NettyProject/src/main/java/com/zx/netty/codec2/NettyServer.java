package com.zx.netty.codec2;

import com.zx.netty.codec.StudentPOJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @author Zx
 * @date 2020/9/26 10:37
 * @modified By:
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {
        //创建BossGroup WorkerGroup ,都是无限循环
        //1.bossGroup 处理连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //2.workerGroup 业务处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建服务器端的启动的实例
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {


            //设置
            serverBootstrap.group(bossGroup, workerGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class) //使用NioServerSocketChannel 做为服务器的通道实例
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) //设置连接保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //创建一个通道测试对象,给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("decoder", new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                            socketChannel.pipeline().addLast(new NettyServerHandler());

                        }
                    }); //给我们的workerGroup 的EventLoop 对应管道的处理器

            System.out.println("server is ready...");

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
