package com.jjz.energy.ui.mine.shop_order;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.ui.mine.shipping_address.AddressManagerActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 确定购买页面
 * @author: create by chenhao on 2019/9/26
 */
public class SureBuyActivity extends BaseActivity {

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
    @BindView(R.id.tv_commodity_price)
    TextView tvCommodityPrice;
    @BindView(R.id.tv_commodity_old_price)
    TextView tvCommodityOldPrice;
    @BindView(R.id.tv_shipping_address)
    TextView tvShippingAddress;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.tv_price_title)
    TextView tvPriceTitle;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("购买商品");
    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_shipping_address,
            R.id.tv_pay_type, R.id.tv_integral, R.id.tv_sure_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_toolbar_left:
                finish();
                break;
                //收货地址
            case R.id.tv_shipping_address:
                ActivityUtils.startActivity(AddressManagerActivity.class);
                break;
                //支付方式
            case R.id.tv_pay_type:
                break;
                //积分抵扣
            case R.id.tv_integral:
                break;
                //确认购买
            case R.id.tv_sure_buy:
                break;
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_sure_buy;
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }
}
