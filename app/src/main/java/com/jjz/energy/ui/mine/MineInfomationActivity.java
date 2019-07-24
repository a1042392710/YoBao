package com.jjz.energy.ui.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 个人设置
 * @author: create by chenhao on 2019/7/16
 */
public class MineInfomationActivity extends BaseActivity {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_brithday)
    TextView tvBrithday;
    @BindView(R.id.ll_receipt_address)
    LinearLayout llReceiptAddress;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_infomation;
    }

    @Override
    protected void initView() {
    tvToolbarTitle.setText("个人资料");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }



    @OnClick({R.id.ll_toolbar_left, R.id.img_head, R.id.tv_nick_name, R.id.tv_sex,
            R.id.tv_brithday, R.id.ll_receipt_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //头像
            case R.id.img_head:
                break;
            //昵称
            case R.id.tv_nick_name:
                break;
            //简介
            case R.id.tv_desc:
                break;
            //性别
            case R.id.tv_sex:
                break;
            //生日
            case R.id.tv_brithday:
                break;
            //收货地址
            case R.id.ll_receipt_address:
                break;
        }
    }
}
