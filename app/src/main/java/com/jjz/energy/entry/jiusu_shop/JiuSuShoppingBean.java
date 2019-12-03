package com.jjz.energy.entry.jiusu_shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 商家店内消费记录
 * @author: create by chenhao on 2019/11/9
 */
public class JiuSuShoppingBean implements Serializable {

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

    public  class  ListBean implements Serializable{
        private String shop_name;
        private String shop_img;
        private String id;
        private long pay_time;
        private String order_amount;
        private String total_amount;
        private String order_sn;

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

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getPay_time() {
            return pay_time;
        }

        public void setPay_time(long pay_time) {
            this.pay_time = pay_time;
        }

        public String getOrder_amount() {
            return order_amount == null ? "" : order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getTotal_amount() {
            return total_amount == null ? "" : total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getOrder_sn() {
            return order_sn == null ? "" : order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }
    }

}
