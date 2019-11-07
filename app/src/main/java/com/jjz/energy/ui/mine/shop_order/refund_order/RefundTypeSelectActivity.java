package com.jjz.energy.ui.mine.shop_order.refund_order;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.order.ShopOrderDetailsBean;
import com.jjz.energy.util.glide.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 选择退货还是退款
 * @author: create by chenhao on 2019/11/4
 */
public class RefundTypeSelectActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_commodity)
    ImageView imgCommodity;
    @BindView(R.id.tv_commodity_title)
    TextView tvCommodityTitle;
    @BindView(R.id.img_test_one)
    ImageView imgTestOne;
    @BindView(R.id.tv_test_one)
    TextView tvTestOne;
    @BindView(R.id.tv_test_two)
    TextView tvTestTwo;
    @BindView(R.id.rl_only_refund)
    RelativeLayout rlOnlyRefund;
    @BindView(R.id.img_test_three)
    ImageView imgTestThree;
    @BindView(R.id.tv_test_four)
    TextView tvTestFour;
    @BindView(R.id.tv_test_five)
    TextView tvTestFive;
    @BindView(R.id.rl_sales_return)
    RelativeLayout rlSalesReturn;

    private ShopOrderDetailsBean mShopOrderDetailsBean;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_refund_type;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("选择服务方式");
        mShopOrderDetailsBean = (ShopOrderDetailsBean) getIntent().getSerializableExtra(Constant.INTENT_KEY_OBJECT);
        //防止为空
        if (mShopOrderDetailsBean==null){
            mShopOrderDetailsBean = new ShopOrderDetailsBean();
        }
        //商品图片
        GlideUtils.loadRoundCircleImage(mContext,mShopOrderDetailsBean.getGoods_images(),imgCommodity);
        //商品名称
        tvCommodityTitle.setText(mShopOrderDetailsBean.getGoods_name());
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }



    @OnClick({R.id.ll_toolbar_left, R.id.rl_only_refund, R.id.rl_sales_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //退款
            case R.id.rl_only_refund:
                startActivity(new Intent(mContext,ApplicationRefundActivity.class).putExtra(Constant.REC_ID,mShopOrderDetailsBean.getRec_id()).putExtra("type",Constant.RETURN_MONEY));
                break;
                //退货退款页面
            case R.id.rl_sales_return:
                startActivity(new Intent(mContext,ApplicationRefundActivity.class).putExtra(Constant.REC_ID,mShopOrderDetailsBean.getRec_id()).putExtra("type",Constant.RETURN_SALES));
                break;
        }
    }
}
