package com.jjz.energy.entry;

import java.io.Serializable;

/**
 * @Features: 个人信息
 * @author: create by chenhao on 2019/9/28
 */
public class UserInfo implements Serializable {

    /**
     * 用户ID
     */
    private int user_id;
    /**
     * 头像
     */
    private String head_pic;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 简介
     */
    private String desc;
    /**
     * 性别  0 未选择  1 男  2 女
     */
    private int sex;
    /**
     * 是否设置了登录密码  1有  0 无
     */
    private int haspassword;

    /**
     * 时间戳
     */
    private String time;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 是否绑定微信
     */
    private int is_bind_wechat;
    /**
     * 是否绑定支付宝
     */
    private int is_bind_alipay;
    /**
     * 微信的openId 有 就表示授权了
     */
    private String wxapp_openid;
    /**
     * 用户token
     */
    private String token;
    /**
     * 极光IM 用户密码
     */
    private String jmessage_password;
    /**
     * 资料完整度
     */
    private int completion;
    /**
     * 实名认证 的身份证图片
     */
    private int is_set_idcard;

    /**
     * 剩余积分数量
     */
    private float pay_points;

    /**
     * 商家Id
     */
    private String shop_id;
    /**
     * 粉丝数量
     */
    private int focus_num;
    /**
     * 关注数量
     */
    private int fans_num;

    /**
     * 推送公告
     */
    private String push_message;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getHead_pic() {
        return head_pic == null ? "" : head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDesc() {
        return desc == null ? "" : desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getHaspassword() {
        return haspassword;
    }

    public void setHaspassword(int haspassword) {
        this.haspassword = haspassword;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBirthday() {
        return birthday == null ? "" : birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getIs_bind_wechat() {
        return is_bind_wechat;
    }

    public void setIs_bind_wechat(int is_bind_wechat) {
        this.is_bind_wechat = is_bind_wechat;
    }

    public int getIs_bind_alipay() {
        return is_bind_alipay;
    }

    public void setIs_bind_alipay(int is_bind_alipay) {
        this.is_bind_alipay = is_bind_alipay;
    }

    public String getWxapp_openid() {
        return wxapp_openid == null ? "" : wxapp_openid;
    }

    public void setWxapp_openid(String wxapp_openid) {
        this.wxapp_openid = wxapp_openid;
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getJmessage_password() {
        return jmessage_password == null ? "" : jmessage_password;
    }

    public void setJmessage_password(String jmessage_password) {
        this.jmessage_password = jmessage_password;
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }

    public int getIs_set_idcard() {
        return is_set_idcard;
    }

    public void setIs_set_idcard(int is_set_idcard) {
        this.is_set_idcard = is_set_idcard;
    }

    public float getPay_points() {
        return pay_points;
    }

    public void setPay_points(float pay_points) {
        this.pay_points = pay_points;
    }

    public String getShop_id() {
        return shop_id == null ? "" : shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public int getFocus_num() {
        return focus_num;
    }

    public void setFocus_num(int focus_num) {
        this.focus_num = focus_num;
    }

    public int getFans_num() {
        return fans_num;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }

    public String getPush_message() {
        return push_message == null ? "" : push_message;
    }

    public void setPush_message(String push_message) {
        this.push_message = push_message;
    }
}
