package com.jjz.energy.entry.community;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 社区 帖子列表
 * @author: create by chenhao on 2019/11/14
 */
public class CommunityBean implements Serializable {

    private List<Community> list;
    private int  count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Community> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<Community> list) {
        this.list = list;
    }

}
