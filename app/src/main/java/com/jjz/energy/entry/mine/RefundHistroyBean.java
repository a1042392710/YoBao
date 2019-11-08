package com.jjz.energy.entry.mine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 协商历史
 * @author: create by chenhao on 2019/11/8
 */
public class RefundHistroyBean implements Serializable {

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

    public  class ListBean implements Serializable{

        private String action_note;
        private String head_pic;
        private String nickname;
        private long log_time;
        private String imgs;

        public String getImgs() {
            return imgs == null ? "" : imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getAction_note() {
            return action_note == null ? "" : action_note;
        }

        public void setAction_note(String action_note) {
            this.action_note = action_note;
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

        public long getLog_time() {
            return log_time;
        }

        public void setLog_time(long log_time) {
            this.log_time = log_time;
        }
    }
}
