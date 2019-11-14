package com.jjz.energy.model.home;

import com.jjz.energy.base.Api;
import com.jjz.energy.base.BaseModel;
import com.jjz.energy.entry.community.CommunityBean;
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
 * @ fuction  社区
 */
public class CommunityModel extends BaseModel {

    /**
     * 发布帖子
     */
    public Flowable<String> putPost(String requestData,  List<File> urls ) {

        MultipartBody.Part mBuilder = MultipartBody.Part.createFormData(Api.PACK_NO, requestData);
        Map<String, RequestBody> photos = new HashMap<>();
        if (urls.size() > 0) {
            for (int i = 0; i < urls.size(); i++) {
                photos.put("goods_images[]\"; filename=\"" + urls.get(i).getName(), RequestBody.create(MediaType.parse("image/png"), urls.get(i)));
            }
        }
        return RetrofitFactory.getRetrofit().create(Api.class).putPost(mBuilder, photos).compose(RxSchedulerHepler.handleMyResult());
    }

    //获取帖子列表
    public Flowable<CommunityBean> getPostList(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getPostList(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    //获取帖子详情
    public Flowable<String> getPostDeatails(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).getPostDeatails(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    //点赞
    public Flowable<String> putLike(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putPostLike(requestData).compose(RxSchedulerHepler.handleMyResult());
    }
    //评论
    public Flowable<String> putComment(String requestData) {
        return RetrofitFactory.getRetrofit().create(Api.class).putPostComment(requestData).compose(RxSchedulerHepler.handleMyResult());
    }

}
