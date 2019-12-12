package com.jjz.energy.presenter.jiusu;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu.YoCardReceiveListBean;
import com.jjz.energy.model.jiusu.JiuSuMineVipModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.jiusu.IJiuSuMineVipView;

/**
 * create by: 我的会员
 * Date: 2018/9/17 下午4:22
 */
public class JiuSuMineVipPresenter extends BasePresenter<JiuSuMineVipModel, IJiuSuMineVipView> {


    public JiuSuMineVipPresenter(IJiuSuMineVipView view) {
        initPresenter(view);
    }

    /**
     * 获取记录
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getYoGiftList(String map, boolean isShow) {

        addSubscribe(mModel.getYoGiftList(map)
                .subscribeWith(new CommonSubscriber<YoCardReceiveListBean>() {

                    @Override
                    protected void startLoading() {
                        if (isShow){
                            mView.showLoading();
                        }
                    }

                    @Override
                    protected void onSuccess(YoCardReceiveListBean response) {
                        mView.stopLoading();
                        mView.isListSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg , boolean i) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }
    @Override
    protected JiuSuMineVipModel createModel() {
        return new JiuSuMineVipModel();
    }

}