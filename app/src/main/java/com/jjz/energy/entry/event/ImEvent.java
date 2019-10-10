package com.jjz.energy.entry.event;

import cn.jpush.im.android.api.event.MessageEvent;

/**
 * @Features: 极光Im eventbus所传输的类
 * @author: create by chenhao on 2019/10/10
 */
public class ImEvent {

    /**
     * 新消息
     */
    public static final String NEW_IM = "new_im";
    /**
     * 刷新消息
     */
    public static final String REFRESH_IM = "refresh_im";
    /**
     * IM消息类型
     */
    private String imType;
    /**
     * 需要传递的消息
     */
    private MessageEvent eventMsg;

    public String getImType() {
        return imType == null ? "" : imType;
    }

    public MessageEvent getEventMsg() {
        return eventMsg;
    }

    public ImEvent(String type) {
        this.imType = type;
    }

    public ImEvent(String type, MessageEvent event) {
        this.imType = type;
        this.eventMsg = event;
    }

}
