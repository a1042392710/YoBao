package com.jjz.energy.entry.jiusu_shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 久速商家 推荐商家列表和商家分类
 * @author: create by chenhao on 2019/11/9
 */
public class JiuSuShopClassBean implements Serializable {


    private List<ClassListBean> list;

    public List<ClassListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ClassListBean> list) {
        this.list = list;
    }

    public  class  ClassListBean implements Serializable{

        private String image;
        private String mobile_name;
        private int id;

        public String getImage() {
            return image == null ? "" : image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMobile_name() {
            return mobile_name == null ? "" : mobile_name;
        }

        public void setMobile_name(String mobile_name) {
            this.mobile_name = mobile_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
