package com.jjz.energy.entry;

import java.io.Serializable;

/**
 * @Features: 获取验证码 返回用户信息
 * @author: create by chenhao on 2019/4/4
 */
public class LoginBean implements Serializable {
    /**
     * 推广的会员数量
     */
    private Member member;
    /**
     * 头像
     */
    private String head_pic;
    /**
     * 会员等级id
     */
    private int level_id;
    /**
     * 上级
     */
    private String parent_id;
    /**
     * 会员等级图片
     */
    private String level_img;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 车牌
     */
    private String license_plate;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别  0 未选择  1 男  2 女
     */
    private int sex;
    /**
     * 是否设置支付密码 0 未设置 1 已设置
     */
    private int issetpaypwd;

    /**
     * 时间戳
     */
    private String time;
    /**
     * 1可以关联上级  0不能
     */
    private int isolate;

    /**
     * 生日
     */
    private String birthday;
    /**
     * 是否绑定微信
     */
    private int is_bind_wechat;
    /**
     * 微信的openId 有 就表示授权了
     */
    private String wxapp_openid;
    /**
     * 是否绑定支付宝
     */
    private int is_bind_alipay;

    /**
     * 升级会员的文案
     */
    private String up_vip_html;

    /**
     * 升级会员的价格
     */
    private String up_vip_money;

    /**
     * 未处理的卖家订单数量
     */
    private int unconfirmed_num;

    //剩余可领取的油
    private double oil_balance;

    // 0 用户  1 卖家
    private int is_manager;

    // 分享有奖的文案
    private String up_vip_center;
    // 推送公告
    private String push_message;

    public String getPush_message() {
        return push_message == null ? "" : push_message;
    }

    public void setPush_message(String push_message) {
        this.push_message = push_message;
    }

    public String getUp_vip_center() {
        return up_vip_center == null ? "" : up_vip_center;
    }

    public void setUp_vip_center(String up_vip_center) {
        this.up_vip_center = up_vip_center;
    }

    public int getIs_manager() {
        return is_manager;
    }

    public void setIs_manager(int is_manager) {
        this.is_manager = is_manager;
    }

    public int getUnconfirmed_num() {
        return unconfirmed_num;
    }

    public void setUnconfirmed_num(int unconfirmed_num) {
        this.unconfirmed_num = unconfirmed_num;
    }

    public double getOil_balance() {
        return oil_balance;
    }

    public void setOil_balance(double oil_balance) {
        this.oil_balance = oil_balance;
    }

    public String getUp_vip_money() {
        return up_vip_money == null ? "" : up_vip_money;
    }

    public void setUp_vip_money(String up_vip_money) {
        this.up_vip_money = up_vip_money;
    }

    public String getUp_vip_html() {
        return up_vip_html == null ? "" : up_vip_html;
    }

    public void setUp_vip_html(String up_vip_html) {
        this.up_vip_html = up_vip_html;
    }

    public String getLicense_plate() {
        return license_plate == null ? "" : license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    private String token;

    public String getParent_id() {
        return parent_id == null ? "" : parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public int getIsolate() {
        return isolate;
    }

    public void setIsolate(int isolate) {
        this.isolate = isolate;
    }

    public String getWxapp_openid() {
        return wxapp_openid == null ? "" : wxapp_openid;
    }

    public void setWxapp_openid(String wxapp_openid) {
        this.wxapp_openid = wxapp_openid;
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

    public int getIssetpaypwd() {
        return issetpaypwd;
    }

    public void setIssetpaypwd(int issetpaypwd) {
        this.issetpaypwd = issetpaypwd;
    }

    public String getLevel_img() {
        return level_img == null ? "" : level_img;
    }

    public void setLevel_img(String level_img) {
        this.level_img = level_img;
    }


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getHead_pic() {
        return head_pic == null ? "" : head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getLevel_id() { return level_id; }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
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

    public int getSex() { return sex; }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBirthday() {
        return birthday == null ? "" : birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}
