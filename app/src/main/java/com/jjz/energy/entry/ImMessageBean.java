package com.jjz.energy.entry;

import cn.jpush.im.android.api.model.Message;

/**
 * @Features: 聊天记录
 * @author: create by chenhao on 2019/10/8
 */
public class ImMessageBean  {

    /**
     * 消息发送成功
     */
    public static final int SEND_MESSAGE_SUC = 1;
    /**
     * 消息发送失败
     */
    public static final int SEND_MESSAGE_FAIL= -1;
    /**
     * 消息开始发送
     */
    public static final int SEND_MESSAGE_START = 0;

    public ImMessageBean(int messageStatus, Message message) {
        this.messageStatus = messageStatus;
        this.message = message;
    }
    /**
     * 消息发送状态 0 开始发送  1 发送成功  -1 发送失败
     */
    private int messageStatus = 1;

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * 消息内容
     */
    private Message message;


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
