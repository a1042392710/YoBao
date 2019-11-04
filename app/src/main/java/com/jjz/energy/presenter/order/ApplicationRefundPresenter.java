package com.jjz.energy.presenter.order;

import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.order.ApplicationRefundBean;
import com.jjz.energy.model.order.ApplicationRefundModel;
import com.jjz.energy.util.networkUtil.CommonSubscriber;
import com.jjz.energy.view.order.IApplicationRefundView;

import java.io.File;
import java.util.List;

/**
 * @author chenhao 2018/9/20
 * @function 申请售后
 */
public class ApplicationRefundPresenter extends BasePresenter<ApplicationRefundModel, IApplicationRefundView> {


    public ApplicationRefundPresenter(IApplicationRefundView view) {
        initPresenter(view);
    }


    /**
     * 获取申请售后信息
     */
    public void getApplicationData(String pack_no) {

        addSubscribe( mModel.getApplicationRefundData(pack_no)
                .subscribeWith(new CommonSubscriber<ApplicationRefundBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(ApplicationRefundBean response) {
                        mView.isSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean isNewError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }

   /**
     * 提交售后
     */
    public void submitApplication(String pack_no, List<File> files) {

        addSubscribe( mModel.submitRefund(pack_no,files)
                .subscribeWith(new CommonSubscriber<ApplicationRefundBean>() {

                    @Override
                    protected void startLoading() {
                            mView.showLoading();
                    }

                    @Override
                    protected void onSuccess(ApplicationRefundBean response) {
                        mView.stopLoading();
                        mView.isSubmitSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg,boolean isNewError) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }



    @Override
    protected ApplicationRefundModel createModel() {
        return new ApplicationRefundModel();
    }

}
