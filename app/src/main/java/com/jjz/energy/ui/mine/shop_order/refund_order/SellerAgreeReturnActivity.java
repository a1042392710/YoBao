package com.jjz.energy.ui.mine.shop_order.refund_order;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.mine.AddressBean;
import com.jjz.energy.ui.mine.shipping_address.AddressManagerActivity;
import com.jjz.energy.util.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 卖家同意退货
 * @author: create by chenhao on 2019/11/4
 */
public class SellerAgreeReturnActivity extends BaseActivity {


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
    @BindView(R.id.tv_address_list)
    TextView tvAddressList;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("卖家同意退货");
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
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_seller_agree_return;
    }

    /**
     * 收货人的id
     */
    private int seller_address_id;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AddressManagerActivity.RESULT_ADDRESS) {
            AddressBean.ListBean bean = (AddressBean.ListBean) data.getSerializableExtra(Constant.INTENT_KEY_OBJECT);
            //显示收货地址
            String addressStr = bean.getArea() + bean.getAddress();
            //地址
            tvSellerAddress.setText(addressStr.replace(" ", ""));
            //地址id
            seller_address_id = bean.getAddress_id();
            //人名和电话
            tvSellerNameAndPhone.setText(bean.getConsignee() + " " + bean.getMobile());
        }
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_address_list, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //地址簿
            case R.id.tv_address_list:
                startActivityForResult(new Intent(mContext, AddressManagerActivity.class).putExtra(AddressManagerActivity.SELECT_ADDRESS,true),10);
                break;
                //发送退货地址
            case R.id.tv_sure:
                if (StringUtil.isEmpty(tvSellerNameAndPhone.getText().toString())){
                    showToast("请去地址簿添加地址");
                    return;
                }
                break;
        }
    }
}
