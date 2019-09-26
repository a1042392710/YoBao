package com.jjz.energy.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jjz.energy.util.system.LoadingDialogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Fragment基础类
 * Created by chenhao on 2018/11/22.
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {
    //得到类名
    protected final String TAG = this.getClass().getName();
    protected P mPresenter;
    /**
     * 根视图
     */
    protected View mRootView;

    /**
     * 上下文对象
     */
    protected Context mContext;
    /**
     * 吐司
     */
    protected Toast mToast;
    /**
     * 注解
     */
    private Unbinder unbinder;
    
    //********************************************* 生命周期

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attach(context);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        attach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(layoutId(), container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        mPresenter = getPresenter();
        mLoadingDialogUtil = new LoadingDialogUtil();

        return mRootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (unbinder != null) {
            unbinder.unbind(); //注解框架解绑
        }
        //取消 view 的绑定
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        //关闭弹窗
        mLoadingDialogUtil.cancelDialogForLoading();
    }


    private void attach(Context context) {
        mContext = context;
    }


    //********************************************* 实用方法

    /**
     * 短吐司
     */
    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * 长吐司
     */
    public void showToastLong(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        }
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }

    /**
     * 弹窗
     */
    private LoadingDialogUtil mLoadingDialogUtil;

    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        mLoadingDialogUtil.showDialogForLoading(getActivity(),true);
    }
    /**
     * 停止加载进度条
     */
    public void stopProgressDialog() {
        mLoadingDialogUtil.cancelDialogForLoading();
    }


    /**
     * 关闭刷新
     */
    protected void closeRefresh(SmartRefreshLayout view) {
        view.finishRefresh(300);
        view.finishLoadMore(300);
    }


    //********************************************* 子类实现

    protected abstract P getPresenter();

    //初始化
    protected abstract void initView();

    //布局ID
    protected abstract int layoutId();

}