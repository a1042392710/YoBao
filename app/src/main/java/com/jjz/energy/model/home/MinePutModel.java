package com.jjz.energy.model.home;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.mine.LikeGoodsBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @ author CH
 * @ fuction  我的发布
 */
public class MinePutModel extends BaseModel {


    //我的发布
    public Flowable<LikeGoodsBean> getMinePutGoods(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getMinePutGoods(requestData).compose(RxSchedulerHepler.handleMyResult());
    }


    //下架商品
    public Flowable<String> downGoods(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).downGoods(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
