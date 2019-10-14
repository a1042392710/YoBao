package com.jjz.energy.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhao 2018/12/20
 * @function 我的收藏列表 可共用为 商品列表
 */
public class LikeGoodsBean implements Serializable {


    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    private List<ListBean> list;



    public  class ListBean implements Serializable{


        /**
         * 商品id
         */
        private int goods_id;
        /**
         * 收藏id
         */
        private int collect_id;
        /**
         * 评论数
         */
        private int comment_num;
        /**
         * 商品名称
         */
        private String goods_name;
        /**
         * 发布时间
         */
        private long on_time;
        /**
         * 原价
         */
        private String market_price;
        /**
         * 现价
         */
        private String shop_price;
        /**
         * 上架状态 1 上架  0下架
         */
        private int is_on_sale;
        /**
         * 点击数
         */
        private int click_count;
        /**
         * 收藏数
         */
        private int collect_sum;
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

        public String getGoods_images() {
            return goods_images == null ? "" : goods_images;
        }

        public void setGoods_images(String goods_images) {
            this.goods_images = goods_images;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getCollect_id() {
            return collect_id;
        }

        public void setCollect_id(int collect_id) {
            this.collect_id = collect_id;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public String getGoods_name() {
            return goods_name == null ? "" : goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public long getOn_time() {
            return on_time;
        }

        public void setOn_time(long on_time) {
            this.on_time = on_time;
        }

        public String getMarket_price() {
            return market_price == null ? "" : market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getShop_price() {
            return shop_price == null ? "" : shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public int getIs_on_sale() {
            return is_on_sale;
        }

        public void setIs_on_sale(int is_on_sale) {
            this.is_on_sale = is_on_sale;
        }

        public int getClick_count() {
            return click_count;
        }

        public void setClick_count(int click_count) {
            this.click_count = click_count;
        }

        public int getCollect_sum() {
            return collect_sum;
        }

        public void setCollect_sum(int collect_sum) {
            this.collect_sum = collect_sum;
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
    }
}
