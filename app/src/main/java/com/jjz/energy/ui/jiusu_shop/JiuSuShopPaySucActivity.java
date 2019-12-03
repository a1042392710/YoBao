package com.jjz.energy.ui.jiusu_shop;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.jiusu_shop.Parms;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.glide.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 久速商家 店内消费 支付完成
 * @author: create by chenhao on 2019/11/30
 */
public class JiuSuShopPaySucActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.img_shop)
    ImageView imgShop;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    /**
     * 支付结果
     */
    private Parms mParms;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_shop_pay_suc;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("支付结果");
        mParms = (Parms) getIntent().getSerializableExtra(Constant.INTENT_KEY_OBJECT);
        if (mParms!=null){
            GlideUtils.loadRoundCircleImage(mContext,mParms.getShop_img(),imgShop);
            tvPayPrice.setText("¥" + mParms.getPrice_title() );
            tvShopName.setText("¥" + mParms.getShop_name() );
            tvPayTime.setText(DateUtil.longToDate(mParms.getPay_time()/1000L,null));
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
