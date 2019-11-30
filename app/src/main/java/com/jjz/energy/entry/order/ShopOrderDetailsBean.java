package com.jjz.energy.entry.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 订单详情
 * @author: create by chenhao on 2019/10/21
 */
public class ShopOrderDetailsBean implements Serializable {

    //订单状态（0待支付，1待发货,2待收货，3待评价，4交易关闭，5交易完成）

    /**
     * address : 翻云覆雨餐厅
     * city : 110100
     * consignee : 陈浩浩
     * district : 110103
     * full_address : 北京市北京市崇文区翻云覆雨餐厅
     * goods_amount : 0.01
     * goods_id : 246
     * goods_images : http://qiniu.jjznewenergy.com/2019_10_14_053a46351
     * goods_name : 13123123123123
     * goods_num : 1
     * goods_price : 0.01
     * is_comment : 0
     * mobile : 13576102823
     * order_amount : 0.01
     * order_sn : order620191023174618
     * order_status : 0
     * pay_status : 0
     * pay_time : 0
     * province : 110000
     * shipping_code :
     * shipping_price : 0.00
     * shipping_status : 0
     * status : 0
     * statusList : ["待支付","待发货","待收货","待评价","交易关闭","交易完成"]
     * twon : 0
     */
    /**
     * 到期时间
     */
    private long end_time;
    /**
     * 商品Id
     */
    private int rec_id;
    /**
     * 售后id
     */
    private String return_id;

    public String getReturn_id() {
        return return_id == null ? "" : return_id;
    }

    public void setReturn_id(String return_id) {
        this.return_id = return_id;
    }

    /**
     * 物流单号
     */
    private String shipping_no;
    private String address;
    private int city;
    private String consignee;
    private int district;
    private String full_address;
    private String goods_amount;
    private int goods_id;
    private String goods_images;
    private String goods_name;
    private int goods_num;
    private String goods_price;
    private int is_comment;
    private String mobile;
    private String buyer_mobile;
    private String seller_mobile;
    private String order_amount;
    private String order_sn;
    private int order_status;
    private int pay_status;
    private long pay_time;
    private int province;
    private String shipping_code;
    private String shipping_price;
    private int shipping_status;
    private int status;
    private String state;
    private String pay_name;
    private int twon;
    private int return_status;
    private String integral_money;

    public String getIntegral_money() {
        return integral_money == null ? "" : integral_money;
    }

    public void setIntegral_money(String integral_money) {
        this.integral_money = integral_money;
    }

    public int getReturn_status() {
        return return_status;
    }

    public void setReturn_status(int return_status) {
        this.return_status = return_status;
    }

    private List<String> statusList;

    public String getBuyer_mobile() {
        return buyer_mobile == null ? "" : buyer_mobile;
    }

    public void setBuyer_mobile(String buyer_mobile) {
        this.buyer_mobile = buyer_mobile;
    }

    public String getSeller_mobile() {
        return seller_mobile == null ? "" : seller_mobile;
    }

    public void setSeller_mobile(String seller_mobile) {
        this.seller_mobile = seller_mobile;
    }

    public int getRec_id() {
        return rec_id;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getShipping_no() {
        return shipping_no == null ? "" : shipping_no;
    }

    public void setShipping_no(String shipping_no) {
        this.shipping_no = shipping_no;
    }

    public String getPay_name() {
        return pay_name == null ? "" : pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getState() {
        return state == null ? "" : state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getPay_time() {
        return pay_time;
    }

    public void setPay_time(long pay_time) {
        this.pay_time = pay_time;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getConsignee() {
        return consignee == null ? "" : consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public String getFull_address() {
        return full_address == null ? "" : full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public String getGoods_amount() {
        return goods_amount == null ? "" : goods_amount;
    }

    public void setGoods_amount(String goods_amount) {
        this.goods_amount = goods_amount;
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

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public String getGoods_price() {
        return goods_price == null ? "" : goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public int getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(int is_comment) {
        this.is_comment = is_comment;
    }

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrder_amount() {
        return order_amount == null ? "" : order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getOrder_sn() {
        return order_sn == null ? "" : order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public String getShipping_code() {
        return shipping_code == null ? "" : shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code;
    }

    public String getShipping_price() {
        return shipping_price == null ? "" : shipping_price;
    }

    public void setShipping_price(String shipping_price) {
        this.shipping_price = shipping_price;
    }

    public int getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(int shipping_status) {
        this.shipping_status = shipping_status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTwon() {
        return twon;
    }

    public void setTwon(int twon) {
        this.twon = twon;
    }

    public List<String> getStatusList() {
        if (statusList == null) {
            return new ArrayList<>();
        }
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }
}
