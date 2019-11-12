package com.jjz.energy.model.home;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.NoticeListInfo;
import com.jjz.energy.entry.mine.OrderNoticeBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import io.reactivex.Flowable;

/**
 * @ author CH
 * @ fuction  消息管理
 */
public class NoticeModel extends BaseModel {


    //获取订单消息列表
    public Flowable<OrderNoticeBean> getOrderNoticeList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getOrderNoticeList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取所有订单的最新消息
    public Flowable<NoticeListInfo> getNoticeListInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getNoticeListInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
