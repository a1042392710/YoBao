package com.jjz.energy.model;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 获取验证码
 * Date: 2018/11/22 下午5:21
 */
public class GetCodeModel extends BaseModel {

    public Flowable<String> getVCode(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getVCode(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    public Flowable<LoginBean> login(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).login(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    public Flowable<LoginBean> pwdLogin(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).pwdLogin(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
}