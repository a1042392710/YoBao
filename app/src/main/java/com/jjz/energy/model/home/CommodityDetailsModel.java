package com.jjz.energy.model.home;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.commodity.GoodsDetailsBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @ author CH
 * @ fuction  商品详情
 */
public class CommodityDetailsModel extends BaseModel {

    //分类列表
    public Flowable<GoodsDetailsBean> getGoodsDetails(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getGoodsDetails(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
