package com.jjz.energy.model.order;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.order.ExpressAddressInfoBean;
import com.jjz.energy.entry.order.ExpressCompanyBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @ author CH
 * @ fuction  物流的所有接口
 */
public class ExpressModel extends BaseModel {


    //快递公司列表
    public Flowable<List<ExpressCompanyBean>> getExpressCompany(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getExpressCompany(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //发货
    public Flowable<String> putExpressInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putExpressInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //见面交易
    public Flowable<String> putMeetingTransaction(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putMeetingTransaction(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取物流地址信息
    public Flowable<ExpressAddressInfoBean> getExpressAddressInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getExpressAddressInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取物流跟踪
    public Flowable<ExpressAddressInfoBean> getExpressTracking(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getExpressTracking(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
