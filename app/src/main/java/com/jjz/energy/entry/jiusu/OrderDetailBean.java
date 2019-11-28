package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features: 订单详情数据
 * @author: create by chenhao on 2019/4/15
 */
public class OrderDetailBean implements Serializable {
    //用于生产二维码
    private String order_sn;
    //订单状态 文字描述
    private String order_state;
    private String order_id;//订单id
    private String user_id;    //用户id
    private String shop_name;    //商铺名称
    private String shop_address;    //商铺地址
    private String shop_phone;    //商铺电话
    private String shop_id;    //商铺id
    private String goods_id;    //商品id
    private int goods_num;    //油的数量（单位：升）
    private double goods_price;    //油的单价
    private String order_amount;    //订单总价
    private int pay_status;    //支付状态 1已支付 0未支付
    private int shipping_status;    //卖家确认状态 1是 0否
    private int order_status;    //订单状态 0待确认 1待收货 3交易关闭 4交易成功
    private String pay_name;    //支付名称 alipay wxpay
    private String pay_no;    //支付序列号
    private String pay_code;    //支付类型代码
    private long add_time;    //下单时间
    private String pay_time;    //支付时间
    private String shipping_time;    //商家确定时间 发货时间
    private String confirm_time;    //确认收货时间
    private String goods_name;    //商品名称
    private String goods_img;    //商品图片
    private String qr_code;    //二维码字符串
    private String nickname;    //买家昵称
    private String mobile;    //买家电话
    private String integral_money;    //折扣金额

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public String getIntegral_money() {
        return integral_money == null ? "" : integral_money;
    }

    public void setIntegral_money(String integral_money) {
        this.integral_money = integral_money;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQr_code() {
        return qr_code == null ? "" : qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getShop_phone() {
        return shop_phone == null ? "" : shop_phone;
    }

    public void setShop_phone(String shop_phone) {
        this.shop_phone = shop_phone;
    }

    public String getShop_address() {
        return shop_address == null ? "" : shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_name() {
        return shop_name == null ? "" : shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getOrder_state() {
        return order_state == null ? "" : order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getOrder_sn() {
        return order_sn == null ? "" : order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }
    public String getOrder_amount() {
        return order_amount == null ? "" : order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getOrder_id() {
        return order_id == null ? "" : order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id == null ? "" : user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getShop_id() {
        return shop_id == null ? "" : shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getGoods_id() {
        return goods_id == null ? "" : goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }


    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public int getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(int shipping_status) {
        this.shipping_status = shipping_status;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getPay_name() {
        return pay_name == null ? "" : pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getPay_no() {
        return pay_no == null ? "" : pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public String getPay_code() {
        return pay_code == null ? "" : pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getPay_time() {
        return pay_time == null ? "" : pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getShipping_time() {
        return shipping_time == null ? "" : shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }

    public String getConfirm_time() {
        return confirm_time == null ? "" : confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
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
