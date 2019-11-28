package com.jjz.energy.presenter.jiusu;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu.JiuSuOrderBean;
import com.jjz.energy.model.jiusu.MineSellerOrderModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.jiusu.IMineSellerOrderView;

/**
 * create by: 获取卖家订单
 * Date: 2018/9/17 下午4:22
 */
public class MineSellerOrderPresenter extends BasePresenter<MineSellerOrderModel, IMineSellerOrderView> {


    public MineSellerOrderPresenter(IMineSellerOrderView view) {
        initPresenter(view);
    }


    /**
     * 卖家订单列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getSellerOrderList(String map, boolean isShowLoading) {

        addSubscribe(mModel.getSellerOrderList(map)
                .subscribeWith(new CommonSubscriber<JiuSuOrderBean>() {

                    @Override
                    protected void startLoading() {
                        if (isShowLoading){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(JiuSuOrderBean response) {
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
     * 卖家接受订单
     * @param map
     */
    @SuppressLint("CheckResult")
    public void confirmOrder(String map) {

        addSubscribe(mModel.confirmOrder(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isConfirmOrderSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean e) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }
    @Override
    protected MineSellerOrderModel createModel() {
        return new MineSellerOrderModel();
    }

}