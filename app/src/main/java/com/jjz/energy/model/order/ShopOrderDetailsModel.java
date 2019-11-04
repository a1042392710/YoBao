package com.jjz.energy.model.order;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.order.ShopOrderDetailsBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @ author CH
 * @ fuction  订单详情
 */
public class ShopOrderDetailsModel extends BaseModel {


    //获取订单详情数据
    public Flowable<ShopOrderDetailsBean> getOrderDetails(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getShopOrderDetails(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //确认收货
    public Flowable<String> confirmReceipt(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).confirmReceipt(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //取消订单
    public Flowable<String> cancelOrder(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).cancelShopOrder(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
