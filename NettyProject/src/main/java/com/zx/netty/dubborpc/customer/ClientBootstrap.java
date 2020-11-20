package com.zx.netty.dubborpc.customer;

import com.zx.netty.dubborpc.netty.NettyClient;
import com.zx.netty.dubborpc.publicinterface.HelloService;

/**
 * @author Zx
 * @date 2020/11/20 8:59
 * @modified By:
 */
public class ClientBootstrap {

    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) {

        //创建一个消费者
        NettyClient customer = new NettyClient();
        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);

        String hello = service.hello("asd123");
        System.out.println("调用服务的结果=" + hello);
    }
}
