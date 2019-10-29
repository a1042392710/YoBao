package com.jjz.energy.entry.commodity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 留言的数据接收类
 * @author: create by chenhao on 2019/10/12
 */
public class CommentBean implements Serializable {

    private List<Comment> list;

    public List<Comment> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<Comment> list) {
        this.list = list;
    }



}
