package com.jjz.energy.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhao 2018/12/20
 * @function 我的收藏列表
 */
public class LikeGoodsBean implements Serializable {


    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    private List<ListBean> list;



    public  class ListBean implements Serializable{

    }
}
