package com.jjz.energy.base;

/**
 * @ author FX
 * @ date  2018/12/24  17:05
 * @ fuction
 */
public class LoginEventBean {

    /**
     * 取消登录
     */
    public static final byte LOGIN_CANCLE = -1;
    /**
     * 登入
     */
    public static final byte LOG_IN = 1;
    /**
     * 登出
     */
    public static final byte LOG_OUT = 0;

    /**
     * 弹出填写验证码的页面
     */
    public static final byte SHOW_TUIJIAN = 20;

    /**
     * 微信支付成功,或者支付拒绝
     */
    public static final byte WEIXIN_PAYSUC = 30;
    /**
     * 微信授权登录成功
     */
    public static final byte WECHAT_LOG_IN = 31;
    /**
     * 微信绑定成功
     */
    public static final byte WECHAT_BIND_SUC = 32;
    /**
     * 充值会员成功
     */
    public static final byte WEIXIN_VIP_PAYSUC = 33;
    /**
     * 微信分享成功
     */
    public static final byte SHARE_SUC = 33;

    /**
     * 支付宝支付完成
     */
    public static final byte ALIPAY_SUCCESS = 40;
    /**
     * 支付宝绑定成功
     */
    public static final byte ALIPAY_BIND_SUC = 41;




    /**
     * 携带的数据
     */
    private String value ;

    private byte loginStatus = -2;

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LoginEventBean(byte loginStatus) {
        this.loginStatus = loginStatus;
    }

    public byte getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(byte loginStatus) {
        this.loginStatus = loginStatus;
    }


}
