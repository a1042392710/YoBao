package com.jjz.energy.presenter.home;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.GoodsBean;
import com.jjz.energy.entry.HomeDetailBean;
import com.jjz.energy.model.home.HomeModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.home.IHomeView;

import java.util.List;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 首页
 */
public class HomePresenter extends BasePresenter<HomeModel, IHomeView> {


    public HomePresenter(IHomeView view) {
        initPresenter(view);
    }


    /**
     * 获取分类列表和Banner
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getSortAndBanner(String map) {

        addSubscribe(mModel.getSortAndBanner(map)
                .subscribeWith(new CommonSubscriber<HomeDetailBean>() {

                    @Override
                    protected void startLoading() {
                    }

                    @Override
                    protected void onSuccess(HomeDetailBean response) {
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

    /**
     * 商品列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getGoodsList(String map , boolean isLoadMore) {

        addSubscribe(mModel.getGoodsList(map)
                .subscribeWith(new CommonSubscriber<List<GoodsBean>>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(List<GoodsBean> response) {
                        mView.stopLoading();
                        mView.isGetGoodsSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isGetGoodsFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected HomeModel createModel() {
        return new HomeModel();
    }
}
