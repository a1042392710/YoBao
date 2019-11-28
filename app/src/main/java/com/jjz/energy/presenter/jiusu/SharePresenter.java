package com.jjz.energy.presenter.jiusu;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu.ShareInfoBean;
import com.jjz.energy.model.jiusu.ShareModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.jiusu.IShareView;

/**
 * create by: 分享推荐
 * Date: 2018/9/17 下午4:22
 */
public class SharePresenter extends BasePresenter<ShareModel, IShareView> {


    public SharePresenter(IShareView view) {
        initPresenter(view);
    }


    /**
     * 卖家订单列表
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getShareInfo(String map) {

        addSubscribe(mModel.getShareInfo(map)
                .subscribeWith(new CommonSubscriber<ShareInfoBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(ShareInfoBean response) {
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
    protected ShareModel createModel() {
        return new ShareModel();
    }

}