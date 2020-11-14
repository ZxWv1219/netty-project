package com.zx.netty.websocket;

import com.zx.netty.heartbeat.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Zx
 * @date 2020/11/13 16:11
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
                            //加入HTTP编码/解码器
                            pipeline.addLast(new HttpServerCodec());
                            //以块方式写
                            pipeline.addLast(new ChunkedWriteHandler());
                            //http数据在传输过程中是分段的,所以HttpObjectAggregator就是可以将多个段聚合起来
                            //当浏览器发送大量数据时,就会发出多次http请求
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            //websocket 的数据是以帧(frame)形式传递的
                            //可以看到webSocketFrame下面有6个子类
                            //浏览器请求 ws://localHost:7000/hello
                            //WebSocketServerProtocolHandler核心功能是将http转成ws协议 保持长连接
                            //请求状态码101
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            //加入处理业务的handler(自定义)
                            pipeline.addLast(new MyTextWebSocketFrameHandler());
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
