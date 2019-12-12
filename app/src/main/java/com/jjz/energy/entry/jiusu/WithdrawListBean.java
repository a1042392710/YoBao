package com.jjz.energy.entry.jiusu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 提现记录
 * @author: create by chenhao on 2019/4/26
 */
public class WithdrawListBean implements Serializable {


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



        private long apply_time ;
        private int status ;
        private String statusName ;
        private String typeName ;
        private String total_money ;
        private String pay_money ;
        private String charge ;
        //税费用
        private String total_taxes ;

        public long getApply_time() {
            return apply_time;
        }

        public void setApply_time(long apply_time) {
            this.apply_time = apply_time;
        }

        public String getCharge() {
            return charge == null ? "" : charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }

        public String getTotal_taxes() {
            return total_taxes == null ? "" : total_taxes;
        }

        public void setTotal_taxes(String total_taxes) {
            this.total_taxes = total_taxes;
        }

        public String getTotal_money() {
            return total_money == null ? "" : total_money;
        }

        public void setTotal_money(String total_money) {
            this.total_money = total_money;
        }

        public String getPay_money() {
            return pay_money == null ? "" : pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusName() {
            return statusName == null ? "" : statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getTypeName() {
            return typeName == null ? "" : typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

    }

}
