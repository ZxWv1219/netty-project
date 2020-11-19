package com.zx.netty.dubborpc.provider;

import com.zx.netty.dubborpc.netty.NettyServer;

/**
 * 会启动一个服务提供者,就是NettyServer
 *
 * @author Zx
 * @date 2020/11/19 20:46
 * @modified By:
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 7000);
    }
}
