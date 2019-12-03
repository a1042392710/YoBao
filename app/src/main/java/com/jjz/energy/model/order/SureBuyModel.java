package com.jjz.energy.model.order;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.commodity.GoodsBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShop;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;
import com.jjz.energy.wxapi.OrderPayTypeBean;

import io.reactivex.Flowable;

/**
 * @ author CH
 * @ fuction  立即购买页面
 */
public class SureBuyModel extends BaseModel {


    //支付页获取商品信息
    public Flowable<GoodsBean> getGoodsInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getSureBuyInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //支付页 获取商家信息
    public Flowable<JiuSuShop> getShopsInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getShopsInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }


    //调起支付 获取支付信息
    public Flowable<OrderPayTypeBean> getBuyGoodsInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getBuyGoodsInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //调起支付 获取支付信息
    public Flowable<OrderPayTypeBean> getBuyShopsInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getBuyShopsInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }


}
