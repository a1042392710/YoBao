package com.jjz.energy.model.jiusu_shop;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.commodity.HomePageCommentBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopClassBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShoppingBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShoppingDetailsBean;
import com.jjz.energy.entry.jiusu_shop.ShopHomePageBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 我的
 * Date: 2018/11/22 下午5:21
 */
public class JiuSuShopModel extends BaseModel {

    //获取推荐商家列表
    public Flowable<JiuSuShopBean> getShopList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getShopList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //搜索久速商家
    public Flowable<JiuSuShopBean> getSearchShopList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getSearchShopList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取久速列表分类
    public Flowable<JiuSuShopClassBean> getShopClass(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getShopClass(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取久速店内消费记录
    public Flowable<JiuSuShoppingBean> getJiuSuShoppingList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getJiuSuShoppingList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取商家个人主页的数据
    public Flowable<ShopHomePageBean> getShopHomePage(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getShopHomePage(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取商家个人主页的评价数据
    public Flowable<HomePageCommentBean> getShopCommentList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getShopCommentList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取商家个人主页的数据
    public Flowable<JiuSuShoppingDetailsBean> getJiuSuShoppingDetails(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getJiuSuShoppingDetails(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}