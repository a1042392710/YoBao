package com.jjz.energy.entry.event;

/**
 * @Features:
 * @author: create by chenhao on 2019/4/3
 */
public class MainEvent {

    /**
     * 跳个人中心
     */
    public static final String GO_MINE = "mine";
    /**
     * 跳发现
     */
    public static final String GO_HOT = "hot";
    /**
     * 跳首页
     */
    public static final String GO_HOME = "home";

    public String getEventMsg() {
        return eventMsg == null ? "" : eventMsg;
    }

    /**
     * 需要传递的消息
     */
    private String eventMsg;

    public MainEvent(String msg) {
        this.eventMsg = msg;
    }

    public MainEvent() {
        eventMsg = GO_HOME;
    }




}
