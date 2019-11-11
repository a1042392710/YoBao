package com.jjz.energy.model.order;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.order.EvaluateDetailsBean;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import java.io.File;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @ author CH
 * @ fuction  评价
 */
public class EvaluateModel extends BaseModel {

    /**
     * 评价
     */
    public Flowable<String> putEvaluateInfo(String requestData, String file) {

        MultipartBody.Builder mBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(Api.PACK_NO, requestData);
        if (!StringUtil.isEmpty(file)) {
            File mFile = new File(file);
            mBuilder.addFormDataPart("img", mFile.getName(), RequestBody.create(MediaType.parse("image/png"), mFile));
        }

        return RetrofitFactory.getRetrofit().create(Api.class).putEvaluateInfo(mBuilder.build()).compose(RxSchedulerHepler.handleMyResult());
    }


    //查看评价
    public Flowable<EvaluateDetailsBean> getEvaluateDetails(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getEvaluateDetails(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
