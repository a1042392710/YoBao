package com.jjz.energy.wxapi;

import java.util.List;

/**
 * @ author FX
 * @ date  2019/1/3  17:11
 * @ fuction
 */
public class UserInfoBean {

    /**
     * openid : okVKhwHPx6upTkGq99-1SpvxY8UA
     * nickname : 杨飞
     * sex : 1
     * language : zh_CN
     * city : Nanchang
     * province : Jiangxi
     * country : CN
     * headimgurl : http://wx.qlogo.cn/mmopen/PiajxSqBRaELSnKAq9KXkOLmr6aibruQ2jUm1Js0mwICCSpXCpKuibskUf8NRfGer5tUQJDvLibMhhlpaCJeNgQAog/0
     * privilege : []
     * unionid : oiazt0Ul9_XpH3ETZh3smc8fWIkY
     */
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<String> privilege;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<String> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<String> privilege) {
        this.privilege = privilege;
    }
}
