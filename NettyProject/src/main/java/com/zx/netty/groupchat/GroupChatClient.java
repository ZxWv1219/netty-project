package com.zx.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author Zx
 * @date 2020/11/12 10:45
 * @modified By:
 */
public class GroupChatClient {
    private final String host;
    private final int port;

    public GroupChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        new GroupChatClient("127.0.0.1", 7000).run();
    }

    public void run() {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sch) throws Exception {
                            //取到pipeline
                            ChannelPipeline pipeline = sch.pipeline();
                            //加入编码与解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入业务Handler
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });


            //启动客户端
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            System.out.println("客户端已启动: " + channel.localAddress());
            //创建扫描器,给用户输入
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String content = scanner.nextLine();
                channel.writeAndFlush(content + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
