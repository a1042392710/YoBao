package com.jjz.energy.model.home;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.commodity.GoodsListBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @ author CH
 * @ fuction  首页  商品
 */
public class HomeCommodityModel extends BaseModel {


    //商品列表
    public Flowable<GoodsListBean> getGoodsList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getGoodsList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
