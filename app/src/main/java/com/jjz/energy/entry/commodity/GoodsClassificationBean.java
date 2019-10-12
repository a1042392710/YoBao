package com.jjz.energy.entry.commodity;

import java.io.Serializable;

/**
 * @Features: 商品分类
 * @author: create by chenhao on 2019/10/10
 */
public class GoodsClassificationBean implements Serializable {
    /**
     * 类别id
     */
    private int id = 0;
    /**
     * 分类名称
     */
    private String mobile_name;
    /**
     * 分类图片
     */
    private String imgage;

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

    public String getImgage() {
        return imgage == null ? "" : imgage;
    }

    public void setImgage(String imgage) {
        this.imgage = imgage;
    }
}
