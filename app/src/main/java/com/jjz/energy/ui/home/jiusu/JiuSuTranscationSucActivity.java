package com.jjz.energy.ui.home.jiusu;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 结单完成
 * @author: create by chenhao on 2019/4/18
 */
public class JiuSuTranscationSucActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_finish)
    TextView tvFinish;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_transaction_suc;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("交易状态");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }



    @OnClick({R.id.ll_toolbar_left, R.id.tv_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
            case R.id.tv_finish:
                finish();
                break;
        }
    }
}
