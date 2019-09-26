package com.jjz.energy.entry;

import java.io.Serializable;

/**
 * @Features:
 * @author: create by chenhao on 2019/7/17
 */
public class VipListInfo implements Serializable {

    /**
     * 会员等级
     */
    private int level_id;
    //会员名称
    private String level_name;
    //会员价格
    private double level_price;


    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public String getLevel_name() {
        return level_name == null ? "" : level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public double getLevel_price() {
        return level_price;
    }

    public void setLevel_price(double level_price) {
        this.level_price = level_price;
    }
}
