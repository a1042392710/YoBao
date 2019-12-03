package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
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
 * @function 意见反馈
 */
public class FeedBackModel extends BaseModel {

    /**
     * 提交意见
     * @param requestData
     */
    public Flowable<String> feedbackSubmit(String requestData, List<File>urls) {
        MultipartBody.Part mBuilder = MultipartBody.Part.createFormData(Api.PACK_NO, requestData);
        Map<String, RequestBody> photos = new HashMap<>();
        if (urls.size() > 0) {
            for (int i = 0; i < urls.size(); i++) {
                photos.put("image[]\"; filename=\"" + urls.get(i).getName(), RequestBody.create(MediaType.parse("image/png"), urls.get(i)));
            }
        }
        return  RetrofitFactory.getRetrofit().create(Api.class).feedbackSubmit(mBuilder, photos).compose(RxSchedulerHepler.handleMyResult());

    }


}
