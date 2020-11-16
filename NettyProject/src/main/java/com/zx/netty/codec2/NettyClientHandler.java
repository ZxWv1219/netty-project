package com.zx.netty.codec2;

import com.zx.netty.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * @author Zx
 * @date 2020/9/26 15:16
 * @modified By:
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当通道就绪时 触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        switch (random) {
            case 0:
                myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                        .setStudent(MyDataInfo.Student.newBuilder().setId(5).setName("学习1"))
                        .build();
                break;
            case 1:
                myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                        .setWorker(MyDataInfo.Worker.newBuilder().setAge(15).setName("worker1"))
                        .build();
                break;
            default:
                break;
        }

        ctx.writeAndFlush(myMessage);
    }

    /**
     * 当通道有读取事件时 触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //转换msg
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("receive server msg : " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("server ipAddress : " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
