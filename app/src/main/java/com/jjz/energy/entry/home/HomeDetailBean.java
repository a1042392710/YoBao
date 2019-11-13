package com.jjz.energy.entry.home;

import com.jjz.energy.entry.commodity.GoodsClassificationBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features: 首页的数据
 * @author: create by chenhao on 2019/10/10
 */
public class HomeDetailBean implements Serializable {

    private int unread_num;


    private List<Banner> bannerList;
    private List<GoodsClassificationBean> cateList;

    public List<Banner> getBannerList() {
        if (bannerList == null) {
            return new ArrayList<>();
        }
        return bannerList;
    }

    public int getUnread_num() {
        return unread_num;
    }

    public void setUnread_num(int unread_num) {
        this.unread_num = unread_num;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public List<GoodsClassificationBean> getCateList() {
        if (cateList == null) {
            return new ArrayList<>();
        }
        return cateList;
    }

    public void setCateList(List<GoodsClassificationBean> cateList) {
        this.cateList = cateList;
    }
}
