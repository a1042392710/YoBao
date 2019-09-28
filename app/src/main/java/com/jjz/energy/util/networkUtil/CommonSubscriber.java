package com.jjz.energy.util.networkUtil;

import com.blankj.utilcode.util.StringUtils;
import com.jjz.energy.base.BaseApplication;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * @author CH
 * @funtion 进行异常信息的处理
 */
public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private String mErrorMsg;


    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startLoading();
    }

    @Override
    public void onError(Throwable e) {
        //错误信息的处理
        // 网络不可用
        if (!NetworkUtil.isNetConnected(BaseApplication.getAppContext())) {
            onFail("网络不可用,请检查你的网络",true);
        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            onFail("请求超时,请稍后再试",true);
        }
        // 服务器
        else if (e instanceof ServerException) {
            onFail("服务器访问出错",true);
        } else if (e instanceof HttpException) {
            onFail("服务器异常，请稍后再试",true);
        } else if (!StringUtils.isEmpty(e.getMessage())) {
            if (StringUtils.isEmpty(e.getMessage())) {
                onFail("接口访问错误",true);
            } else {
                onFail(e.getMessage(),false);
            }
        }
        // 其它
        else {
            onFail(e.getMessage(),false);
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * @param errorMsg 错误信息
     * @param isNetAndServiceError 是否是网络或服务器错误
     */
    protected abstract void onFail(String errorMsg,boolean isNetAndServiceError);

    protected abstract void onSuccess(T response);

    protected abstract void startLoading();


}
