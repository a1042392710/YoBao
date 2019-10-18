package com.jjz.energy.entry.commodity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 商品列表
 * @author: create by chenhao on 2019/10/10
 */
public class GoodsListBean implements Serializable {

    /**
     * 商品列表
     */
    private List<GoodsBean> list;

    public List<GoodsBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<GoodsBean> list) {
        this.list = list;
    }


}
