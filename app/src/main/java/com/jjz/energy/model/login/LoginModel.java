package com.jjz.energy.model.login;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @ author CH
 * @ date  2019/8/7  16:00
 * @ fuction
 */
public class LoginModel extends BaseModel {


    /**
     * 登录
     */
    public Flowable<UserInfo> passWordLogin(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).passWordLogin(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    /**
     * 第三方登录
     */
//    public Flowable<LoginBean> apiLogin(String requestData) {
//        return RetrofitFactory.getRetrofit().create(Api.class).apiLogin(requestData).compose(RxSchedulerHepler.handleMyResult());
//    }

}
