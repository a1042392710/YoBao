package com.jjz.energy.entry.commodity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 商品信息
 * @author: create by chenhao on 2019/10/10
 */
public class GoodsListBean implements Serializable {

    /**
     * 商品列表
     */
    private List<GoodsBean> list;

    public List<GoodsBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<GoodsBean> list) {
        this.list = list;
    }

    public class GoodsBean implements Serializable{


        /**
         * 商品Id
         */
        private int goods_id;
        /**
         * 商品名称
         */
        private String goods_name;
        /**
         * 商品价格
         */
        private  double shop_price;
        /**
         * 商品图片
         */
        private String goods_images;
        /**
         * 多少人喜欢
         */
        private int collect_sum;
        /**
         * 用户名称
         */
        private String nickname;
        /**
         * 用户头像
         */
        private String head_pic;


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

        public double getShop_price() {
            return shop_price;
        }

        public void setShop_price(double shop_price) {
            this.shop_price = shop_price;
        }

        public String getGoods_images() {
            return goods_images == null ? "" : goods_images;
        }

        public void setGoods_images(String goods_images) {
            this.goods_images = goods_images;
        }

        public int getCollect_sum() {
            return collect_sum;
        }

        public void setCollect_sum(int collect_sum) {
            this.collect_sum = collect_sum;
        }

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead_pic() {
            return head_pic == null ? "" : head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }
    }
}
