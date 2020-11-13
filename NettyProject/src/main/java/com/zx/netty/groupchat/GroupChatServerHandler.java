package com.zx.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zx
 * @date 2020/11/12 9:52
 * @modified By:
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 定义一个channel组,管理所有的channel
     * GlobalEventExecutor.INSTANCE 是全局的事件执行器(单例)
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 表示连接建立,一但连接建立后,将第一个执行
     * 将当前channel加入到channelGroup组中
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //推送给其它在线用户,并加入聊天组
        //该方法会将channelGroup中所有的channel遍历并发送消息,并排队自己
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天 " + sdf.format(new Date()) + "\n");
        channelGroup.add(channel);

        System.out.println("当前channelGroup数量: " + channelGroup.size());
    }

    /**
     * 断开连接
     * channelGroup会自动删除channel
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "离线了" + sdf.format(new Date()) + "\n");
        System.out.println("当前channelGroup数量: " + channelGroup.size());
    }

    /**
     * 表示channel处与活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //提示XXX上线
        System.out.println(ctx.channel().remoteAddress() + "上线了" + sdf.format(new Date()) + "\n");
    }

    /**
     * 表示channel处与非活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //提示XXX离线
        System.out.println(ctx.channel().remoteAddress() + "离线了" + sdf.format(new Date()) + "\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        //根据不同情况,回头不同消息
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                //排队自己,转发消息给其他人
                ch.writeAndFlush("[客户端]" + channel.remoteAddress() + " " + sdf.format(new Date()) + "说: " + msg + "\n");
            } else {
                ch.writeAndFlush("[我] " + sdf.format(new Date()) + " 说: " + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        System.err.println("发生异常了");
//        System.out.println(cause.toString());

        //关闭通道
        ctx.close();
    }
}
