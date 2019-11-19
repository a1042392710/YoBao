package com.jjz.energy.presenter.mine;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.mine.MineIntegralBean;
import com.jjz.energy.model.mine.MineIntegralModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IMineIntegralView;

/**
 * create by: 我的积分
 * Date: 2018/9/17 下午4:22
 */
public class MineIntegralPresenter extends BasePresenter<MineIntegralModel, IMineIntegralView> {


    public MineIntegralPresenter(IMineIntegralView view) {
        initPresenter(view);
    }


    /**
     * 获取积分信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getIntegralList(String map, boolean isLoadMore  ) {

        addSubscribe(mModel.getIntegralList(map)
                .subscribeWith(new CommonSubscriber<MineIntegralBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(MineIntegralBean response) {
                        mView.stopLoading();
                        mView.isGetIntegralSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean isNetError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }




    @Override
    protected MineIntegralModel createModel() {
        return new MineIntegralModel();
    }

}