package com.jjz.energy.entry.jiusu_shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 久速商家 推荐商家列表和商家分类
 * @author: create by chenhao on 2019/11/9
 */
public class JiuSuShopBean implements Serializable {

    private List<ListBean> shop_list;

    private List<ClassListBean> class_list;

    public List<ListBean> getShop_list() {
        if (shop_list == null) {
            return new ArrayList<>();
        }
        return shop_list;
    }

    public void setShop_list(List<ListBean> shop_list) {
        this.shop_list = shop_list;
    }

    public List<ClassListBean> getClass_list() {
        if (class_list == null) {
            return new ArrayList<>();
        }
        return class_list;
    }

    public void setClass_list(List<ClassListBean> class_list) {
        this.class_list = class_list;
    }

    public  class  ListBean implements Serializable{

    }

    public  class  ClassListBean implements Serializable{

        private String img;
        private String type;

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
