package com.jjz.energy.presenter.mine;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.BindOwnerInfoBean;
import com.jjz.energy.model.mine.BindOwnerInfoModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IBindOwnerInfoView;

/**
 * create by: ch 车主信息
 * Date: 2018/9/17 下午4:22
 */
public class BindOwnerInfoPresenter extends BasePresenter<BindOwnerInfoModel, IBindOwnerInfoView> {


    public BindOwnerInfoPresenter(IBindOwnerInfoView view) {
        initPresenter(view);
    }


    /**
     * 提交车主信息
     */
    @SuppressLint("CheckResult")
    public void putBindInfo(String map) {

        addSubscribe(mModel.putBindInfo(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isPutSuccess(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }


    /**
     * 查询车主信息
     */
    @SuppressLint("CheckResult")
    public void getBindInfo(String map) {

        addSubscribe(mModel.getBindInfo(map)
                .subscribeWith(new CommonSubscriber<BindOwnerInfoBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(BindOwnerInfoBean response) {
                        mView.stopLoading();
                        mView.isGetSuccess(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected BindOwnerInfoModel createModel() {
        return new BindOwnerInfoModel();
    }

}