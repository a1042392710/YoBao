package com.jjz.energy.entry.mine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features:
 * @author: create by chenhao on 2019/11/9
 */
public class OrderNoticeBean implements Serializable {

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

    public  class ListBean  implements Serializable{


    }
}
