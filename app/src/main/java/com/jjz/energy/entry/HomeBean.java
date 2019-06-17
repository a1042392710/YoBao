package com.jjz.energy.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Features:
 * @author: create by chenhao on 2019/6/17
 */
public class HomeBean implements Serializable {

    public List<String> getBannerList() {
        if (bannerList == null) {
            return new ArrayList<>();
        }
        return bannerList;
    }

    public void setBannerList(List<String> bannerList) {
        this.bannerList = bannerList;
    }

    private List<String> bannerList;


}
