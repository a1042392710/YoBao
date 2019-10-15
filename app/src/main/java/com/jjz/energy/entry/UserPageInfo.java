package com.jjz.energy.entry;

import java.io.Serializable;

/**
 * @Features: 个人主页 用户信息
 * @author: create by chenhao on 2019/10/15
 */
public class UserPageInfo implements Serializable {

    /**
     * 昵称
     */
    private String nickname;
    /**
     * 简介
     */
    private String desc;
    /**
     * 用户ID
     */
    private int user_id;
    /**
     * 头像
     */
    private String head_pic;
    /**
     * 粉丝数
     */
    private int fans_num;
    /**
     * 关注数
     */
    private int focus_num;
    /**
     * 上次登录时间
     */
    private long last_time;
    /**
     * 是否已关注该用户  1 关注  0 未关注
     */
    private int is_focus;

    public int getIs_focus() {
        return is_focus;
    }

    public void setIs_focus(int is_focus) {
        this.is_focus = is_focus;
    }

    public int getFans_num() {
        return fans_num;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }

    public int getFocus_num() {
        return focus_num;
    }

    public void setFocus_num(int focus_num) {
        this.focus_num = focus_num;
    }

    public long getLast_time() {
        return last_time;
    }

    public void setLast_time(long last_time) {
        this.last_time = last_time;
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
}
