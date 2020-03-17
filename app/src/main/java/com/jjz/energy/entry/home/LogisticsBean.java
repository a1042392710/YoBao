package com.jjz.energy.entry.home;

import java.io.Serializable;

/**
 * @Features: 物流
 * @author: create by chenhao on 2020/3/16
 */
public class LogisticsBean implements Serializable {


    /**
     * start_time : 1584352800
     * start_city : 长沙市
     * start_address : 湖南省长沙市天心区湘府西路
     * start_lng : 112.98284
     * start_lat : 28.10985
     * end_city : 长沙市
     * end_address : 湖南省长沙市天心区杉木冲路
     * add_time : 2020-03-16 14:32:35
     * shipper : 付弟弟
     * shipper_phone : 13576102823
     * weight : 嘟嘟
     * goods_size : 112312
     * price : 79464.00
     */

    private long start_time;
    private String start_city;
    private String goods_name;
    private String start_address;
    private double start_lng;
    private double start_lat;
    private double end_lng;
    private double end_lat;
    private String end_city;
    private String end_address;
    private long add_time;
    private String shipper;
    private String shipper_phone;
    private String weight;
    private String goods_size;
    private String price;
    private String id;
    private String start_poiname;
    private String end_poiname;
    private String goods_price;
    private String caption;
    private String consignee;
    private String consignee_phone;
    //用于导航
    private double start_gcj_lng, start_gcj_lat;
    private double end_gcj_lng, end_gcj_lat;


    public double getStart_gcj_lng() {
        return start_gcj_lng;
    }

    public void setStart_gcj_lng(double start_gcj_lng) {
        this.start_gcj_lng = start_gcj_lng;
    }

    public double getStart_gcj_lat() {
        return start_gcj_lat;
    }

    public void setStart_gcj_lat(double start_gcj_lat) {
        this.start_gcj_lat = start_gcj_lat;
    }

    public double getEnd_gcj_lng() {
        return end_gcj_lng;
    }

    public void setEnd_gcj_lng(double end_gcj_lng) {
        this.end_gcj_lng = end_gcj_lng;
    }

    public double getEnd_gcj_lat() {
        return end_gcj_lat;
    }

    public void setEnd_gcj_lat(double end_gcj_lat) {
        this.end_gcj_lat = end_gcj_lat;
    }

    public String getConsignee() {
        return consignee == null ? "" : consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee_phone() {
        return consignee_phone == null ? "" : consignee_phone;
    }

    public void setConsignee_phone(String consignee_phone) {
        this.consignee_phone = consignee_phone;
    }

    public String getCaption() {
        return caption == null ? "" : caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getGoods_price() {
        return goods_price == null ? "" : goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_name() {
        return goods_name == null ? "" : goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getStart_poiname() {
        return start_poiname == null ? "" : start_poiname;
    }

    public void setStart_poiname(String start_poiname) {
        this.start_poiname = start_poiname;
    }

    public String getEnd_poiname() {
        return end_poiname == null ? "" : end_poiname;
    }

    public void setEnd_poiname(String end_poiname) {
        this.end_poiname = end_poiname;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getStart_lng() {
        return start_lng;
    }

    public void setStart_lng(double start_lng) {
        this.start_lng = start_lng;
    }

    public double getStart_lat() {
        return start_lat;
    }

    public void setStart_lat(double start_lat) {
        this.start_lat = start_lat;
    }

    public double getEnd_lng() {
        return end_lng;
    }

    public void setEnd_lng(double end_lng) {
        this.end_lng = end_lng;
    }

    public double getEnd_lat() {
        return end_lat;
    }

    public void setEnd_lat(double end_lat) {
        this.end_lat = end_lat;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public String getStart_city() {
        return start_city == null ? "" : start_city;
    }

    public void setStart_city(String start_city) {
        this.start_city = start_city;
    }

    public String getStart_address() {
        return start_address == null ? "" : start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public String getEnd_city() {
        return end_city == null ? "" : end_city;
    }

    public void setEnd_city(String end_city) {
        this.end_city = end_city;
    }

    public String getEnd_address() {
        return end_address == null ? "" : end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public String getShipper() {
        return shipper == null ? "" : shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getShipper_phone() {
        return shipper_phone == null ? "" : shipper_phone;
    }

    public void setShipper_phone(String shipper_phone) {
        this.shipper_phone = shipper_phone;
    }

    public String getWeight() {
        return weight == null ? "" : weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGoods_size() {
        return goods_size == null ? "" : goods_size;
    }

    public void setGoods_size(String goods_size) {
        this.goods_size = goods_size;
    }

    public String getPrice() {
        return price == null ? "" : price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
