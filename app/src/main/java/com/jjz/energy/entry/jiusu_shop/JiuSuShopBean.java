package com.jjz.energy.entry.jiusu_shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 久速商家 推荐商家列表和商家分类
 * @author: create by chenhao on 2019/11/9
 */
public class JiuSuShopBean implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }



    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public  class ListBean implements Serializable {


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
        private String id;
        private String cateName;
        private double lng;
        private double lat;
        private String applause_rate;
        private String district;


        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
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
}
