package com.jjz.energy.util.networkUtil;

import android.util.Log;

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
        Log.e("网络请求异常走了这里", e + "");
        // 网络不可用
        if (!NetworkUtil.isNetConnected(BaseApplication.getAppContext())) {
            onFail("网络不可用,请检查你的网络");
        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            onFail("请求超时,请稍后再试");
        }
        // 服务器
        else if (e instanceof ServerException) {
            onFail("服务器访问出错");
        } else if (e instanceof HttpException) {
            onFail("服务器异常，请稍后再试");
        } else if (!StringUtils.isEmpty(e.getMessage())) {
            if (StringUtils.isEmpty(e.getMessage())) {
                onFail("接口访问错误");
            } else {
                onFail(e.getMessage());
            }
        }
        // 其它
        else {
            onFail(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }


    protected abstract void onFail(String errorMsg);

    protected abstract void onSuccess(T response);

    protected abstract void startLoading();


}
