package com.jjz.energy.entry.order;

import java.io.Serializable;

/**
 * @Features: 快递公司
 * @author: create by chenhao on 2019/10/10
 */
public class ExpressCompanyBean implements Serializable {
    /**
     * 类别id
     */
    private int id = 0;
    /**
     * 分类名称
     */
    private String mobile_name;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile_name() {
        return mobile_name == null ? "" : mobile_name;
    }

    public void setMobile_name(String mobile_name) {
        this.mobile_name = mobile_name;
    }
}
