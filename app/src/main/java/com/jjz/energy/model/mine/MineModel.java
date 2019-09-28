package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.MineInfoBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 我的
 * Date: 2018/11/22 下午5:21
 */
public class MineModel extends BaseModel {

    //获取用户信息
    public Flowable<MineInfoBean> getUserInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getMineInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}