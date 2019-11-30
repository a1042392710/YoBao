package com.jjz.energy.presenter.order;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.order.ShopOrderDetailsBean;
import com.jjz.energy.model.order.ShopOrderDetailsModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.order.IOrderDetalsView;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 订单详情
 */
public class ShopOrderDetailsPresenter extends BasePresenter<ShopOrderDetailsModel, IOrderDetalsView> {


    public ShopOrderDetailsPresenter(IOrderDetalsView view) {
        initPresenter(view);
    }


    /**
     * 获取订单详情
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getOrderDetails(String map) {

        addSubscribe(mModel.getOrderDetails(map)
                .subscribeWith(new CommonSubscriber<ShopOrderDetailsBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(ShopOrderDetailsBean response) {
                        mView.stopLoading();
                        mView.isGetOrderDetailsSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }


    /**
     * 确认收货
     */
    public void confirmReceipt(String pack_no) {

        addSubscribe(mModel.confirmReceipt(pack_no)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isConfirmReceiptSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg, boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg, isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));
    }

    /**
     * 提醒收货
     */
    public void remindReceipt(String pack_no) {

        addSubscribe(mModel.remindReceipt(pack_no)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isFail(response,false);
                    }

                    @Override
                    protected void onFail(String errorMsg, boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg, isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));
    }


    /**
     * 提醒发货
     */
    public void remindShipment(String pack_no) {

        addSubscribe(mModel.remindShipment(pack_no)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isFail(response,false);
                    }

                    @Override
                    protected void onFail(String errorMsg, boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg, isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));
    }


    /**
     * 取消订单
     */
    public void cancelOrder(String pack_no) {

        addSubscribe(mModel.cancelOrder(pack_no)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isCancelOrderSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg, boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg, isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }


    @Override
    protected ShopOrderDetailsModel createModel() {
        return new ShopOrderDetailsModel();
    }
}
