package com.jjz.energy.ui.charitable;

import android.support.v7.widget.CardView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 公益慈善详情
 * @author: create by chenhao on 2019/7/2
 */
public class CharitableDetailActivity extends BaseActivity {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_unit_content)
    TextView tvUnitContent;
    @BindView(R.id.ll_unit_info)
    LinearLayout llUnitInfo;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.tv_all_love_money)
    TextView tvAllLoveMoney;
    @BindView(R.id.tv_love_number)
    TextView tvLoveNumber;
    @BindView(R.id.tv_get_love_number)
    TextView tvGetLoveNumber;
    @BindView(R.id.rl_money)
    RelativeLayout rlMoney;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.cv_donate_money)
    CardView cvDonateMoney;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_charitable_detail;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("项目详情");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }


    @OnClick({R.id.ll_toolbar_left, R.id.cv_donate_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.cv_donate_money:
                break;
        }
    }
}
