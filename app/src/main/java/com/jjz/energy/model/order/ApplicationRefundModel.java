package com.jjz.energy.model.order;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.order.ApplicationRefundBean;
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
 * @function 售后申请
 */
public class ApplicationRefundModel extends BaseModel {

    /**
     * 售后申请页面数据
     * @param requestData
     */

    public Flowable<ApplicationRefundBean> getApplicationRefundData(String requestData) {

        return RetrofitFactory.getRetrofit().create(Api.class).getApplicationRefundData(requestData).compose(RxSchedulerHepler.handleMyResult());

    }

    /**
     * 售后申请 提交售后申请
     * @param requestData
     */

    public Flowable<ApplicationRefundBean> submitRefund(String requestData, List<File> urls) {
        MultipartBody.Part mBuilder = MultipartBody.Part.createFormData(Api.PACK_NO, requestData);
        Map<String, RequestBody> photos = new HashMap<>();
        if (urls.size() > 0) {
            for (int i = 0; i < urls.size(); i++) {
                photos.put("imgs[]\"; filename=\"" + urls.get(i).getName(), RequestBody.create(MediaType.parse("image/png"), urls.get(i)));

            }
        }

        return  RetrofitFactory.getRetrofit().create(Api.class).submitRefund(mBuilder, "file", photos).compose(RxSchedulerHepler.handleMyResult());

    }


}
