package com.jjz.energy.entry.home;

import java.io.Serializable;

/**
 * @Features: 首页bannner
 * @author: create by chenhao on 2019/6/17
 */
public class HomeBannerBean implements Serializable {

    private String bannerStr ;

    public HomeBannerBean(String bannerStr) {
        this.bannerStr = bannerStr;
    }

    public String getBannerStr() {
        return bannerStr == null ? "" : bannerStr;
    }

    public void setBannerStr(String bannerStr) {
        this.bannerStr = bannerStr;
    }
}
