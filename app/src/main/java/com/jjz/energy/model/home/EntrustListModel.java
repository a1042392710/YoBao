package com.jjz.energy.model.home;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.home.EntrustListBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;
import com.jjz.energy.wxapi.OrderPayTypeBean;

import io.reactivex.Flowable;

/**
 * create 委托大厅
 * Date: 2018/11/22 下午5:21
 */
public class EntrustListModel extends BaseModel {

    //获取委托大厅列表
    public Flowable<EntrustListBean> getEntrustList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getEntrustList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取我接受的委托
    public Flowable<EntrustListBean> getMineAccpetEntrustList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getMineAccpetEntrustList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取我发布的委托
    public Flowable<EntrustListBean> getMinePutEntrustList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getMinePutEntrustList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //发布委托
    public Flowable<OrderPayTypeBean> putEntrust(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putEntrust(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //接受委托
    public Flowable<String> accpetEnturst(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).accpetEnturst(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //完成委托
    public Flowable<String> finishEntrust(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).finishEntrust(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //取消委托
    public Flowable<String> cancleEntrust(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).cancleEntrust(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}