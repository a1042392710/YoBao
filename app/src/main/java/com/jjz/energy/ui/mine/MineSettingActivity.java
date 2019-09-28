package com.jjz.energy.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseWebActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.ui.MainActivity;
import com.jjz.energy.ui.mine.information.MineAccountsActivity;
import com.jjz.energy.ui.mine.information.MineInfomationActivity;
import com.jjz.energy.ui.mine.information.OwnerInfoActivity;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.util.system.SpUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

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

        //是否开启消息通知
        if ("2".equals(SpUtil.init(mContext).readString("is_notice"))){
            switchNotice.setChecked(false);
        }
        switchNotice.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //选中
            if (isChecked){
                JPushInterface.resumePush(getApplicationContext());
                SpUtil.init(mContext).commit("is_notice","1");
            }else{
                JPushInterface.stopPush(getApplicationContext());
                SpUtil.init(mContext).commit("is_notice","2");
            }
        });
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
                break;
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
                startActivity(new Intent(mContext, BaseWebActivity.class).putExtra("title","隐私政策").putExtra("url", Constant.PRIVACY_POLICY_URL));
                break;

            //退出登录
            case R.id.tv_login_out:
                PopWindowUtil.getInstance().showPopupWindow(mContext, "您确定退出登录吗？", () -> {
                    UserLoginBiz.getInstance(mContext).logout();
                    startActivity(new Intent(mContext, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                });
                break;
        }
    }
}
