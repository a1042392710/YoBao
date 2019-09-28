package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.MineAccountBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 我的收款账户
 * Date: 2018/11/22 下午5:21
 */
public class MineAccountsModel extends BaseModel {

    //获取收款账户信息
    public Flowable<MineAccountBean> getMineAccounts(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getMineAccounts(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取收款账户信息
    public Flowable<String> setDefaultAccount(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putBindInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}