package com.jjz.energy.entry.order;

import java.io.Serializable;

/**
 * @Features: 物流地址
 * @author: create by chenhao on 2019/10/25
 */
public class ExpressAddressInfoBean implements Serializable {


    /**
     * address : 翻云覆雨餐厅
     * city : 110100
     * consignee : 陈浩浩
     * district : 110103
     * full_address : 北京市北京市崇文区翻云覆雨餐厅
     * mobile : 13576102823
     * order_sn : order6201910250943471621
     * order_status : 0
     * pay_status : 1
     * province : 110000
     * send_info : {"address_id":162,"consignee":"陈浩浩","full_address":"北京市北京市崇文区翻云覆雨餐厅"}
     * "mobile":"13576102823"
     * shipping_status : 0
     */

    private String address;
    private int city;
    private String consignee;
    private int district;
    private String full_address;
    private String mobile;
    private String order_sn;
    private int order_status;
    private int pay_status;
    private int province;
    private SendInfoBean send_info;
    private int shipping_status;

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

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public SendInfoBean getSend_info() {
        return send_info;
    }

    public void setSend_info(SendInfoBean send_info) {
        this.send_info = send_info;
    }

    public int getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(int shipping_status) {
        this.shipping_status = shipping_status;
    }

    public  class SendInfoBean implements Serializable{
        /**
         * address_id : 162
         * consignee : 陈浩浩
         * full_address : 北京市北京市崇文区翻云覆雨餐厅
         * mobile : 13576102823
         */

        private int address_id;
        private String consignee;
        private String full_address;
        private String mobile;

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public String getConsignee() {
            return consignee == null ? "" : consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getFull_address() {
            return full_address == null ? "" : full_address;
        }

        public void setFull_address(String full_address) {
            this.full_address = full_address;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
