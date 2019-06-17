package com.jjz.energy;

import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

public class MainActivity extends BaseActivity {


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }
}
