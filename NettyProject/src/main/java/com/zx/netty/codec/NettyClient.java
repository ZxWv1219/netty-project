package com.zx.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @author Zx
 * @date 2020/9/26 11:48
 * @modified By:
 */
public class NettyClient {
    public static void main(String[] args) throws Exception {
        //客户端需要一个事件循环组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        //创建客户端启动实例
        Bootstrap bootstrap = new Bootstrap();

        try {


            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class) // 设置客户端通道实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("encoder", new ProtobufEncoder());
                            socketChannel.pipeline().addLast(new NettyClientHandler()); //设置处理器
                        }
                    });

            System.out.println("Client is ok...");

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9898).sync();

            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
