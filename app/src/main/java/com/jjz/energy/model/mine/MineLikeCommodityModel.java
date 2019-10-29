package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.mine.LikeGoodsBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @author ch 2018 12/20
 * @function: 收藏列表
 */
public class MineLikeCommodityModel extends BaseModel {

    //获取我的收藏
    public Flowable<LikeGoodsBean> getLikeGoods(String requestData) {

        return RetrofitFactory.getRetrofit().create(Api.class).getLikeGoods(requestData).compose(RxSchedulerHepler.handleMyResult());

    }
    //取消收藏
    public Flowable<String> putCollect(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putCollect(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
}