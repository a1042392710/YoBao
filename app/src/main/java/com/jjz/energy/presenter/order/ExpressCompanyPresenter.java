package com.jjz.energy.presenter.order;

import android.annotation.SuppressLint;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.order.ExpressCompanyBean;
import com.jjz.energy.model.order.ExpressCompanyModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.order.IExpressCompanyView;

import java.util.List;

/**
 * @ author Ch
 * @ date  2019/9/7  15:59
 * @ fuction 选择快递公司
 */
public class ExpressCompanyPresenter extends BasePresenter<ExpressCompanyModel, IExpressCompanyView> {


    public ExpressCompanyPresenter(IExpressCompanyView view) {
        initPresenter(view);
    }



    /**
     * 查询所有的物流公司
     * @param map
     */
    @SuppressLint("CheckResult")
    public void getExpressCompany(String map) {

        addSubscribe(mModel.getExpressCompany(map)
                .subscribeWith(new CommonSubscriber<List<ExpressCompanyBean>>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(List<ExpressCompanyBean> response) {
                        mView.stopLoading();
                        mView.isGetExpressCompanySuc(response);
                    }

                    @Override
                    protected void onFail(String errorMsg ,boolean isNetAndSeriveError) {
                        mView.isFail(errorMsg,isNetAndSeriveError);
                        mView.stopLoading();
                    }
                }));

    }


    @Override
    protected ExpressCompanyModel createModel() {
        return new ExpressCompanyModel();
    }
}
