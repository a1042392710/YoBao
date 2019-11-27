package com.jjz.energy.entry.jiusu;

import java.io.Serializable;

/**
 * @Features: 我的页面 返回的用户信息
 * @author: create by chenhao on 2019/9/28
 */
public class MineInfoBean  implements Serializable {
    /**
     * 头像
     */
    private String head_pic;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 个人主页
     */
    private int user_id ;

    // 推送公告
    private String push_message;

    /**
     * 粉丝数量
      */
    private int focus_num;
    /**
     * 关注数量
     */
    private int fans_num;
    /**
     * 剩余积分数量
     */
    private float pay_points;
    /**
     * 商家Id
     */
    private String shop_id;

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

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getHead_pic() {
        return head_pic == null ? "" : head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPush_message() {
        return push_message == null ? "" : push_message;
    }

    public void setPush_message(String push_message) {
        this.push_message = push_message;
    }


}
