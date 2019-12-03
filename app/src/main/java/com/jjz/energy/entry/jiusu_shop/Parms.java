package com.jjz.energy.entry.jiusu_shop;

import java.io.Serializable;

/**
     * 支付成功携带参数进下个页面
     */
    public class Parms implements Serializable {
        private String shop_name;
        private String shop_img;
        private String price_title;
        private long pay_time;

        public String getShop_name() {
            return shop_name == null ? "" : shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_img() {
            return shop_img == null ? "" : shop_img;
        }

        public void setShop_img(String shop_img) {
            this.shop_img = shop_img;
        }

        public String getPrice_title() {
            return price_title == null ? "" : price_title;
        }

        public void setPrice_title(String price_title) {
            this.price_title = price_title;
        }

        public long getPay_time() {
            return pay_time;
        }

        public void setPay_time(long pay_time) {
            this.pay_time = pay_time;
        }
    }
