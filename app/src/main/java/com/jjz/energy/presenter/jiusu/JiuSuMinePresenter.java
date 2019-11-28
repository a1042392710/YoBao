package com.jjz.energy.presenter.jiusu;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu.LoginBean;
import com.jjz.energy.entry.jiusu.OrderDetailBean;
import com.jjz.energy.model.mine.JiuSuMineModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IPersonalInformationView;

/**
 * create by: 久速 获取用户信息
 * Date: 2018/9/17 下午4:22
 */
public class JiuSuMinePresenter extends BasePresenter<JiuSuMineModel, IPersonalInformationView> {


    public JiuSuMinePresenter(IPersonalInformationView view) {
        initPresenter(view);
    }


    /**
     * 获取用户信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getUserInfo(String map) {

        addSubscribe(mModel.getUserInfo(map)
                .subscribeWith(new CommonSubscriber<LoginBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(LoginBean response) {
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
     * 完结订单
     * @param map
     */
    @SuppressLint("CheckResult")
    public void finishOrder(String map) {

        addSubscribe(mModel.finishOrder(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isFinishSuccess(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.stopLoading();
                        mView.isFail(errorMsg,isNetAndSeriveError);
                    }
                }));

    }

    /**
     * 查看订单详情
     * @param map
     */
    @SuppressLint("CheckResult")
    public void scanQrOrder(String map) {

        addSubscribe(mModel.scanQrOrder(map)
                .subscribeWith(new CommonSubscriber<OrderDetailBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(OrderDetailBean response) {
                        mView.stopLoading();
                        mView.isOrderInfoSuccess(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.stopLoading();
                        mView.isFail(errorMsg,isNetAndSeriveError);
                    }
                }));

    }
    /**
     * 提交推荐码
     * @param map
     */
    @SuppressLint("CheckResult")
    public void putTuiJianCode(String map) {

        addSubscribe(mModel.putTuiJianCode(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isPutTjCodeSuccess(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.stopLoading();
                        mView.isFail(errorMsg,isNetAndSeriveError);
                    }
                }));

    }
    @Override
    protected JiuSuMineModel createModel() {
        return new JiuSuMineModel();
    }

}