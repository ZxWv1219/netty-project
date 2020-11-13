package com.zx.netty.heartbeat;

import com.zx.netty.groupchat.GroupChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Zx
 * @date 2020/11/12 15:08
 * @modified By:
 */
public class MyServer {

    public static void main(String[] args) {

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
                    //增加日志处理器
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //获取pipeline
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            //加入一个netty 提供IdleStateHandler
                            /**
                             * readerIdleTime 表示多长时间没有读,就会发送一个心跳检测包检测是否连接
                             * writerIdleTime 表示多长时间没有写,就会发送一个心跳检测包检测是否连接
                             * allIdleTime 表示多长时间没有写读,就会发送一个心跳检测包检测是否连接
                             * 当 IdleStateEvent 触发后,就会传递给管道的下一个handler处理
                             * 通过回调触发下一个handler的 userEventTrigger,在该方法中处理
                             */
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            //todo 加入一个对空闲检测进一步处理业务的handler(自定义)
                            pipeline.addLast(new MyServerHandler());
                        }
                    });


            System.out.println("netty 服务器启动");

            ChannelFuture channelFuture = bootstrap.bind(7000).sync();
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
