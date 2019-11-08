package com.jjz.energy.model.order;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.mine.AddressBean;
import com.jjz.energy.entry.mine.RefundHistroyBean;
import com.jjz.energy.entry.order.RefundDetailsBean;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author chenhao 2018/11/27
 * @function 退款退货
 */
public class RefundModel extends BaseModel {

    /**
     * 获取退款详情数据
     * @param requestData
     */
    public Flowable<RefundDetailsBean> getRefundDetails(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getRefundDetails(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    /**
     * 买家撤销退款申请
     * @param requestData
     */
    public Flowable<String> buyerCancelApplication(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).buyerCancelApplication(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    /**
     * 卖家同意退款
     * @param requestData
     */
    public Flowable<String> sellerAgreeReturnMoney(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).sellerAgreeReturnMoney(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    /**
     * 卖家同意退货，提交退货地址
     * @param requestData
     */
    public Flowable<String> sellerPutExpressInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).sellerPutExpressInfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取物流地址信息
    public Flowable<AddressBean> getAddressList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getAddressList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取协商历史
    public Flowable<RefundHistroyBean> getRefundHistoryList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getRefundHistoryList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

    /**
     * 买家提交售后信息
     * @param requestData
     */
    public Flowable<String> buyerPutExpressInfo(String requestData, List<File> urls) {
        MultipartBody.Part mBuilder = MultipartBody.Part.createFormData(Api.PACK_NO, requestData);
        Map<String, RequestBody> photos = new HashMap<>();
        if (urls.size() > 0) {
            for (int i = 0; i < urls.size(); i++) {
                photos.put("shipping_imgs[]\"; filename=\"" + urls.get(i).getName(), RequestBody.create(MediaType.parse("image/png"), urls.get(i)));
            }
        }
        return  RetrofitFactory.getRetrofit().create(Api.class).buyerPutExpressInfo(mBuilder,photos).compose(RxSchedulerHepler.handleMyResult());

    }


    /**
     * 卖家拒绝退款申请
     * @param requestData
     */
    public Flowable<String> sellerRefuseApplication(String requestData, List<File> urls) {
        MultipartBody.Part mBuilder = MultipartBody.Part.createFormData(Api.PACK_NO, requestData);
        Map<String, RequestBody> photos = new HashMap<>();
        if (urls.size() > 0) {
            for (int i = 0; i < urls.size(); i++) {
                photos.put("reject_imgs[]\"; filename=\"" + urls.get(i).getName(), RequestBody.create(MediaType.parse("image/png"), urls.get(i)));
            }
        }
        return  RetrofitFactory.getRetrofit().create(Api.class).sellerRefuseApplication(mBuilder,photos).compose(RxSchedulerHepler.handleMyResult());

    }
}
