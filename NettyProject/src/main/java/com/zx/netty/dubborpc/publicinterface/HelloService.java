package com.zx.netty.dubborpc.publicinterface;

/**
 * 服务提供方,消费方都需要
 *
 * @author ：Zx
 * @date ：2020/11/19 20:38
 * @modified By：
 */
public interface HelloService {
    /**
     *
     * @param msg
     * @return
     */
    String hello(String msg);
}
