package com.jjz.energy.ui.mine;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 设置登录密码
 * @author: create by chenhao on 2019/8/8
 */
public class MineSettingPasswordActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.et_input_one)
    EditText etInputOne;
    @BindView(R.id.ll_input_one)
    LinearLayout llInputOne;
    @BindView(R.id.et_input_two)
    EditText etInputTwo;
    @BindView(R.id.et_input_three)
    EditText etInputThree;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    /**
     * 是否是修改密码
     */
    private boolean isMotify = false;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_setting_password;
    }

    @Override
    protected void initView() {
        isMotify = getIntent().getBooleanExtra("isMotify", false);
        if (isMotify) {
            tvToolbarTitle.setText("修改登录密码");
            llInputOne.setVisibility(View.VISIBLE);
            etInputTwo.setHint("请输入新密码");
            etInputThree.setHint("请再次输入新密码");
        } else {
            tvToolbarTitle.setText("设置登录密码");
        }

    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }




    @OnClick({R.id.ll_toolbar_left, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //提交
            case R.id.tv_submit:
                break;
        }
    }
}
