package com.jjz.energy.model.jiusu;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.jiusu.OrderDetailBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 卖家订单详情
 * Date: 2018/11/22 下午5:21
 */
public class MineOrderDetailModel extends BaseModel {

    public Flowable<OrderDetailBean> getOrderDetail(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getOrderDetail(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    public Flowable<OrderDetailBean> getSellerOrderDetail(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getSellerOrderDetail(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    public Flowable<String> cancelOrder(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).cancelOrder(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}