package com.jjz.energy.model.jiusu;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.jiusu.ShareInfoBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 分享邀请
 * Date: 2018/11/22 下午5:21
 */
public class ShareModel extends BaseModel {

    public Flowable<ShareInfoBean> getShareInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getShareInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}