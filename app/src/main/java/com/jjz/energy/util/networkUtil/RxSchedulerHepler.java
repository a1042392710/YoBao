package com.jjz.energy.util.networkUtil;


import com.jjz.energy.base.BaseApplication;
import com.jjz.energy.base.Constant;
import com.jjz.energy.base.LoginEventBean;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chenhao 2018/8/30
 * @function 线程转换辅助类
 */
public class RxSchedulerHepler {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<ResponseData<T>, T> handleMyResult() {   //compose判断结果
        return httpResponseFlowable -> httpResponseFlowable.flatMap((Function<ResponseData<T>, Flowable<T>>) response -> {
            //访问成功
            if (Constant.NETWORK_YES.equals(response.getCode())) {
                if (null!=response.getData()){
                    return createData(response.getData());
                }else{
                    return Flowable.error(new Exception(
                            response.getMessage()
                    ));
                }
            }else if ("-1".equals(response.getCode())) {
                //访问失败 判断用户是否已登录
                if (UserLoginBiz.getInstance(BaseApplication.getAppContext()).detectUserLoginStatus()){
                    //被挤下线后发送广播
                    UserLoginBiz.getInstance(BaseApplication.getAppContext()).logout();
                    EventBus.getDefault().post(new LoginEventBean(LoginEventBean.LOG_OUT));
                }
                return Flowable.error(new Exception(
                        response.getMessage()
                ));
            } else {
                return Flowable.error(new Exception(
                        response.getMessage()
                ));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 存储数据
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);
    }


}
