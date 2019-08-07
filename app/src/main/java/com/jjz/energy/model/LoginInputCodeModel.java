package com.jjz.energy.model;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @ author Ch
 * @ fuction 获取验证码
 */
public class LoginInputCodeModel extends BaseModel {


    /**
     * 获取验证码
     */
    public Flowable<LoginBean> requestAuthCode(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getAuthCode(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    /**
     * 验证码登录
     */
    public Flowable<LoginBean> loginVCode(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).loginVCode(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    /**
     * 忘记密码  提交验证码
     */
    public Flowable<LoginBean> forgotPasswordPutVCode(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).forgotPasswordPutVCode(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
