package com.jjz.energy.presenter.mine;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.MineInfoBean;
import com.jjz.energy.model.mine.MineModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IMineView;

/**
 * create by: 商城获取用户信息
 * Date: 2018/9/17 下午4:22
 */
public class MinePresenter extends BasePresenter<MineModel, IMineView> {


    public MinePresenter(IMineView view) {
        initPresenter(view);
    }


    /**
     * 获取用户信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getUserInfo(String map) {

        addSubscribe(mModel.getUserInfo(map)
                .subscribeWith(new CommonSubscriber<MineInfoBean>() {

                    @Override
                    protected void startLoading() {
                    }

                    @Override
                    protected void onSuccess(MineInfoBean response) {
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




    @Override
    protected MineModel createModel() {
        return new MineModel();
    }

}