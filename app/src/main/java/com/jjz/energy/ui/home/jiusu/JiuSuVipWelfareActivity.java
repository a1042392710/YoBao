package com.jjz.energy.ui.home.jiusu;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.jiusu.LoginBean;
import com.jjz.energy.util.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 会员福利
 * @author: create by chenhao on 2019/4/3
 */
public class JiuSuVipWelfareActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_update_vip)
    TextView tvUpdateVip;
    @BindView(R.id.webView)
    WebView webView;

    private String vipMoney;

    private LoginBean loginBean;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("会员福利");
         loginBean = (LoginBean) getIntent().getSerializableExtra(Constant.INTENT_KEY_OBJECT);
        //升级会员的文案
        String htmlStr = loginBean.getUp_vip_html();
        //升级会员需要的钱
        vipMoney = loginBean.getUp_vip_money();
        //加载文案
        if (StringUtil.isEmpty(htmlStr)) htmlStr = "";
        webView.loadDataWithBaseURL(null, htmlStr, "text/html", "utf-8", null);
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    protected int layoutId() {
        return R.layout.act_upgrade_membership;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_update_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //升级会员
            case R.id.tv_update_vip:
                break;
        }
    }

}
