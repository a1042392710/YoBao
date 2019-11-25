package com.jjz.energy.entry.jiusu_shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 商家店内消费记录
 * @author: create by chenhao on 2019/11/9
 */
public class JiuSuShoppingBean implements Serializable {

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

    public  class  ListBean implements Serializable{

    }

}
