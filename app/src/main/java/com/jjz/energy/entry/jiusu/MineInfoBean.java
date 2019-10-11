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
    private long user_id ;

    // 推送公告
    private String push_message;

    /**
     * 粉丝数量
      */
    private int fens_num;
    /**
     * 关注数量
     */
    private int like_num;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
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

    public int getFens_num() {
        return fens_num;
    }

    public void setFens_num(int fens_num) {
        this.fens_num = fens_num;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }
}
