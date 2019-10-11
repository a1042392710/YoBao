package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features: 提现记录
 * @author: create by chenhao on 2019/4/26
 */
public class WithdrawListBean implements Serializable {



    private String apply_time ;
    private int status ;
    private String statusName ;
    private String typeName ;
    private String commossion_pay ;

    public String getApply_time() {
        return apply_time == null ? "" : apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName == null ? "" : statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTypeName() {
        return typeName == null ? "" : typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCommossion_pay() {
        return commossion_pay == null ? "" : commossion_pay;
    }

    public void setCommossion_pay(String commossion_pay) {
        this.commossion_pay = commossion_pay;
    }
}
