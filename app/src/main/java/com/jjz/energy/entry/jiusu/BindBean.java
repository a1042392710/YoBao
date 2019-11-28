package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features: 绑定的微信支付宝信息
 * @author: create by chenhao on 2019/4/12
 */
public class BindBean implements Serializable {

    /**
     * 手机号码/账号
     */
    private String alipay_account  ;
    /**
     * 真实姓名
     */
    private String alipay_name ;


    /**
     * 手机号码/账号
     */
    private String wechat_account  ;
    /**
     * 真实姓名
     */
    private String wechat_name ;
    /**
     * 微信昵称
     */
    private String nickname ;

    public String getWechat_account() {
        return wechat_account == null ? "" : wechat_account;
    }

    public void setWechat_account(String wechat_account) {
        this.wechat_account = wechat_account;
    }

    public String getWechat_name() {
        return wechat_name == null ? "" : wechat_name;
    }

    public void setWechat_name(String wechat_name) {
        this.wechat_name = wechat_name;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAlipay_account() {
        return alipay_account == null ? "" : alipay_account;
    }

    public void setAlipay_account(String alipay_account) {
        this.alipay_account = alipay_account;
    }

    public String getAlipay_name() {
        return alipay_name == null ? "" : alipay_name;
    }

    public void setAlipay_name(String alipay_name) {
        this.alipay_name = alipay_name;
    }


}
