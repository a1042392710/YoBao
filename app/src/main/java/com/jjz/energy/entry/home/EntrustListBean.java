package com.jjz.energy.entry.home;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 委托大厅
 * @author: create by chenhao on 2019/11/9
 */
public class EntrustListBean implements Serializable {

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

    public class ListBean implements Serializable {


        /**
         * add_time : 1578386838
         * city : 衡阳市
         * demand_content : 帮我做掉一个人，一千块就是你的
         * district : 蒸湘区
         * head_pic : http://qiniu.jjznewenergy.com/2019_11_09_da334340
         * id : 2
         * money : 1884.00
         * nickname : 爱唱跳rap的彭于晏
         * order_sn : demand6202001071647182710
         * province : 湖南省
         * status : 0
         */
        private long add_time;
        private String city;
        private String demand_content;
        private String district;
        private String head_pic;
        private int id;
        private String money;
        private String nickname;
        private String order_sn;
        private String province;
        private int status;
        private String state;
        private String mobile;


        public String getState() {
            return state == null ? "" : state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getCity() {
            return city == null ? "" : city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDemand_content() {
            return demand_content == null ? "" : demand_content;
        }

        public void setDemand_content(String demand_content) {
            this.demand_content = demand_content;
        }

        public String getDistrict() {
            return district == null ? "" : district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getHead_pic() {
            return head_pic == null ? "" : head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMoney() {
            return money == null ? "" : money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getOrder_sn() {
            return order_sn == null ? "" : order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getProvince() {
            return province == null ? "" : province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
