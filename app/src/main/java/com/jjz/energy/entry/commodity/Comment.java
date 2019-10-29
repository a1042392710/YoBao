package com.jjz.energy.entry.commodity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 第一层评论
 */
public class Comment implements Serializable {

    /**
     * 评论的主键Id
     */
    private long comment_id;
    /**
     * 用户id
     */
    private long user_id;
    /**
     * 留言时间
     */
    private long add_time;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 头像
     */
    private String head_pic;
    /**
     * 评论主体
     */
    private String content;

    public long getComment_id() {
        return comment_id;
    }

    public void setComment_id(long comment_id) {
        this.comment_id = comment_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHead_pic() {
        return head_pic == null ? "" : head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private List<ChildComment> reply_list;

    public List<ChildComment> getReply_list() {
        if (reply_list == null) {
            return new ArrayList<>();
        }
        return reply_list;
    }

    public void setReply_list(List<ChildComment> reply_list) {
        this.reply_list = reply_list;
    }

}