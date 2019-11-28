package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features: 过期佣金
 * @author: create by chenhao on 2019/4/27
 */
public class ExpireDateBean implements Serializable {

    /**
     * 被推荐人
     */
    private String nickname;

    /**
     * 过期时间
     */
    private long time;

    /**
     * 佣金金额
     */
    private String commission_money;

    /**
     * 佣金状态  timeout 已过期  insufficient 已经过期
     */
    private String status;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getCommission_money() {
        return commission_money == null ? "" : commission_money;
    }

    public void setCommission_money(String commission_money) {
        this.commission_money = commission_money;
    }


}
