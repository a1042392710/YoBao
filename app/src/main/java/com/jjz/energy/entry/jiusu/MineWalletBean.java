package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features:
 * @author: create by chenhao on 2019/4/26
 */
public class MineWalletBean implements Serializable {

    /**
     * 佣金余额
     */
    private String total_commissions = "0";

    /**
     * 可提现余额
     */
    private String apply_commissions= "0";



    public String getTotal_commissions() {
        return total_commissions == null ? "" : total_commissions;
    }

    public void setTotal_commissions(String total_commissions) {
        this.total_commissions = total_commissions;
    }

    public String getApply_commissions() {
        return apply_commissions == null ? "" : apply_commissions;
    }

    public void setApply_commissions(String apply_commissions) {
        this.apply_commissions = apply_commissions;
    }
}
