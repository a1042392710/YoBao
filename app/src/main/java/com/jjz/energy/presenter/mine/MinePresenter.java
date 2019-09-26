package com.jjz.energy.presenter.mine;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.LoginBean;
import com.jjz.energy.entry.OrderDetailBean;
import com.jjz.energy.model.mine.MineModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IPersonalInformationView;

/**
 * create by: 获取用户信息
 * Date: 2018/9/17 下午4:22
 */
public class MinePresenter extends BasePresenter<MineModel, IPersonalInformationView> {


    public MinePresenter(IPersonalInformationView view) {
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
                    }

                    @Override
                    protected void onSuccess(LoginBean response) {
                        mView.stopLoading();
                        mView.isSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        mView.isFail(errorMsg);
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
                    protected void onFail(String errorMsg) {
                        mView.stopLoading();
                        mView.isFail(errorMsg);
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
                    protected void onFail(String errorMsg) {
                        mView.stopLoading();
                        mView.isFail(errorMsg);
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
                    protected void onFail(String errorMsg) {
                        mView.stopLoading();
                        mView.isFail(errorMsg);
                    }
                }));

    }
    @Override
    protected MineModel createModel() {
        return new MineModel();
    }

}