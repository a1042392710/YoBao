package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features: 赠品油获取记录
 * @author: create by chenhao on 2019/4/26
 */
public class YoCardReceiveListBean implements Serializable {



    private long change_time ;



    private int log_id ;

    public double getOil_balance() {
        return oil_balance;
    }

    public void setOil_balance(double oil_balance) {
        this.oil_balance = oil_balance;
    }

    private double oil_balance ;
    private String desc ;

    public long getChange_time() {
        return change_time;
    }

    public void setChange_time(long change_time) {
        this.change_time = change_time;
    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }


    public String getDesc() {
        return desc == null ? "" : desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
