package com.jjz.energy.model.jiusu;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.jiusu.YoCardReceiveListBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 我的会员 赠品领取记录
 * Date: 2018/11/22 下午5:21
 */
public class JiuSuMineVipModel extends BaseModel {

    //获取记录
    public Flowable<YoCardReceiveListBean> getYoGiftList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getYoGiftList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
}




