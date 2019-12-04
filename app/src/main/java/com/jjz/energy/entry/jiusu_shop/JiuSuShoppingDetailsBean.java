package com.jjz.energy.entry.jiusu_shop;

/**
 * @Features: 消费详情
 * @author: create by chenhao on 2019/11/25
 */
public class JiuSuShoppingDetailsBean  {

    /**
     * shop_name : 水果店
     * shop_phone : 17318418967
     * house_number : 231
     * lng : 112.577306
     * lat : 26.924344
     * pay_time : 0
     * order_amount : 0.09
     * total_amount : 0.10
     * order_sn : offline6201912031154442185
     */
    private String shop_name;
    private String shop_phone;
    private String house_number;
    private double lng;
    private double lat;
    private long pay_time;
    private String order_amount;
    private String total_amount;
    private String order_sn;
    private String nickname;
    private String mobile;
    private String head_pic;

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHead_pic() {
        return head_pic == null ? "" : head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
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

    public String getHouse_number() {
        return house_number == null ? "" : house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }


    public long getPay_time() {
        return pay_time;
    }

    public void setPay_time(long pay_time) {
        this.pay_time = pay_time;
    }

    public String getOrder_amount() {
        return order_amount == null ? "" : order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getTotal_amount() {
        return total_amount == null ? "" : total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getOrder_sn() {
        return order_sn == null ? "" : order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }
}
