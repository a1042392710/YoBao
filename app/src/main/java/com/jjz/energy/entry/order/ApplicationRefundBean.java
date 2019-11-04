package com.jjz.energy.entry.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhao 2019/1/8
 * @function 申请售后
 */
public class ApplicationRefundBean implements Serializable {

    /**
     * order_goods : {"goods_name":"男士手表","goods_num":4,"final_price":"2325.00","spec_key_name":"蓝色,XS,超级五大 阿三超级五大 阿三超级五大 阿三超级五大 阿三超级五大 阿三超级五大 阿三超级五大 阿三","original_img":"http://img.hyuansc.com/2019_01_03_588538908.jpg"}
     * reason : [{"id":1,"value":"不想要了"},{"id":2,"value":"商品信息描述不符"},{"id":3,"value":"拍多了"},{"id":4,"value":"少件/漏件"},{"id":5,"value":"包装/商品破损/污渍"},{"id":6,"value":"卖家发错货"},{"id":7,"value":"发票问题"}]
     * way : [{"value":1,"title":"快递寄回"},{"value":2,"title":"上门取件"}]
     */

    private OrderGoodsBean order_goods;
    private List<RefundTypeBean> reason;
    private List<RefundTypeBean> way;
    private ReturnGoodsBean return_goods;

    public ReturnGoodsBean getReturn_goods() {
        return return_goods;
    }

    public void setReturn_goods(ReturnGoodsBean return_goods) {
        this.return_goods = return_goods;
    }

    public OrderGoodsBean getOrder_goods() {
        return order_goods;
    }

    public void setOrder_goods(OrderGoodsBean order_goods) {
        this.order_goods = order_goods;
    }

    public List<RefundTypeBean> getReason() {
        if (reason == null) {
            return new ArrayList<>();
        }
        return reason;
    }

    public void setReason(List<RefundTypeBean> reason) {
        this.reason = reason;
    }

    public List<RefundTypeBean> getWay() {
        if (way == null) {
            return new ArrayList<>();
        }
        return way;
    }

    public void setWay(List<RefundTypeBean> way) {
        this.way = way;
    }

    public class OrderGoodsBean implements Serializable {
        /**
         * goods_name : 男士手表
         * goods_num : 4
         * final_price : 2325.00
         * spec_key_name : 蓝色,XS,超级五大 阿三超级五大 阿三超级五大 阿三超级五大 阿三超级五大 阿三超级五大 阿三超级五大 阿三
         * original_img : http://img.hyuansc.com/2019_01_03_588538908.jpg
         */

        private String goods_name;
        private int goods_num;
        private String total;
        private String final_price;
        private String spec_key_name;

        public String getTotal() {
            return total == null ? "" : total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        private String original_img;



        public String getGoods_name() {
            return goods_name == null ? "" : goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public String getFinal_price() {
            return final_price == null ? "" : final_price;
        }

        public void setFinal_price(String final_price) {
            this.final_price = final_price;
        }

        public String getSpec_key_name() {
            return spec_key_name == null ? "" : spec_key_name;
        }

        public void setSpec_key_name(String spec_key_name) {
            this.spec_key_name = spec_key_name;
        }

        public String getOriginal_img() {
            return original_img == null ? "" : original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }
    }


    public class ReturnGoodsBean implements Serializable {

        private int reason;        //订单原因id
        private String describe;        //说明
        private String imgs; //图片url，逗号分隔
        private String refund_deposit;//金额

        public String getRefund_deposit() {
            return refund_deposit == null ? "" : refund_deposit;
        }

        public void setRefund_deposit(String refund_deposit) {
            this.refund_deposit = refund_deposit;
        }

        public int getReason() {
            return reason;
        }

        public void setReason(int reason) {
            this.reason = reason;
        }

        public String getDescribe() {
            return describe == null ? "" : describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getImgs() {
            return imgs == null ? "" : imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }



    }

}
