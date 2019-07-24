package com.jjz.energy.model;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.BindOwnerInfoBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 车主信息
 * Date: 2018/11/22 下午5:21
 */
public class BindOwnerInfoModel extends BaseModel {

    public Flowable<String> putBindInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putBindOwnerInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    public Flowable<BindOwnerInfoBean> getBindInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getBindOwnernfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
}