package com.jjz.energy.presenter.mine;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.BindBean;
import com.jjz.energy.model.mine.BindALiAndWechatModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IBindALiAndWechatView;

/**
 * create by: ch 绑定微信或支付宝信息
 * Date: 2018/9/17 下午4:22
 */
public class BindALiAndWechatPresenter extends BasePresenter<BindALiAndWechatModel, IBindALiAndWechatView> {


    public BindALiAndWechatPresenter(IBindALiAndWechatView view) {
        initPresenter(view);
    }


    /**
     * 提交微信或支付宝信息
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
                    protected void onFail(String errorMsg) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }


    /**
     * 查询已经绑定的信息
     */
    @SuppressLint("CheckResult")
    public void getBindInfo(String map) {

        addSubscribe(mModel.getBindInfo(map)
                .subscribeWith(new CommonSubscriber<BindBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(BindBean response) {
                        mView.stopLoading();
                        mView.isGetSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected BindALiAndWechatModel createModel() {
        return new BindALiAndWechatModel();
    }

}