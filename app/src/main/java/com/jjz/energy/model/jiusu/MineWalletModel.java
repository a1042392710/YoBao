package com.jjz.energy.model.jiusu;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.jiusu.CommissionDetailBean;
import com.jjz.energy.entry.jiusu.MineWalletBean;
import com.jjz.energy.entry.jiusu.MineWalletListBean;
import com.jjz.energy.entry.jiusu.WithdrawInfoBean;
import com.jjz.energy.entry.jiusu.WithdrawListBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import java.util.List;

import io.reactivex.Flowable;

/**
 * create 我的佣金
 * Date: 2018/11/22 下午5:21
 */
public class MineWalletModel extends BaseModel {

    //获取佣金余额
    public Flowable<MineWalletBean> getBalance(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getBalance(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取佣金记录
    public Flowable<List<MineWalletListBean>> getBalanceList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getBalanceList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //提交提现信息
    public Flowable<String> putWithdrawInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putWithdrawInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }



    //获取提现记录
    public Flowable<List<WithdrawListBean>> getWithdrawList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getWithdrawList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    //获取提现记录
    public Flowable<CommissionDetailBean> getCommissionDetail(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getCommissionDetail(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取上次银行卡提现记录
    public Flowable<WithdrawInfoBean> getWithdrawInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getWithdrawInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}