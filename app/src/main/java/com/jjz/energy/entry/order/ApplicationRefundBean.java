package com.jjz.energy.entry.order;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenhao 2019/1/8
 * @function 申请售后
 */
public class ApplicationRefundBean implements Serializable {


    /**
     * order_goods : {"final_price":"0.01","goods_images":"http://qiniu.jjznewenergy
     * .com/2019_10_14_053a46351","goods_name":"13123123123123","goods_num":1,"spec_key_name":"",
     * "total":0.01}
     * reason : [{"id":1,"value":"不想要了"},{"id":2,"value":"商品信息描述不符"},{"id":3,"value":"拍多了"},{"id
     * ":4,"value":"少件/漏件"},{"id":5,"value":"包装/商品破损/污渍"},{"id":6,"value":"卖家发错货"},{"id":7,
     * "value":"发票问题"}]
     * return_goods : {"refund_money":0}
     */

    private OrderGoodsBean order_goods;
    private ReturnGoodsBean return_goods;
    private List<RefundTypeBean> reason;

    public OrderGoodsBean getOrder_goods() { return order_goods;}

    public void setOrder_goods(OrderGoodsBean order_goods) { this.order_goods = order_goods;}

    public ReturnGoodsBean getReturn_goods() { return return_goods;}

    public void setReturn_goods(ReturnGoodsBean return_goods) { this.return_goods = return_goods;}

    public List<RefundTypeBean> getReason() { return reason;}

    public void setReason(List<RefundTypeBean> reason) { this.reason = reason;}

    public class OrderGoodsBean implements Serializable {
        /**
         * final_price : 0.01
         * goods_images : http://qiniu.jjznewenergy.com/2019_10_14_053a46351
         * goods_name : 13123123123123
         * goods_num : 1
         * spec_key_name :
         * total : 0.01
         */

        private String final_price;
        private String goods_images;
        private String goods_name;
        private int goods_num;
        private String spec_key_name;
        private double total;

        public String getFinal_price() { return final_price;}

        public void setFinal_price(String final_price) { this.final_price = final_price;}

        public String getGoods_images() { return goods_images;}

        public void setGoods_images(String goods_images) { this.goods_images = goods_images;}

        public String getGoods_name() { return goods_name;}

        public void setGoods_name(String goods_name) { this.goods_name = goods_name;}

        public int getGoods_num() { return goods_num;}

        public void setGoods_num(int goods_num) { this.goods_num = goods_num;}

        public String getSpec_key_name() { return spec_key_name;}

        public void setSpec_key_name(String spec_key_name) { this.spec_key_name = spec_key_name;}

        public double getTotal() { return total;}

        public void setTotal(double total) { this.total = total;}
    }

    public  class ReturnGoodsBean implements Serializable {

        private int reason;        //订单原因id
        private String describe;        //说明
        private String imgs; //图片url，逗号分隔
        private String refund_money;//金额

        public String getRefund_money() {
            return refund_money == null ? "" : refund_money;
        }

        public void setRefund_money(String refund_money) {
            this.refund_money = refund_money;
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
