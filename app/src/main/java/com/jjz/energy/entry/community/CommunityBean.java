package com.jjz.energy.entry.community;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 社区 帖子列表
 * @author: create by chenhao on 2019/11/14
 */
public class CommunityBean implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public class ListBean implements Serializable {

        private int id;
        private String content;
        private String images;
        private String top_num;
        private String msg_num;
        private long add_time;
        private String nickname;
        private String head_pic;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImages() {
            return images == null ? "" : images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getTop_num() {
            return top_num == null ? "" : top_num;
        }

        public void setTop_num(String top_num) {
            this.top_num = top_num;
        }

        public String getMsg_num() {
            return msg_num == null ? "" : msg_num;
        }

        public void setMsg_num(String msg_num) {
            this.msg_num = msg_num;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

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
    }
}
