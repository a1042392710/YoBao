package com.jjz.energy.presenter.home;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.home.EntrustListBean;
import com.jjz.energy.model.home.EntrustListModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.home.IEntrustListView;
import com.jjz.energy.wxapi.OrderPayTypeBean;

/**
 * create by: 委托大厅
 * Date: 2018/9/17 下午4:22
 */
public class EntrustListPresenter extends BasePresenter<EntrustListModel, IEntrustListView> {


    public EntrustListPresenter(IEntrustListView view) {
        initPresenter(view);
    }


    /**
     * 获取委托
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getEntrustList(String map,boolean isLoadMore) {

        addSubscribe(mModel.getEntrustList(map)
                .subscribeWith(new CommonSubscriber<EntrustListBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(EntrustListBean response) {
                        mView.stopLoading();
                        mView.isGetEntrustListSuc(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 接受委托
     * @param map
     */
    @SuppressLint("CheckResult")
    public void accpetEnturst(String map) {

        addSubscribe(mModel.accpetEnturst(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isAccpetEntrustSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 查询我接受的委托
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getMineAccpetEntrustList(String map,boolean isLoadMore) {

        addSubscribe(mModel.getMineAccpetEntrustList(map)
                .subscribeWith(new CommonSubscriber<EntrustListBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(EntrustListBean response) {
                        mView.stopLoading();
                        mView.isGetEntrustListSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 查询我发布的委托
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getMinePutEntrustList(String map,boolean isLoadMore) {

        addSubscribe(mModel.getMinePutEntrustList(map)
                .subscribeWith(new CommonSubscriber<EntrustListBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(EntrustListBean response) {
                        mView.stopLoading();
                        mView.isGetEntrustListSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 发布委托
     * @param map
     */
    @SuppressLint("CheckResult")
    public void putEntrust(String map) {

        addSubscribe(mModel.putEntrust(map)
                .subscribeWith(new CommonSubscriber<OrderPayTypeBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(OrderPayTypeBean response) {
                        mView.stopLoading();
                        mView.isPutEntrustSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 完成委托
     * @param map
     */
    @SuppressLint("CheckResult")
    public void finishEntrust(String map) {

        addSubscribe(mModel.finishEntrust(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isFinishEntrustSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }
    /**
     * 取消委托
     * @param map
     */
    @SuppressLint("CheckResult")
    public void cancleEntrust(String map) {

        addSubscribe(mModel.cancleEntrust(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isCancleEntrustSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }
    @Override
    protected EntrustListModel createModel() {
        return new EntrustListModel();
    }

}