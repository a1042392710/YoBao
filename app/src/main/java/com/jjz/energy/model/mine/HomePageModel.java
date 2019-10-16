package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.UserPageInfo;
import com.jjz.energy.entry.commodity.GoodsListBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * create 个人主页
 * Date: 2018/11/22 下午5:21
 */
public class HomePageModel extends BaseModel {

    //获取用户信息
    public Flowable<UserPageInfo> getUserPageInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getUserPageInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //关注用户和取消
    public Flowable<String> setFocusUser(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).setFocusUser(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取用户的所有商品
    public Flowable<GoodsListBean> getUserAllGoods(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getUserAllGoods(requestData).compose(RxSchedulerHepler.handleMyResult());
    }


}