package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.mine.MineIntegralBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 我的积分
 * Date: 2018/11/22 下午5:21
 */
public class MineIntegralModel extends BaseModel {

    //获取积分
    public Flowable<MineIntegralBean> getIntegralList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getIntegralList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
}