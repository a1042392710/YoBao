package com.jjz.energy.model.jiusu;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.jiusu.JiuSuOrderBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 卖家订单
 * Date: 2018/11/22 下午5:21
 */
public class MineSellerOrderModel extends BaseModel {

    public Flowable<JiuSuOrderBean> getSellerOrderList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getSellerOrderList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    public Flowable<String> confirmOrder(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).confirmOrder(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}