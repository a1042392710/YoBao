package com.jjz.energy.presenter.jiusu;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.jiusu.VipListInfo;
import com.jjz.energy.model.jiusu.UpgradeMemberShipModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.jiusu.IUpgradeMemberShipView;
import com.jjz.energy.wxapi.OrderPayTypeBean;

import java.util.List;

/**
 * create by: 充值500元升级会员
 * Date: 2018/9/17 下午4:22
 */
public class UpgradeMemberShipPresenter extends BasePresenter<UpgradeMemberShipModel, IUpgradeMemberShipView> {


    public UpgradeMemberShipPresenter(IUpgradeMemberShipView view) {
        initPresenter(view);
    }

    /**
     * 升级会员
     *
     * @param map
     */
    @SuppressLint("CheckResult")
    public void upVip(String map, String file) {

        addSubscribe(mModel.upVip(map,file)
                .subscribeWith(new CommonSubscriber<OrderPayTypeBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(OrderPayTypeBean response) {
                        mView.isUpVipSuccess(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean i) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    /**
     * 获取会员信息
     *
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getVipInfo(String map) {

        addSubscribe(mModel.getVipInfo(map)
                .subscribeWith(new CommonSubscriber<List<VipListInfo>>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(List<VipListInfo> response) {
                        mView.isVipInfoSuccess(response);
                        mView.stopLoading();
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean i) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

    @Override
    protected UpgradeMemberShipModel createModel() {
        return new UpgradeMemberShipModel();
    }

}