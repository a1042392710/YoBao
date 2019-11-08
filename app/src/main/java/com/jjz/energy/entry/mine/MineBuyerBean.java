package com.jjz.energy.entry.mine;

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
         * 申请售后的id
         */
        private int rec_id;
        /**
         * 商品名称
         */
        private String goods_name;
        /**
         * 用户电话
         */
        private String mobile;
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
        /**
         * 订单编号
         */
        private String order_sn;

        private String return_id;
        /**
         * 售后状态
         */
        private int return_status;

        private String buyer_mobile;
        private String seller_mobile;

        public String getBuyer_mobile() {
            return buyer_mobile == null ? "" : buyer_mobile;
        }

        public void setBuyer_mobile(String buyer_mobile) {
            this.buyer_mobile = buyer_mobile;
        }

        public String getSeller_mobile() {
            return seller_mobile == null ? "" : seller_mobile;
        }

        public void setSeller_mobile(String seller_mobile) {
            this.seller_mobile = seller_mobile;
        }

        public int getReturn_status() {
            return return_status;
        }

        public void setReturn_status(int return_status) {
            this.return_status = return_status;
        }

        public String getReturn_id() {
            return return_id == null ? "" : return_id;
        }

        public void setReturn_id(String return_id) {
            this.return_id = return_id;
        }

        public int getRec_id() {
            return rec_id;
        }

        public void setRec_id(int rec_id) {
            this.rec_id = rec_id;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

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
