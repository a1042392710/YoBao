package com.jjz.energy.presenter.jiusu;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu.MapMarkerBean;
import com.jjz.energy.entry.jiusu.ShopMarkerBean;
import com.jjz.energy.model.jiusu.JiuSuHomeModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.jiusu.IJiuSuHomeView;
import com.jjz.energy.wxapi.OrderPayTypeBean;

import java.util.List;

/**
 * create by: 首页地图
 * Date: 2018/9/17 下午4:22
 */
public class JiuSuHomePresenter extends BasePresenter<JiuSuHomeModel, IJiuSuHomeView> {


    public JiuSuHomePresenter(IJiuSuHomeView view) {
        initPresenter(view);
    }


    /**
     * 查询所有网点
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getServiceMarkerInfo(String map) {

        addSubscribe(mModel.getServiceMarkerInfo(map)
                .subscribeWith(new CommonSubscriber<List<MapMarkerBean>>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(List<MapMarkerBean> response) {
                        mView.isSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean i) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 查询指定网点信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getShopInfo(String map) {

        addSubscribe(mModel.getShopInfo(map)
                .subscribeWith(new CommonSubscriber<ShopMarkerBean>() {

                    @Override
                    protected void startLoading() {}

                    @Override
                    protected void onSuccess(ShopMarkerBean response) {
                        mView.isShopInfoSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean i) {
                        mView.isFail(errorMsg);
                    }
                }));

    }
    /**
     * 创建订单 获取油价
     * @param map
     */
    @SuppressLint("CheckResult")
    public void createOrder(String map) {

        addSubscribe(mModel.createOrder(map)
                .subscribeWith(new CommonSubscriber<OrderPayTypeBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(OrderPayTypeBean response) {
                        mView.isCreateOrderSuccess(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean i) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected JiuSuHomeModel createModel() {
        return new JiuSuHomeModel();
    }

}