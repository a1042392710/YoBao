package com.jjz.energy.entry.commodity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 个人主页 评价列表
 * @author: create by chenhao on 2019/10/28
 */
public class HomePageCommentBean implements Serializable {


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



    }
}
