package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.entry.OrderDetailBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 我的
 * Date: 2018/11/22 下午5:21
 */
public class JiuSuMineModel extends BaseModel {

    //获取用户信息
    public Flowable<LoginBean> getUserInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getJiuSuInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    //结单
    public Flowable<String> finishOrder(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).finishOrder(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    //查询卖家详情
    public Flowable<OrderDetailBean> scanQrOrder(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).scanQrOrder(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    //提交推荐码
    public Flowable<String> putTuiJianCode(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putTuiJianCode(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
}