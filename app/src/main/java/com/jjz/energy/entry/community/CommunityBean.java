package com.jjz.energy.entry.community;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 社区 帖子列表
 * @author: create by chenhao on 2019/11/14
 */
public class CommunityBean  implements Serializable {

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

    public class ListBean implements Serializable{

    }
}
