package com.jjz.energy.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhao 2018/12/20
 * @function 地址列表
 */
public class AddressBean implements Serializable {


    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    private List<ListBean> list;



    public  class ListBean implements Serializable{
        /**
         * address_id : 90
         * consignee : 老吴
         * country : 0
         * province : 110000
         * city : 110100
         * district : 110101
         * twon : 0
         * address : 北京市东城区王府井
         * mobile : 18579212005
         * is_default : 1
         */

        private int address_id;
        private String consignee;
        private int country;
        private int province;
        private int city;
        private int district;
        private int twon;
        private String address;
        private String area;

        public String getArea() {
            return area == null ? "" : area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        private String mobile;
        private int is_default;

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

        public int getCountry() {
            return country;
        }

        public void setCountry(int country) {
            this.country = country;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public int getDistrict() {
            return district;
        }

        public void setDistrict(int district) {
            this.district = district;
        }

        public int getTwon() {
            return twon;
        }

        public void setTwon(int twon) {
            this.twon = twon;
        }

        public String getAddress() {
            return address == null ? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }
    }
}
