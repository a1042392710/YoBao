package com.jjz.energy.model.mine;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.jiusu.BindOwnerInfoBean;
import com.jjz.energy.ui.mine.information.OwnerInfoActivity;
import com.jjz.energy.util.networkUtil.RetrofitFactory;
import com.jjz.energy.util.networkUtil.RxSchedulerHepler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * create 车主信息
 * Date: 2018/11/22 下午5:21
 */
public class BindOwnerInfoModel extends BaseModel {

    public Flowable<String> putBindInfo(String requestData, List<OwnerInfoActivity.SubmitIdCardBean> files) {
        MultipartBody.Part mBuilder = MultipartBody.Part.createFormData(Api.PACK_NO, requestData);
        Map<String, RequestBody> photos = new HashMap<>();
        if (files.size() > 0) {
            for (int i = 0; i < files.size(); i++) {
                photos.put(files.get(i).getType()+"\"; filename=\"" + files.get(i).getFile().getName(),RequestBody.create(MediaType.parse("image/png"), files.get(i).getFile()));
            }
        }
        return RetrofitFactory.getRetrofit().create(Api.class).putBindOwnerInfo(mBuilder, photos).compose(RxSchedulerHepler.handleMyResult());
    }

    public Flowable<BindOwnerInfoBean> getBindInfo(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getBindOwnernfo(requestData).compose(RxSchedulerHepler.handleMyResult());
    }


}