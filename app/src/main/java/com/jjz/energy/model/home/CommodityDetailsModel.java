package com.jjz.energy.model.home;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.CommentBean;
import com.jjz.energy.entry.commodity.GoodsDetailsBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @ author CH
 * @ fuction  商品详情
 */
public class CommodityDetailsModel extends BaseModel {

    //分类列表
    public Flowable<GoodsDetailsBean> getGoodsDetails(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getGoodsDetails(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //评论列表
    public Flowable<CommentBean> getGoodsComment(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getGoodsComment(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    //发送评论
    public Flowable<String> putComment(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putComment(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    //添加收藏
    public Flowable<String> putCollect(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putCollect(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
