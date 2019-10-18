package com.jjz.energy.ui.mine.shop_order;

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
 * @Features: 我要发货物
 * @author: create by chenhao on 2019/10/18
 */
public class DeliverGoodsActivity extends BaseActivity {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_cricle_seller)
    TextView tvCricleSeller;
    @BindView(R.id.tv_seller_name_and_phone)
    TextView tvSellerNameAndPhone;
    @BindView(R.id.tv_seller_address)
    TextView tvSellerAddress;
    @BindView(R.id.tv_buyer_name_and_phone)
    TextView tvBuyerNameAndPhone;
    @BindView(R.id.tv_cricle_buyer)
    TextView tvCricleBuyer;
    @BindView(R.id.tv_buyer_address)
    TextView tvBuyerAddress;
    @BindView(R.id.et_express_number)
    EditText etExpressNumber;
    @BindView(R.id.tv_express_company)
    TextView tvExpressCompany;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_deliver_goods;
    }

    @Override
    protected void initView() {
            tvToolbarTitle.setText("我要发货");
            tvToolbarRight.setText("无需寄件");
    }



    @OnClick({R.id.ll_toolbar_left, R.id.tv_express_company, R.id.tv_submit, R.id.tv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //选择物流公司
            case R.id.tv_express_company:

                break;
                //提交数据
            case R.id.tv_submit:
                submit();
                break;
                //无需寄件
            case R.id.tv_toolbar_right:
                break;
        }
    }

    /**
     * 提交数据
     */
    private void submit(){

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
