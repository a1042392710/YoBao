package com.jjz.energy.entry.community;

import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 社区帖子详情
 * @author: create by chenhao on 2019/11/14
 */
public class CommunityCommentBean {

    private int count;

    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        /**
         * content : 你瞅啥
         * from_pic : http://qiniu.jjznewenergy.com/2019_04_25_577037666
         * from_uid : 1022
         * from_username : 18807797527
         * reply_id : 1
         * reply_time : 1548653972
         * to_pic : http://qiniu.jjznewenergy.com/2019_05_08_9a7878013
         * to_uid : 1021
         * to_username : 17774814716
         */

        private String content;
        private String from_pic;
        private int from_uid;
        private String from_username;
        private int reply_id;
        private long reply_time;
        private String to_pic;
        private int to_uid;
        private String to_username;

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFrom_pic() {
            return from_pic == null ? "" : from_pic;
        }

        public void setFrom_pic(String from_pic) {
            this.from_pic = from_pic;
        }

        public int getFrom_uid() {
            return from_uid;
        }

        public void setFrom_uid(int from_uid) {
            this.from_uid = from_uid;
        }

        public String getFrom_username() {
            return from_username == null ? "" : from_username;
        }

        public void setFrom_username(String from_username) {
            this.from_username = from_username;
        }

        public int getReply_id() {
            return reply_id;
        }

        public void setReply_id(int reply_id) {
            this.reply_id = reply_id;
        }

        public long getReply_time() {
            return reply_time;
        }

        public void setReply_time(long reply_time) {
            this.reply_time = reply_time;
        }

        public String getTo_pic() {
            return to_pic == null ? "" : to_pic;
        }

        public void setTo_pic(String to_pic) {
            this.to_pic = to_pic;
        }

        public int getTo_uid() {
            return to_uid;
        }

        public void setTo_uid(int to_uid) {
            this.to_uid = to_uid;
        }

        public String getTo_username() {
            return to_username == null ? "" : to_username;
        }

        public void setTo_username(String to_username) {
            this.to_username = to_username;
        }
    }

}
