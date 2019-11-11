package com.jjz.energy.model.jiusu_shop;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopBean;
import com.jjz.energy.entry.jiusu_shop.ShopHomePageBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 我的
 * Date: 2018/11/22 下午5:21
 */
public class JiuSuShopModel extends BaseModel {

    //获取久速列表分类 和 推荐商家列表
    public Flowable<JiuSuShopBean> getShopList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getShopList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }


    //获取商家个人主页的数据
    public Flowable<ShopHomePageBean> getShopHomePage(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getShopHomePage(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}