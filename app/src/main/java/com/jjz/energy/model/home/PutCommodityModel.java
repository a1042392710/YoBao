package com.jjz.energy.model.home;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.GoodsClassificationBean;
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
 * @ author CH
 * @ fuction  发布商品
 */
public class PutCommodityModel extends BaseModel {


    /**
     * 发布商品
     */
    public Flowable<String> putCommodity(String requestData,  List<File> urls ) {

        MultipartBody.Part mBuilder = MultipartBody.Part.createFormData(Api.PACK_NO, requestData);
        Map<String, RequestBody> photos = new HashMap<>();
        if (urls.size() > 0) {
            for (int i = 0; i < urls.size(); i++) {
                photos.put("goods_images[]\"; filename=\"" + urls.get(i).getName(), RequestBody.create(MediaType.parse("image/png"), urls.get(i)));
            }
        }
        return RetrofitFactory.getRetrofit().create(Api.class).putCommodity(mBuilder, photos).compose(RxSchedulerHepler.handleMyResult());
    }


    //分类列表
    public Flowable<List<GoodsClassificationBean>> getClassification(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getClassification(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
