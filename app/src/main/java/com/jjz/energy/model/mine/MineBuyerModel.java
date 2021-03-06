package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.entry.mine.MineBuyerBean;
import com.jjz.energy.model.order.ShopOrderDetailsModel;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @author ch 2018 12/20
 * @function: 我买到的
 */
public class MineBuyerModel extends ShopOrderDetailsModel {

    //获取我买到的
    public Flowable<MineBuyerBean> getMyBuyer(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getMyBuyer(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    //获取我卖出的
    public Flowable<MineBuyerBean> getMySeller(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getMySeller(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}