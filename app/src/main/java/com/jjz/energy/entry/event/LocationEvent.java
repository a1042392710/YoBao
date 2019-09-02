package com.jjz.energy.entry.event;

/**
 * @Features: 定位信息
 * @author: create by chenhao on 2019/9/2
 */
public class LocationEvent  {


    public String getEventMsg() {
        return eventMsg == null ? "" : eventMsg;
    }

    /**
     * 需要传递的定位的消息  目前是市名称
     */
    private String eventMsg;

    public LocationEvent(String msg) {
        this.eventMsg = msg.replace("市","");
    }


}
