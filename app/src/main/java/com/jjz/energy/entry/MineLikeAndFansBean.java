package com.jjz.energy.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 我的关注和我的粉丝
 * @author: create by chenhao on 2019/10/15
 */
public class MineLikeAndFansBean implements Serializable {

    private List<ListBean> list ;

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public  class ListBean implements Serializable{



        private int user_id;
        private String nickname;
        private String desc;
        private String head_pic;
        private int is_focus;


        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public String getHead_pic() {
            return head_pic == null ? "" : head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public int getIs_focus() {
            return is_focus;
        }

        public void setIs_focus(int is_focus) {
            this.is_focus = is_focus;
        }




    }



}
