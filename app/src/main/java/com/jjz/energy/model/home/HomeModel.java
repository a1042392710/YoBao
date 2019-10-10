package com.jjz.energy.model.home;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.GoodsBean;
import com.jjz.energy.entry.HomeDetailBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @ author CH
 * @ fuction  首页
 */
public class HomeModel extends BaseModel {

    //分类列表
    public Flowable<HomeDetailBean> getSortAndBanner(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getHomeClassification(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //商品列表
    public Flowable<List<GoodsBean>> getGoodsList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getGoodsList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
