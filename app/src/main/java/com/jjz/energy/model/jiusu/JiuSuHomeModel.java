package com.jjz.energy.model.jiusu;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.jiusu.MapMarkerBean;
import com.jjz.energy.entry.jiusu.ShopMarkerBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;
import com.jjz.energy.wxapi.OrderPayTypeBean;

import java.util.List;

import io.reactivex.Flowable;

/**
 * create 主页地图
 * Date: 2018/11/22 下午5:21
 */
public class JiuSuHomeModel extends BaseModel {


    //获取所有服务网点坐标信息
    public Flowable<List<MapMarkerBean>> getServiceMarkerInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getServiceMarkerInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取指定服务网点信息
    public Flowable<ShopMarkerBean> getShopInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getShopInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    //创建订单 获取油价
    public Flowable<OrderPayTypeBean> createOrder(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).createOrder(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
}