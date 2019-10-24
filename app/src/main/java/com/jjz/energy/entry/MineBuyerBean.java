package com.jjz.energy.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 我买到的
 * @author: create by chenhao on 2019/10/24
 */
public class MineBuyerBean implements Serializable {

    private List<MineBuyerListBean> list ;

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MineBuyerListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<MineBuyerListBean> list) {
        this.list = list;
    }

    public  class MineBuyerListBean implements Serializable{
        /**
         * 商品id
         */
        private int goods_id;
        /**
         * 用户id
         */
        private int user_id;
        /**
         * 商品名称
         */
        private String goods_name;
        /**
         * 原价
         */
        private String market_price;
        /**
         * 现价
         */
        private String goods_price;
        /**
         * 头像
         */
        private String head_pic;
        /*
         * 昵称
         */
        private String nickname;
        /*
         * 商品图片
         */
        private String goods_images;

        /**
         * 订单状态
         */
        private String state;

        /**
         * 订单状态 状态码
         */
        private int status;

        private String order_sn;


        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getOrder_sn() {
            return order_sn == null ? "" : order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getStatus() {
            return status;
        }

        public String getGoods_price() {
            return goods_price == null ? "" : goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getState() {
            return state == null ? "" : state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name == null ? "" : goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getMarket_price() {
            return market_price == null ? "" : market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }



        public String getHead_pic() {
            return head_pic == null ? "" : head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getGoods_images() {
            return goods_images == null ? "" : goods_images;
        }

        public void setGoods_images(String goods_images) {
            this.goods_images = goods_images;
        }


    }

}
