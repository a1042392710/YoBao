package com.jjz.energy.model;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;


/**
 * @author chenhao 2018/9/20
 * @function 首页
 */
public class MainModel extends BaseModel {

    /**
     * 上传推送id
     * @param requestData
     */
    public Flowable<String> submitRegistrationId(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).submitRegistrationId(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
