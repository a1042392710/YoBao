package com.jjz.energy.presenter.mine;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.model.mine.MineInformationModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IMineInfomationView;

/**
 * create by: 修改个人信息
 * Date: 2018/9/17 下午4:22
 */
public class MineInformationPresenter extends BasePresenter<MineInformationModel, IMineInfomationView> {


    public MineInformationPresenter(IMineInfomationView view) {
        initPresenter(view);
    }

    /**
     * 获取用户信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getUserInfo(String map) {

        addSubscribe(mModel.getUserInfo(map)
                .subscribeWith(new CommonSubscriber<UserInfo>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(UserInfo response) {
                        mView.stopLoading();
                        mView.isGetInfoSuccess(response);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.stopLoading();
                        mView.isFail(errorMsg,isNetAndSeriveError);
                    }
                }));

    }

    /**
     * 提交或修改个人信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void putUserInfo(String map, String file) {

        addSubscribe(mModel.putUserInfo(map,file)
                .subscribeWith(new CommonSubscriber<UserInfo>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(UserInfo response) {
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
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected MineInformationModel createModel() {
        return new MineInformationModel();
    }

}