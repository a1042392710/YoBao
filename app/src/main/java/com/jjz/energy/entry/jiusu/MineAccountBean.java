package com.jjz.energy.entry.jiusu;

/**
 * @Features: 我的收款账户
 * @author: create by chenhao on 2019/9/28
 */
public class MineAccountBean {

    /**
     * 默认收款账户
     */
    private String payee;
    private alipay alipay;
    private wechat wechat;

    public String getPayee() {
        return payee == null ? "" : payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public MineAccountBean.alipay getAlipay() {
        return alipay;
    }

    public void setAlipay(MineAccountBean.alipay alipay) {
        this.alipay = alipay;
    }

    public MineAccountBean.wechat getWechat() {
        return wechat;
    }

    public void setWechat(MineAccountBean.wechat wechat) {
        this.wechat = wechat;
    }


    /**
     * 支付宝
     */
    public class alipay {
        private String alipay_name;

        public String getAlipay_name() {
            return alipay_name == null ? "" : alipay_name;
        }

        public void setAlipay_name(String alipay_name) {
            this.alipay_name = alipay_name;
        }

        public String getAlipay_account() {
            return alipay_account == null ? "" : alipay_account;
        }

        public void setAlipay_account(String alipay_account) {
            this.alipay_account = alipay_account;
        }

        private String alipay_account;
    }

    /**
     * 微信
     */
    public class wechat {
        private String wechat_name;

        public String getWechat_name() {
            return wechat_name == null ? "" : wechat_name;
        }

        public void setWechat_name(String wechat_name) {
            this.wechat_name = wechat_name;
        }

        public String getWechat_account() {
            return wechat_account == null ? "" : wechat_account;
        }

        public void setWechat_account(String wechat_account) {
            this.wechat_account = wechat_account;
        }

        private String wechat_account;
    }
}
