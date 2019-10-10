package com.jjz.energy.presenter.home;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.GoodsClassificationBean;
import com.jjz.energy.model.home.PutCommodityModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.home.IPutCommodityView;

import java.io.File;
import java.util.List;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 发布宝贝
 */
public class PutCommodityPresenter extends BasePresenter<PutCommodityModel, IPutCommodityView> {


    public PutCommodityPresenter(IPutCommodityView view) {
        initPresenter(view);
    }


    /**
     * 发布宝贝
     */
    @SuppressLint("CheckResult")
    public void putCommodity(String data, List<File> files) {

        addSubscribe(mModel.putCommodity(data,files)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String loginBean) {
                        mView.stopLoading();
                        mView.isPutCommditySuccess(loginBean);
                    }

                    @Override
                   protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));
    }



    /**
     * 分类列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getClassification(String map) {

        addSubscribe(mModel.getClassification(map)
                .subscribeWith(new CommonSubscriber<List<GoodsClassificationBean>>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(List<GoodsClassificationBean> response) {
                        mView.stopLoading();
                        mView.isGetClassificationSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }


    @Override
    protected PutCommodityModel createModel() {
        return new PutCommodityModel();
    }
}
