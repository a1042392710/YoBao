package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features: 佣金记录
 * @author: create by chenhao on 2019/4/26
 */
public class MineWalletListBean implements Serializable {


    /**
     * date : 2019年03月
     * push_money : 0
     * net_money : 0
     * spec_money : 0
     * total_money : 0
     */

    private String date;
    private String push_money;
    private String net_money;
    private String spec_money;
    private String total_money;

    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPush_money() {
        return push_money == null ? "" : push_money;
    }

    public void setPush_money(String push_money) {
        this.push_money = push_money;
    }

    public String getNet_money() {
        return net_money == null ? "" : net_money;
    }

    public void setNet_money(String net_money) {
        this.net_money = net_money;
    }

    public String getSpec_money() {
        return spec_money == null ? "" : spec_money;
    }

    public void setSpec_money(String spec_money) {
        this.spec_money = spec_money;
    }

    public String getTotal_money() {
        return total_money == null ? "" : total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }
}
