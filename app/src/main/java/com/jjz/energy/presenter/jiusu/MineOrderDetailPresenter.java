package com.jjz.energy.presenter.jiusu;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu.OrderDetailBean;
import com.jjz.energy.model.jiusu.MineOrderDetailModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.jiusu.IMineOrderDetailView;

/**
 * create by: 买家订单详情
 * Date: 2018/9/17 下午4:22
 */
public class MineOrderDetailPresenter extends BasePresenter<MineOrderDetailModel, IMineOrderDetailView> {


    public MineOrderDetailPresenter(IMineOrderDetailView view) {
        initPresenter(view);
    }


    /**
     * 获取订单详情
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getOrderDetail(String map) {

        addSubscribe(mModel.getOrderDetail(map)
                .subscribeWith(new CommonSubscriber<OrderDetailBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(OrderDetailBean response) {
                        mView.stopLoading();
                        mView.isSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean e) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }
    /**
     * 获取卖家订单详情
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getSellerOrderDetail(String map) {

        addSubscribe(mModel.getSellerOrderDetail(map)
                .subscribeWith(new CommonSubscriber<OrderDetailBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(OrderDetailBean response) {
                        mView.stopLoading();
                        mView.isSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean e) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 取消订单
     * @param map
     */
    @SuppressLint("CheckResult")
    public void cancleOrder(String map) {

        addSubscribe(mModel.cancelOrder(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isCancleOrderSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean e) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }
    @Override
    protected MineOrderDetailModel createModel() {
        return new MineOrderDetailModel();
    }

}