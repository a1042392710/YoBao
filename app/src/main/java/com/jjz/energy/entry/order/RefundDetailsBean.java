package com.jjz.energy.entry.order;

import java.io.Serializable;

/**
 * @Features: 退款详情
 * @author: create by chenhao on 2019/11/4
 */
public class RefundDetailsBean implements Serializable {


    /**
     * 售后状态
     */
   private int return_status;
    /**
     * 退款类型  0 仅退款 1 退货
     */
   private int type;
    /**
     * 是否已发货  0 未发货  1 已发货
     */
   private int shipping_status;
    /**
     * 退款编号
     */
    private String refund_sn;
    /**
     * 退款申请时间
     */
    private long r_addtime;
    /**
     * 退款金额
     */
    private String refund_money;
    /**
     * 商品名称
     */
    private String goods_name;
    /**
     *商品编号
     */
    private int goods_id;
    /**
     * 商品图片
     */
    private String goods_images;
    /**
     * 订单编号
     */
    private String order_sn;
    /**
     * 订单生成时间
     */
    private String o_addtime;
    /**
     * 退款原因
     */
    private String reason_txt;
    /**
     * 退款成功的时间
     */
    private long refund_time;
    /**
     * 买家撤销时间
     */
    private long canceltime;
    /**
     * 拒绝原因
     */
    private String reject_reason;
    /**
     * 商品id
     */
    private int rec_id;

    private Trick trick;

    public Trick getTrick() {
        return trick;
    }

    public void setTrick(Trick trick) {
        this.trick = trick;
    }

    public long getCanceltime() {
        return canceltime;
    }

    public void setCanceltime(long canceltime) {
        this.canceltime = canceltime;
    }

    public int getRec_id() {
        return rec_id;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }

    public String getReject_reason() {
        return reject_reason == null ? "" : reject_reason;
    }

    public void setReject_reason(String reject_reason) {
        this.reject_reason = reject_reason;
    }

    public long getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(long refund_time) {
        this.refund_time = refund_time;
    }

    public String getRefund_sn() {
        return refund_sn == null ? "" : refund_sn;
    }

    public void setRefund_sn(String refund_sn) {
        this.refund_sn = refund_sn;
    }

    public long getR_addtime() {
        return r_addtime;
    }

    public void setR_addtime(long r_addtime) {
        this.r_addtime = r_addtime;
    }

    public String getRefund_money() {
        return refund_money == null ? "" : refund_money;
    }

    public void setRefund_money(String refund_money) {
        this.refund_money = refund_money;
    }

    public String getGoods_name() {
        return goods_name == null ? "" : goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
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

    public String getOrder_sn() {
        return order_sn == null ? "" : order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getO_addtime() {
        return o_addtime == null ? "" : o_addtime;
    }

    public void setO_addtime(String o_addtime) {
        this.o_addtime = o_addtime;
    }

    public String getReason_txt() {
        return reason_txt == null ? "" : reason_txt;
    }

    public void setReason_txt(String reason_txt) {
        this.reason_txt = reason_txt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(int shipping_status) {
        this.shipping_status = shipping_status;
    }

    public int getReturn_status() {
        return return_status;
    }

    public void setReturn_status(int return_status) {
        this.return_status = return_status;
    }

    public  class Trick implements Serializable{

        /**
         * 物流公司
         */
        private String shipping_name;
        private String courier_number;
        private String state;
        private String AcceptStation;
        private String AcceptTime;

        public String getAcceptTime() {
            return AcceptTime == null ? "" : AcceptTime;
        }

        public void setAcceptTime(String acceptTime) {
            AcceptTime = acceptTime;
        }

        public String getShipping_name() {
            return shipping_name == null ? "" : shipping_name;
        }

        public void setShipping_name(String shipping_name) {
            this.shipping_name = shipping_name;
        }

        public String getCourier_number() {
            return courier_number == null ? "" : courier_number;
        }

        public void setCourier_number(String courier_number) {
            this.courier_number = courier_number;
        }

        public String getState() {
            return state == null ? "" : state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getAcceptStation() {
            return AcceptStation == null ? "" : AcceptStation;
        }

        public void setAcceptStation(String acceptStation) {
            AcceptStation = acceptStation;
        }


    }
}
