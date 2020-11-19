package com.zx.netty.dubborpc.provider;

import com.zx.netty.dubborpc.publicinterface.HelloService;
import io.netty.util.internal.StringUtil;

/**
 * @author Zx
 * @date 2020/11/19 20:39
 * @modified By:
 */
public class HelloServiceImpl implements HelloService {
    /**
     * 当有消费方调用该方法时,返回一个结果
     *
     * @param msg
     * @return
     */
    @Override
    public String hello(String msg) {
        System.out.println("收费客户端消息=" + msg);
        //根据msg返回不同结果
        if (!StringUtil.isNullOrEmpty(msg)) {
            return "你好客户端,我已经收到你的消息了[" + msg + "]";
        }
        return null;
    }
}
