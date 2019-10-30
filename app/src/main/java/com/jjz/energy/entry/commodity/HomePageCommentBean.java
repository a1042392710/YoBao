package com.jjz.energy.entry.commodity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 个人主页 评价列表
 * @author: create by chenhao on 2019/10/28
 */
public class HomePageCommentBean implements Serializable {

        private int total_num;
        private int have_img_num;
        private int good_num;

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public int getHave_img_num() {
        return have_img_num;
    }

    public void setHave_img_num(int have_img_num) {
        this.have_img_num = have_img_num;
    }

    public int getGood_num() {
        return good_num;
    }

    public void setGood_num(int good_num) {
        this.good_num = good_num;
    }

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


        private String nickname;
        private String head_pic;
        private String mobile;
        private String content;
        private String img;
        private long add_time;
        private int start;

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }
    }
}
