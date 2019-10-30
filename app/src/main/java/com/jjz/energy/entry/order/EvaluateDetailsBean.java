package com.jjz.energy.entry.order;

import java.io.Serializable;

/**
 * @Features: 评论详情
 * @author: create by chenhao on 2019/10/29
 */
public class EvaluateDetailsBean implements Serializable {


    /**
     * buyer_add_time : 1572083203
     * buyer_content : 烦烦烦烦烦
     * buyer_head_pic : http://qiniu.jjznewenergy.com/2019_10_09_9bcea1775
     * buyer_img : http://qiniu.jjznewenergy.com/2019_10_26_b2dec3488
     * buyer_nickname : 爱唱跳rap的彭于晏
     * buyer_start : 1
     * goods_info : {"confirm_time":1571998661,"goods_images":"http://qiniu.jjznewenergy
     * .com/2019_10_14_053a46351","goods_name":"13123123123123","order_id":2195,"saler_uid":1,
     * "user_id":6}
     * saler_add_time : 1570954994
     * saler_content : 12312312
     * saler_head_pic : http://qiniu.jjznewenergy.com/2019_09_10_c129d6006
     * saler_nickname : 廖睿芝
     * saler_start : 2
     */

    private int buyer_add_time;
    private String buyer_content;
    private String buyer_head_pic;
    private String buyer_img;
    private String saler_img;
    private String buyer_nickname;
    private int buyer_start;
    private GoodsInfoBean goods_info;
    private int saler_add_time;
    private String saler_content;
    private String saler_head_pic;
    private String saler_nickname;
    private int saler_start;

    public String getSaler_img() {
        return saler_img == null ? "" : saler_img;
    }

    public void setSaler_img(String saler_img) {
        this.saler_img = saler_img;
    }

    public int getBuyer_add_time() {
        return buyer_add_time;
    }

    public void setBuyer_add_time(int buyer_add_time) {
        this.buyer_add_time = buyer_add_time;
    }

    public String getBuyer_content() {
        return buyer_content == null ? "" : buyer_content;
    }

    public void setBuyer_content(String buyer_content) {
        this.buyer_content = buyer_content;
    }

    public String getBuyer_head_pic() {
        return buyer_head_pic == null ? "" : buyer_head_pic;
    }

    public void setBuyer_head_pic(String buyer_head_pic) {
        this.buyer_head_pic = buyer_head_pic;
    }

    public String getBuyer_img() {
        return buyer_img == null ? "" : buyer_img;
    }

    public void setBuyer_img(String buyer_img) {
        this.buyer_img = buyer_img;
    }

    public String getBuyer_nickname() {
        return buyer_nickname == null ? "" : buyer_nickname;
    }

    public void setBuyer_nickname(String buyer_nickname) {
        this.buyer_nickname = buyer_nickname;
    }

    public int getBuyer_start() {
        return buyer_start;
    }

    public void setBuyer_start(int buyer_start) {
        this.buyer_start = buyer_start;
    }

    public GoodsInfoBean getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(GoodsInfoBean goods_info) {
        this.goods_info = goods_info;
    }

    public int getSaler_add_time() {
        return saler_add_time;
    }

    public void setSaler_add_time(int saler_add_time) {
        this.saler_add_time = saler_add_time;
    }

    public String getSaler_content() {
        return saler_content == null ? "" : saler_content;
    }

    public void setSaler_content(String saler_content) {
        this.saler_content = saler_content;
    }

    public String getSaler_head_pic() {
        return saler_head_pic == null ? "" : saler_head_pic;
    }

    public void setSaler_head_pic(String saler_head_pic) {
        this.saler_head_pic = saler_head_pic;
    }

    public String getSaler_nickname() {
        return saler_nickname == null ? "" : saler_nickname;
    }

    public void setSaler_nickname(String saler_nickname) {
        this.saler_nickname = saler_nickname;
    }

    public int getSaler_start() {
        return saler_start;
    }

    public void setSaler_start(int saler_start) {
        this.saler_start = saler_start;
    }

    public  class GoodsInfoBean implements Serializable{
        /**
         * confirm_time : 1571998661
         * goods_images : http://qiniu.jjznewenergy.com/2019_10_14_053a46351
         * goods_name : 13123123123123
         * order_id : 2195
         * saler_uid : 1
         * user_id : 6
         */

        private long confirm_time;
        private String goods_images;
        private String goods_name;
        private int order_id;
        private int saler_uid;
        private int user_id;

        public long getConfirm_time() {
            return confirm_time;
        }

        public void setConfirm_time(long confirm_time) {
            this.confirm_time = confirm_time;
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

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getSaler_uid() {
            return saler_uid;
        }

        public void setSaler_uid(int saler_uid) {
            this.saler_uid = saler_uid;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
