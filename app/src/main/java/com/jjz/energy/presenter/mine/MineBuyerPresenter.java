package com.jjz.energy.presenter.mine;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.mine.MineBuyerBean;
import com.jjz.energy.model.mine.MineBuyerModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IMineBuyerView;

/**
 * @author chenhao 2018/9/20
 * @function 我买到的
 */
public class MineBuyerPresenter extends BasePresenter<MineBuyerModel, IMineBuyerView> {


    public MineBuyerPresenter(IMineBuyerView view) {
        initPresenter(view);
    }


    /**
     * 获取我买到的商品
     */
    public void getMyBuyer(String pack_no,boolean isLoadMore) {

        addSubscribe( mModel.getMyBuyer(pack_no)
                .subscribeWith(new CommonSubscriber<MineBuyerBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }
                    @Override
                    protected void onSuccess(MineBuyerBean response) {
                        mView.stopLoading();
                        mView.isSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 获取我卖出的商品
     */
    public void getMySeller(String pack_no,boolean isLoadMore) {

        addSubscribe( mModel.getMySeller(pack_no)
                .subscribeWith(new CommonSubscriber<MineBuyerBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }
                    @Override
                    protected void onSuccess(MineBuyerBean response) {
                        mView.stopLoading();
                        mView.isSuccess(response);
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
                        mView.isFail("提醒成功",false);
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
                        mView.isFail("提醒成功",false);
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
    protected MineBuyerModel createModel() {
        return new MineBuyerModel();
    }

}
