package com.zx.netty.protocoltcp;

/**
 * 协议包
 * @author Zx
 * @date 2020/11/17 15:28
 * @modified By:
 */
public class MessageProtocol {
    private int len;
    private byte[] content;

    public byte[] getContent() {
        return content;
    }

    public MessageProtocol setContent(byte[] content) {
        this.content = content;
        return this;
    }

    public int getLen() {
        return len;
    }

    public MessageProtocol setLen(int len) {
        this.len = len;
        return this;
    }
}
