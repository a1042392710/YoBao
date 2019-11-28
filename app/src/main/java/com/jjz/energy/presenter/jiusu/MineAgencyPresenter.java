package com.jjz.energy.presenter.jiusu;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu.AgencyBean;
import com.jjz.energy.model.jiusu.MineAgencyModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.jiusu.IMineAgencyView;

import java.util.List;

/**
 * create by: 查询我的下级代理
 * Date: 2018/9/17 下午4:22
 */
public class MineAgencyPresenter extends BasePresenter<MineAgencyModel, IMineAgencyView> {


    public MineAgencyPresenter(IMineAgencyView view) {
        initPresenter(view);
    }


    /**
     * 查询我的下级
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getAgency(String map, boolean isShowLoading) {

        addSubscribe(mModel.getAgency(map)
                .subscribeWith(new CommonSubscriber<List<AgencyBean>>() {

                    @Override
                    protected void startLoading() {
                        if (isShowLoading){
                            mView.showLoading();
                        }

                    }

                    @Override
                    protected void onSuccess(List<AgencyBean> response) {
                        mView.stopLoading();
                        mView.isSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean i) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }


    @Override
    protected MineAgencyModel createModel() {
        return new MineAgencyModel();
    }

}