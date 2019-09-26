package com.jjz.energy.entry;

import java.io.Serializable;

/**
 * @Features: 首页地图点位的Bean
 * @author: create by chenhao on 2019/4/9
 */
public class MapMarkerBean implements Serializable {

    /**
     * shop_id : 1
     * shop_name : 红谷滩服务网点
     * shop_lng : 115.860969
     * shop_lat : 28.691138
     */

    private int shop_id;
    private String shop_name;
    private double shop_lng;
    private double shop_lat;

    public double getShop_lng() {
        return shop_lng;
    }

    public void setShop_lng(double shop_lng) {
        this.shop_lng = shop_lng;
    }

    public double getShop_lat() {
        return shop_lat;
    }

    public void setShop_lat(double shop_lat) {
        this.shop_lat = shop_lat;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name == null ? "" : shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }



}
