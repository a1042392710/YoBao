package com.jjz.energy.presenter.order;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.commodity.GoodsBean;
import com.jjz.energy.entry.jiusu_shop.JiuSuShop;
import com.jjz.energy.model.order.SureBuyModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.order.ISureBuyView;
import com.jjz.energy.wxapi.OrderPayTypeBean;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 立即购买页面
 */
public class SureBuyPresenter extends BasePresenter<SureBuyModel, ISureBuyView> {


    public SureBuyPresenter(ISureBuyView view) {
        initPresenter(view);
    }


    /**
     * 立即购买页面  获取商品信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getGoodsInfo(String map) {

        addSubscribe(mModel.getGoodsInfo(map)
                .subscribeWith(new CommonSubscriber<GoodsBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(GoodsBean response) {
                        mView.stopLoading();
                        mView.isGetGoodsInfoSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 立即购买页面  获取商家信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getShopsInfo(String map) {

        addSubscribe(mModel.getShopsInfo(map)
                .subscribeWith(new CommonSubscriber<JiuSuShop>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(JiuSuShop response) {
                        mView.stopLoading();
                        mView.isGetShopsInfoSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     *  获取支付信息
     */
    @SuppressLint("CheckResult")
    public void getBuyGoodsInfo(String map) {

        addSubscribe(mModel.getBuyGoodsInfo(map)
                .subscribeWith(new CommonSubscriber<OrderPayTypeBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(OrderPayTypeBean response) {
                        mView.isGetBuyInfoSuccess(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg, boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected SureBuyModel createModel() {
        return new SureBuyModel();
    }
}
