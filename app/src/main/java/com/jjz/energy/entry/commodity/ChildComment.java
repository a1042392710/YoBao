package com.jjz.energy.entry.commodity;

import java.io.Serializable;

/**
 * @Features: 第二层评论
 * @author: create by chenhao on 2019/10/12
 */
public class ChildComment implements Serializable {

    /**
     * 二级评论的主键Id
     */
    private long reply_id;
    /**
     * 发送方 id
     */
    private long from_uid;
    /**
     * 发送方 name
     */
    private String from_username;
    /**
     * 发送方 头像
     */
    private String from_pic;
    /**
     * 接收方 id
     */
    private long to_uid;
    /**
     * 接收方 name
     */
    private String to_username;
    /**
     * 发送时间
     */
    private long reply_time;
    /**
     * 发送内容
     */
    private String content;


    public long getReply_id() {
        return reply_id;
    }

    public void setReply_id(long reply_id) {
        this.reply_id = reply_id;
    }

    public long getFrom_uid() {
        return from_uid;
    }

    public void setFrom_uid(long from_uid) {
        this.from_uid = from_uid;
    }

    public String getFrom_username() {
        return from_username == null ? "" : from_username;
    }

    public void setFrom_username(String from_username) {
        this.from_username = from_username;
    }

    public String getFrom_pic() {
        return from_pic == null ? "" : from_pic;
    }

    public void setFrom_pic(String from_pic) {
        this.from_pic = from_pic;
    }

    public long getTo_uid() {
        return to_uid;
    }

    public void setTo_uid(long to_uid) {
        this.to_uid = to_uid;
    }

    public String getTo_username() {
        return to_username == null ? "" : to_username;
    }

    public void setTo_username(String to_username) {
        this.to_username = to_username;
    }

    public long getReply_time() {
        return reply_time;
    }

    public void setReply_time(long reply_time) {
        this.reply_time = reply_time;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
