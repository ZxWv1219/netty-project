package com.zx.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author Zx
 * @date 2020/11/12 8:43
 * @modified By:
 */
public class GroupChatServer {

    /**
     * 监听端口
     */
    private int port;

    public static void main(String[] args) {
        //启动服务端
        new GroupChatServer(7000).run();
    }

    public GroupChatServer(int port) {
        this.port = port;
    }

    public void run() {
        //创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //8个NioEventLoopGroup
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //获取pipeline
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //向pipeline中添加解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //向pipeline中添加编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入业务处理Handler
                            pipeline.addLast(new GroupChatServerHandler());
                        }
                    });


            System.out.println("netty 服务器启动");

            ChannelFuture channelFuture = bootstrap.bind(this.port).sync();
            //监听关闭事件
            channelFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }

    }
}
