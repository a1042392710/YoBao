package com.jjz.energy.entry.community;

import java.io.Serializable;

/**
 * @Features: 社区 帖子的数据
 * @author: create by chenhao on 2019/11/14
 */
public class Community implements Serializable {
        private int id;
        private String content;
        private String images;
        private int top_num;
        private int msg_num;
        private long add_time;
        private String nickname;
        private String head_pic;
        private int is_like;

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public int getMsg_num() {
            return msg_num;
        }

        public void setMsg_num(int msg_num) {
            this.msg_num = msg_num;
        }

        public int getTop_num() {
            return top_num;
        }

        public void setTop_num(int top_num) {
            this.top_num = top_num;
        }

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

