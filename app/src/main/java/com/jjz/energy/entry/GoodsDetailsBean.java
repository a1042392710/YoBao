package com.jjz.energy.entry;

import java.io.Serializable;

/**
 * @Features: 商品详情数据
 * @author: create by chenhao on 2019/10/11
 */
public class GoodsDetailsBean implements Serializable {


    private BuyerInfoBean buyer_info;
    private GoodsInfoBean goods_info;
    private SellerInfoBean seller_info;

    public BuyerInfoBean getBuyer_info() { return buyer_info;}

    public void setBuyer_info(BuyerInfoBean buyer_info) { this.buyer_info = buyer_info;}

    public GoodsInfoBean getGoods_info() { return goods_info;}

    public void setGoods_info(GoodsInfoBean goods_info) { this.goods_info = goods_info;}

    public SellerInfoBean getSeller_info() { return seller_info;}

    public void setSeller_info(SellerInfoBean seller_info) { this.seller_info = seller_info;}

    public  class BuyerInfoBean {
        /**
         * head_pic : http://qiniu.jjznewenergy.com/2019_10_07_62db17546
         * nickname : 爱唱跳rap的彭于晏
         * user_id : 6
         */

        private String head_pic;
        private String nickname;
        private int user_id;

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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }

    public  class GoodsInfoBean {
        /**
         * click_count : 0
         * collect_sum : 47
         * goods_id : 240
         * goods_images : http://qiniu.jjznewenergy.com/2019_10_07_62db17546,http://qiniu
         * .jjznewenergy.com/2019_10_07_62db17546,http://qiniu.jjznewenergy
         * .com/2019_10_07_62db17546,http://qiniu.jjznewenergy.com/2019_10_07_62db17546,
         * http://qiniu.jjznewenergy.com/2019_10_07_62db17546,http://qiniu.jjznewenergy
         * .com/2019_10_07_62db17546
         * goods_name : 翻云覆雨血液系统
         * is_mnh : 1
         * market_price : 666.00
         * mobile_content : ，句习惯第一眼第一第一
         * shop_price : 555.00
         * user_id : 6
         */

        private int click_count;
        private int collect_sum;
        private int goods_id;
        //图片列表
        private String goods_images;
        //商品标题
        private String goods_name;
        //是否全新
        private int is_mnh;
        //原价
        private String market_price;
        //详情
        private String mobile_content;
        //现价
        private String shop_price;
        //邮费
        private int shopping_price;
        //用户id
        private int user_id;

        public int getShopping_price() {
            return shopping_price;
        }

        public void setShopping_price(int shopping_price) {
            this.shopping_price = shopping_price;
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

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_images() {
            return goods_images == null ? "" : goods_images;
        }

        public void setGoods_images(String goods_images) {
            this.goods_images = goods_images;
        }

        public String getGoods_name() {
            return goods_name == null ? "" : goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getIs_mnh() {
            return is_mnh;
        }

        public void setIs_mnh(int is_mnh) {
            this.is_mnh = is_mnh;
        }

        public String getMarket_price() {
            return market_price == null ? "" : market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getMobile_content() {
            return mobile_content == null ? "" : mobile_content;
        }

        public void setMobile_content(String mobile_content) {
            this.mobile_content = mobile_content;
        }

        public String getShop_price() {
            return shop_price == null ? "" : shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }

    public  class SellerInfoBean {
        /**
         * fans_count : 1000000
         * goods_count : 6
         * head_pic : http://qiniu.jjznewenergy.com/2019_10_07_62db17546
         * last_time : 1570788363
         * nickname : 爱唱跳rap的彭于晏
         * order_count : 1000
         * user_id : 6
         */

        private int fans_count;
        private int goods_count;
        private String head_pic;
        private String last_time;
        private String nickname;
        private String mobile;
        private int order_count;
        private int user_id;

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getFans_count() {
            return fans_count;
        }

        public void setFans_count(int fans_count) {
            this.fans_count = fans_count;
        }

        public int getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(int goods_count) {
            this.goods_count = goods_count;
        }

        public String getHead_pic() {
            return head_pic == null ? "" : head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getLast_time() {
            return last_time == null ? "" : last_time;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getOrder_count() {
            return order_count;
        }

        public void setOrder_count(int order_count) {
            this.order_count = order_count;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
