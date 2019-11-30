package com.jjz.energy.entry.jiusu_shop;

import java.io.Serializable;

/**
 * @Features: 久速商家 推荐商家列表和商家分类
 * @author: create by chenhao on 2019/11/9
 */
public class JiuSuShop implements Serializable {

    /**
     * shop_name : 水果店
     * avg_tax : 30  人均消费
     * cateName : 服装
     * lng : 112.577306
     * lat : 26.924344
     * applause_rate : 好评率
     * districtName :  区名
     */
    private String shop_img;
    private String shop_name;
    private int avg_tax;
    private int id;
    private String cateName;
    private double lng;
    private double lat;
    private String applause_rate;
    private String district;
    private String shop_phone;
    private String shop_desc;
    private String poiname;
    private String poiaddress;
    private String header_img;
    private float rebate;
    private float pay_points;

    public float getPay_points() {
        return pay_points;
    }

    public void setPay_points(float pay_points) {
        this.pay_points = pay_points;
    }

    public String getShop_phone() {
        return shop_phone == null ? "" : shop_phone;
    }

    public void setShop_phone(String shop_phone) {
        this.shop_phone = shop_phone;
    }

    public String getShop_desc() {
        return shop_desc == null ? "" : shop_desc;
    }

    public void setShop_desc(String shop_desc) {
        this.shop_desc = shop_desc;
    }

    public String getPoiname() {
        return poiname == null ? "" : poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
    }

    public String getPoiaddress() {
        return poiaddress == null ? "" : poiaddress;
    }

    public void setPoiaddress(String poiaddress) {
        this.poiaddress = poiaddress;
    }

    public String getHeader_img() {
        return header_img == null ? "" : header_img;
    }

    public void setHeader_img(String header_img) {
        this.header_img = header_img;
    }

    public float getRebate() {
        return rebate;
    }

    public void setRebate(float rebate) {
        this.rebate = rebate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrict() {
        return district == null ? "" : district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getShop_img() {
        return shop_img == null ? "" : shop_img;
    }

    public void setShop_img(String shop_img) {
        this.shop_img = shop_img;
    }

    public String getShop_name() {
        return shop_name == null ? "" : shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getAvg_tax() {
        return avg_tax;
    }

    public void setAvg_tax(int avg_tax) {
        this.avg_tax = avg_tax;
    }

    public String getCateName() {
        return cateName == null ? "" : cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
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

    public String getApplause_rate() {
        return applause_rate == null ? "" : applause_rate;
    }

    public void setApplause_rate(String applause_rate) {
        this.applause_rate = applause_rate;
    }

}
