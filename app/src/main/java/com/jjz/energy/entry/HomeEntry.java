package com.jjz.energy.entry;

import java.io.Serializable;

/**
 * @Features: 首页的数据
 * @author: create by chenhao on 2019/6/17
 */
public class HomeEntry implements Serializable {
   



    public HomeBannerBean getHomeBean() {
        return mHomeBean;
    }

    public void setHomeBean(HomeBannerBean homeBean) {
        mHomeBean = homeBean;
    }

    private HomeBannerBean mHomeBean;

    public HomeEntry(HomeBannerBean homeBean) {
        this.mHomeBean = homeBean;
    }



}
