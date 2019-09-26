package com.jjz.energy.entry;

import java.io.Serializable;

/**
 * @Features: 服务网点的信息
 * @author: create by chenhao on 2019/4/10
 */
public class ShopMarkerBean implements Serializable {
    //网点id
    private String shop_id ;
    //服务网点名称
    private String shop_name ;
    //联系电话
    private String shop_phone ;
    //简介
    private String shop_desc ;
    //地址
    private String shop_address ;
    //经度
    private double shop_lng ;
    //维度
    private double shop_lat ;
    //油价
    private double goods_price ;
    //商品名称
    private String goods_name ;
    //剩余可领取的油
    private double oil_balance ;

    public double getOil_balance() {
        return oil_balance;
    }

    public void setOil_balance(double oil_balance) {
        this.oil_balance = oil_balance;
    }

    public String getShop_desc() {
        return shop_desc == null ? "" : shop_desc;
    }

    public void setShop_desc(String shop_desc) {
        this.shop_desc = shop_desc;
    }

    public String getShop_address() {
        return shop_address == null ? "" : shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getGoods_name() {
        return goods_name == null ? "" : goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public String getShop_id() {
        return shop_id == null ? "" : shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name == null ? "" : shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_phone() {
        return shop_phone == null ? "" : shop_phone;
    }

    public void setShop_phone(String shop_phone) {
        this.shop_phone = shop_phone;
    }

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



}

