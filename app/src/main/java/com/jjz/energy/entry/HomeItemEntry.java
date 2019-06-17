package com.jjz.energy.entry;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @Features: 首页的数据
 * @author: create by chenhao on 2019/6/17
 */
public class HomeItemEntry implements MultiItemEntity {
    //广告
    public static final int BANNER = 1;
    //Yo宝专区
    public static final int YOBAO = 2;
    //分类
    public static final int TYPE = 3;

    private int itemType;

    public HomeBean getHomeBean() {
        return mHomeBean;
    }

    public void setHomeBean(HomeBean homeBean) {
        mHomeBean = homeBean;
    }

    private HomeBean mHomeBean;

    public HomeItemEntry(int itemType, HomeBean homeBean) {
        this.itemType = itemType;
        this.mHomeBean = homeBean;
    }

    @Override
    public int getItemType() {
        return itemType;
    }


}
