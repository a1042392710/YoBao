package com.jjz.energy.entry.jiusu_shop;

import com.jjz.energy.entry.commodity.ShopCommodityListBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 久速商家 个人主页
 * @author: create by chenhao on 2019/11/11
 */
public class ShopHomePageBean implements Serializable {

    private List<ShopCommodityListBean> commodityList;
    /**
     * shop_name : 水果店
     * shop_phone : 17318418966
     * shop_desc : 周一至周日 07:00-次日
     * poiname : 乡滋味住家菜
     * poiaddress : 湖南省衡阳市蒸湘区长丰大道蒸湘雅郡4-5号门面
     * house_number : 231
     * lng : 112.577306
     * lat : 26.924344
     * avg_tax : 30
     * applause_rate : 90
     * header_img : http://qiniu.jjznewenergy.com/2019_11_16_5dcfc8f492d3e,http://qiniu
     * .jjznewenergy.com/2019_11_16_5dcfc91b4b9a3,http://qiniu.jjznewenergy
     * .com/2019_11_16_5dcfc9155b7d0
     * discount : 0.90
     * enabled : 1
     */

    private String shop_name;
    private String shop_phone;
    private String shop_desc;
    private String poiname;
    private String poiaddress;
    private String lng;
    private String lat;
    private int avg_tax;
    private int applause_rate;
    private int id;
    private String header_img;
    private float rebate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ShopCommodityListBean> getCommodityList() {
        if (commodityList == null) {
            return new ArrayList<>();
        }
        return commodityList;
    }

    public void setCommodityList(List<ShopCommodityListBean> commodityList) {
        this.commodityList = commodityList;
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

    public String getLng() {
        return lng == null ? "" : lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat == null ? "" : lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public int getAvg_tax() {
        return avg_tax;
    }

    public void setAvg_tax(int avg_tax) {
        this.avg_tax = avg_tax;
    }

    public int getApplause_rate() {
        return applause_rate;
    }

    public void setApplause_rate(int applause_rate) {
        this.applause_rate = applause_rate;
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
}
