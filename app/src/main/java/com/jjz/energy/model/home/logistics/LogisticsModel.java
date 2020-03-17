package com.jjz.energy.model.home.logistics;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.home.LogisticsBean;
import com.jjz.energy.entry.home.LogisticsListBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @ author CH
 * @ fuction  物流专区
 */
public class LogisticsModel extends BaseModel {

    //发布物流信息
    public Flowable<String> putLogisticsInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putLogisticsInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //查看物流列表
    public Flowable<LogisticsListBean> getLogisticsInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getLogisticsInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //查看物流详情
    public Flowable<LogisticsBean> getLogisticsDetail(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getLogisticsDetail(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    //查看物流详情
    public Flowable<String> cancleLogistics(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).cancleLogistics(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
