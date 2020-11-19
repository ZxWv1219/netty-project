package com.zx.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author Zx
 * @date 2020/11/19 21:40
 * @modified By:
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    /**
     * 返回结果
     */
    private String result;
    /**
     * 参数
     */
    private String param;

    /**
     * 与服务器连接创建后触发
     * 这个方法第一个被调用
     * (1)
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    /**
     * 接收服务端数据后触发
     * (4)
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        //唤醒等等的线程
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 被代理对象调用, 发送数据给服务器,等待被唤醒  ->wait
     * (3)
     * wait
     * (5)
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(param);
        //进行wait
        wait();
        return result;
    }

    //(2)
    void setParam(String param) {
        this.param = param;
    }

}
