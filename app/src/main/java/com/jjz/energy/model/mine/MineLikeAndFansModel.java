package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.MineLikeAndFansBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 我的关注和我的粉丝
 * Date: 2018/11/22 下午5:21
 */
public class MineLikeAndFansModel extends BaseModel {

    //获取粉丝列表
    public Flowable<MineLikeAndFansBean> getFansList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getFansList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取关注列表
    public Flowable<MineLikeAndFansBean> getFocusList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getFocusList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //关注用户和取消
    public Flowable<String> setFocusUser(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).setFocusUser(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}