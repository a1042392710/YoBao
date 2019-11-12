package com.jjz.energy.presenter.home;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.NoticeListInfo;
import com.jjz.energy.entry.mine.OrderNoticeBean;
import com.jjz.energy.model.home.NoticeModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.home.INoticeView;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 消息管理
 */
public class NoticePresenter extends BasePresenter<NoticeModel, INoticeView> {


    public NoticePresenter(INoticeView view) {
        initPresenter(view);
    }


    /**
     * 获取订单消息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getOrderNoticeList(String map,boolean isLoadMore) {

        addSubscribe(mModel.getOrderNoticeList(map)
                .subscribeWith(new CommonSubscriber<OrderNoticeBean>() {

                    @Override
                    protected void startLoading() {
                        if (!isLoadMore){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(OrderNoticeBean response) {
                        mView.stopLoading();
                        mView.isGetOrderNoticeSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 获取所有订单的最新信息
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getNoticeListInfo(String map,boolean isShowLoading) {

        addSubscribe(mModel.getNoticeListInfo(map)
                .subscribeWith(new CommonSubscriber<NoticeListInfo>() {

                    @Override
                    protected void startLoading() {
                        if (isShowLoading){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(NoticeListInfo response) {
                        mView.stopLoading();
                        mView.isGetNoticeListInfoSuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }



    @Override
    protected NoticeModel createModel() {
        return new NoticeModel();
    }
}
