package com.jjz.energy.presenter.mine;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.mine.LikeGoodsBean;
import com.jjz.energy.model.mine.MineLikeCommodityModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.mine.IMineLikeCommodityView;

/**
 * @author chenhao 2018/9/20
 * @function 我的收藏夹
 */
public class MineLikeCommodityPresenter extends BasePresenter<MineLikeCommodityModel, IMineLikeCommodityView> {


    public MineLikeCommodityPresenter(IMineLikeCommodityView view) {
        initPresenter(view);
    }


    /**
     * 获取我的收藏
     */
    public void getLikeGoods(String pack_no,boolean isLoadMore) {

        addSubscribe( mModel.getLikeGoods(pack_no)
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
     * 取消收藏商品
     */
    @SuppressLint("CheckResult")
    public void putCollect(String map) {

        addSubscribe(mModel.putCollect(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isPutCollectSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }


    @Override
    protected MineLikeCommodityModel createModel() {
        return new MineLikeCommodityModel();
    }

}
