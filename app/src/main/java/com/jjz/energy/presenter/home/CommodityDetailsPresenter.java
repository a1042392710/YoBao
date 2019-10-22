package com.jjz.energy.presenter.home;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.CommentBean;
import com.jjz.energy.entry.commodity.GoodsDetailsBean;
import com.jjz.energy.model.home.CommodityDetailsModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.home.ICommodityView;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 商品详情
 */
public class CommodityDetailsPresenter extends BasePresenter<CommodityDetailsModel, ICommodityView> {


    public CommodityDetailsPresenter(ICommodityView view) {
        initPresenter(view);
    }


    /**
     * 获取商品详情
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getGoodsDetails(String map) {

        addSubscribe(mModel.getGoodsDetails(map)
                .subscribeWith(new CommonSubscriber<GoodsDetailsBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(GoodsDetailsBean response) {
                        mView.isGetGoodsDetailsSuc(response);
//                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 获取商品评论
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getGoodsComment(String map ,boolean isLoadMore) {

        addSubscribe(mModel.getGoodsComment(map)
                .subscribeWith(new CommonSubscriber<CommentBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(CommentBean response) {
                        mView.isGetCommentSuc(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }


    /**
     * 发送评论
     * @param map
     */
    @SuppressLint("CheckResult")
    public void putComment(String map) {

        addSubscribe(mModel.putComment(map)
                .subscribeWith(new CommonSubscriber<String>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(String response) {
                        mView.stopLoading();
                        mView.isPutCommentSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 收藏商品
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
    protected CommodityDetailsModel createModel() {
        return new CommodityDetailsModel();
    }
}
