package com.jjz.energy.view.jiusu_shop;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopBean;
import com.jjz.energy.entry.jiusu_shop.ShopHomePageBean;

/**
 * create 久速商家
 * Date: 2018/9/17 下午4:22
 */
public interface IJiuSuShopView extends IBaseView {

    //获取久速推荐商家和商家分类
    default void isGetClassAndShopListSuccess(JiuSuShopBean data) {

    }

    //获取商家个人主页信息
    default void isGetShopHomePageSuccess(ShopHomePageBean data) {

    }

    void isFail(String msg, boolean isNetAndServiceError);


}