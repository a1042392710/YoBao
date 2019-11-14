package com.jjz.energy.model;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.mine.LikeGoodsBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;


/**
 * @author chenhao 2018/9/20
 * @function 首页
 */
public class MainModel extends BaseModel {

    /**
     * 上传推送id
     * @param requestData
     */
    public Flowable<String> submitRegistrationId(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).submitRegistrationId(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    /**
     * 获取搜搜结果
     * @param requestData
     */
    public Flowable<LikeGoodsBean> getSearchGoodsResult(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getSearchGoodsResult(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
