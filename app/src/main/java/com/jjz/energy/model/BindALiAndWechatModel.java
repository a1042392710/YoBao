package com.jjz.energy.model;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.BindBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 绑定支付宝和微信信息
 * Date: 2018/11/22 下午5:21
 */
public class BindALiAndWechatModel extends BaseModel {

    public Flowable<String> putBindInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putBindInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    public Flowable<BindBean> getBindInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getBindInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
}