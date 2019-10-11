package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features: 订单列表数据
 * @author: create by chenhao on 2019/4/15
 */
public class OrderBean implements Serializable {
    //订单id
    private String order_id;
    //卖家是否接受订单 0否 1 是
    private int shipping_status;
    //用于生产二维码
    private String order_sn;
    //下单时间
    private String add_time;
    //订单状态
    private int order_status;
    //订单状态 文字描述
    private String order_state;
    //商品名称
    private String goods_name;
    //商品图片
    private String goods_img;
    //商品数量
    private int goods_num;
    //商品总价
    private String order_amount;
    //发票状态  -1 未开 0 在开 1 已开 2 作废
    private int  invoice_status;

    public int getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(int invoice_status) {
        this.invoice_status = invoice_status;
    }

    public int getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(int shipping_status) {
        this.shipping_status = shipping_status;
    }

    public String getOrder_state() {
        return order_state == null ? "" : order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getOrder_amount() {
        return order_amount == null ? "" : order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public String getOrder_sn() {
        return order_sn == null ? "" : order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_id() {
        return order_id == null ? "" : order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAdd_time() {
        return add_time == null ? "" : add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getGoods_name() {
        return goods_name == null ? "" : goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img == null ? "" : goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }



}
