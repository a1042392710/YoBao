package com.jjz.energy.presenter.home;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.LikeGoodsBean;
import com.jjz.energy.model.home.MinePutModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.home.IMinePutView;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 我的发布列表
 */
public class MinePutPresenter extends BasePresenter<MinePutModel, IMinePutView> {


    public MinePutPresenter(IMinePutView view) {
        initPresenter(view);
    }


    /**
     * 我的商品列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getMinePutGoods(String map,boolean isLoadMore) {

        addSubscribe(mModel.getMinePutGoods(map)
                .subscribeWith(new CommonSubscriber<LikeGoodsBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(LikeGoodsBean response) {
                        mView.stopLoading();
                        mView.isGetMineCommditySuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }
    /**
     * 下架商品
     * @param map
     */
    @SuppressLint("CheckResult")
    public void downCommodity(String map) {

        addSubscribe(mModel.downGoods(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isDownGoodsSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected MinePutModel createModel() {
        return new MinePutModel();
    }
}
