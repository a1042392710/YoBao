package com.jjz.energy.presenter.home.logistics;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.home.LogisticsBean;
import com.jjz.energy.entry.home.LogisticsListBean;
import com.jjz.energy.model.home.logistics.LogisticsModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.home.ILogisticsView;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 物流专区
 */
public class LogisticsPresenter extends BasePresenter<LogisticsModel, ILogisticsView> {


    public LogisticsPresenter(ILogisticsView view) {
        initPresenter(view);
    }


    /**
     * 发布物流信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void putLogisticsInfo(String map) {

        addSubscribe(mModel.putLogisticsInfo(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.isPutLogisticsInfoSuc(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }
    /**
     * 撤销物流信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void cancleLogistics(String map) {

        addSubscribe(mModel.cancleLogistics(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.isCancleLogisticsSuc(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }
    /**
     * 获取物流列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getLogisticsInfo(String map,boolean isLoadMore) {

        addSubscribe(mModel.getLogisticsInfo(map)
                .subscribeWith(new CommonSubscriber<LogisticsListBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(LogisticsListBean response) {
                        mView.isGetLogisticsInfoSuc(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }
    /**
     * 获取物流详情
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getLogisticsDetail(String map) {

        addSubscribe(mModel.getLogisticsDetail(map)
                .subscribeWith(new CommonSubscriber<LogisticsBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(LogisticsBean response) {
                        mView.isGetLogisticsDetailSuc(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }
    @Override
    protected LogisticsModel createModel() {
        return new LogisticsModel();
    }
}
