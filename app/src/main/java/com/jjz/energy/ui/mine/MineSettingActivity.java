package com.jjz.energy.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 个人设置
 * @author: create by chenhao on 2019/7/24
 */
public class MineSettingActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.ll_information)
    LinearLayout llInformation;
    @BindView(R.id.switch_notice)
    Switch switchNotice;
    @BindView(R.id.ll_bind_car)
    LinearLayout llBindCar;
    @BindView(R.id.ll_acount)
    LinearLayout llAcount;
    TextView tvBindWechat;
    @BindView(R.id.ll_privacy)
    LinearLayout llPrivacy;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_setting;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("个人设置");
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @OnClick({R.id.ll_toolbar_left, R.id.ll_information, R.id.ll_bind_car, R.id.ll_acount,
            R.id.tv_login_out,
            R.id.ll_privacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_toolbar_left:
                finish();
            //个人资料
            case R.id.ll_information:
                startActivity(new Intent(mContext, MineInfomationActivity.class));
                break;

            //车辆绑定
            case R.id.ll_bind_car:
                startActivity(new Intent(mContext, OwnerInfoActivity.class));
                break;

            //收款账户
            case R.id.ll_acount:
                startActivity(new Intent(mContext, MineAccountsActivity.class));
                break;

            //隐私政策
            case R.id.ll_privacy:
                break;

            //退出登录
            case R.id.tv_login_out:

                break;
        }
    }
}
