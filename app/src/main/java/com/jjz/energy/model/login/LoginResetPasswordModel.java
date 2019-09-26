package com.jjz.energy.model.login;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @author Ch
 * @fuction 忘记密码 重置密码
 */
public class LoginResetPasswordModel extends BaseModel {


    /**
     * 重置密码
     */
    public Flowable<LoginBean> resetPassword(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).resetPassword(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
