package com.jjz.energy.entry;

/**
 * @Features: 商品分类
 * @author: create by chenhao on 2019/10/10
 */
public class GoodsClassificationBean {

    private int goods_type_id;
    private String goods_type_name;

    public int getGoods_type_id() {
        return goods_type_id;
    }

    public void setGoods_type_id(int goods_type_id) {
        this.goods_type_id = goods_type_id;
    }

    public String getGoods_type_name() {
        return goods_type_name == null ? "" : goods_type_name;
    }

    public void setGoods_type_name(String goods_type_name) {
        this.goods_type_name = goods_type_name;
    }
}
