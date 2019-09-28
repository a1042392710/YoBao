package com.jjz.energy.presenter.mine;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.MineAccountBean;
import com.jjz.energy.model.mine.MineAccountsModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IMineAccountsView;

/**
 * create by: 收款账户
 * Date: 2018/9/17 下午4:22
 */
public class MineAccountsPresenter extends BasePresenter<MineAccountsModel, IMineAccountsView> {


    public MineAccountsPresenter(IMineAccountsView view) {
        initPresenter(view);
    }


    /**
     * 获取收款账户信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getMineAccounts(String map) {

        addSubscribe(mModel.getMineAccounts(map)
                .subscribeWith(new CommonSubscriber<MineAccountBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(MineAccountBean response) {
                        mView.stopLoading();
                        mView.isGetInfoSuccess(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 设置默认收款账户
     * @param map
     */
    @SuppressLint("CheckResult")
    public void setDefaultAccount(String map) {

        addSubscribe(mModel.setDefaultAccount(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isPutAccountSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }


    @Override
    protected MineAccountsModel createModel() {
        return new MineAccountsModel();
    }

}