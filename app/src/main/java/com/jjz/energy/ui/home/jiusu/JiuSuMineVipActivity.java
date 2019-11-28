package com.jjz.energy.ui.home.jiusu;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.enums.VipLevelEnum;
import com.jjz.energy.entry.jiusu.LoginBean;
import com.jjz.energy.ui.mine.MineIntegralActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 我的会员
 * @author: create by chenhao on 2019/7/3
 */
public class JiuSuMineVipActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_vip_name)
    TextView tvVipName;
    @BindView(R.id.tv_gift_text)
    TextView tvGiftText;
    @BindView(R.id.tv_gift)
    TextView tvGift;
    @BindView(R.id.tv_vip_level)
    TextView tvVipLevel;
    @BindView(R.id.ll_pay_jiusu)
    LinearLayout llPayJiusu;
    @BindView(R.id.ll_share_gift)
    LinearLayout llShareGift;
    @BindView(R.id.ll_up_vip)
    LinearLayout llUpVip;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_vip;
    }

   private LoginBean loginBean;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("我的会员");
        tvToolbarRight.setText("获取记录");
        loginBean = (LoginBean) getIntent().getSerializableExtra(Constant.INTENT_KEY_OBJECT);
        tvVipName.setText(loginBean.getNickname());
        tvVipLevel.setText(VipLevelEnum.getName(loginBean.getLevel_id()));
        GlideUtils.loadCircleImage(mContext, loginBean.getHead_pic(), imgHead);
        //剩余赠品
        String oil_balance = loginBean.getOil_balance()+"";
        tvGift.setText(oil_balance+"L");
        //分享推荐的文案
        String htmlStr = loginBean.getUp_vip_center();
        //加载文案
        if (StringUtil.isEmpty(htmlStr)) htmlStr = "";
        webView.loadDataWithBaseURL(null, htmlStr, "text/html", "utf-8", null);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_toolbar_right, R.id.ll_pay_jiusu, R.id.ll_share_gift,
            R.id.ll_up_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //领取记录
            case R.id.tv_toolbar_right:
                startActivity(new Intent(mContext, JiuSuYoCardReceiveListActivity.class));
                break;
                //我的积分
            case R.id.ll_pay_jiusu:
                startActivity(new Intent(mContext, MineIntegralActivity.class));
                break;
                //会员福利
            case R.id.ll_share_gift:
                startActivity(new Intent(mContext, JiuSuVipWelfareActivity.class).putExtra(Constant.INTENT_KEY_OBJECT,loginBean));
                break;
                //升级会员
            case R.id.ll_up_vip:
                startActivity(new Intent(mContext, JiuSuUpVipActivity.class).putExtra(Constant.INTENT_KEY_OBJECT,loginBean));
                break;
        }
    }
}
