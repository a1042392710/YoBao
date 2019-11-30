package com.jjz.energy.ui.shop_order.refund_order;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.mine.AddressBean;
import com.jjz.energy.presenter.order.RefundPresenter;
import com.jjz.energy.ui.mine.shipping_address.AddressManagerActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.order.IRefundView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 卖家同意退货
 * @author: create by chenhao on 2019/11/4
 */
public class SellerAgreeReturnActivity extends BaseActivity <RefundPresenter>implements IRefundView {


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
    /**
     * 售后id
     */
    private String return_id;
    /**
     * 订单sn
     */
    private String order_sn;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("卖家同意退货");
        return_id = getIntent().getStringExtra(Constant.RETURN_ID);
        order_sn = getIntent().getStringExtra(Constant.ORDER_SN);
        mPresenter.getAddressList(PacketUtil.getRequestPacket(null));
    }

    @Override
    public void isSellerGetAddressSuccess(AddressBean data) {
        if (!StringUtil.isListEmpty(data.getList())){
            seller_address_id = data.getList().get(0).getAddress_id();
            tvSellerNameAndPhone.setText(data.getList().get(0).getConsignee()+" "+data.getList().get(0).getMobile());
            tvSellerAddress.setText(data.getList().get(0).getFull_address());
        }else{
            tvSellerNameAndPhone.setText("去地址簿添加地址");
        }
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
                HashMap<String,String> map = new HashMap<>();
                map.put("address_id",seller_address_id+"");
                map.put("id",return_id);
                // 1 表示发送收货地址
                map.put("status",1+"");
                mPresenter.sellerPutExpressInfo(PacketUtil.getRequestPacket(map));
                break;
        }
    }

    @Override
    public void isSellerPutExpressInfoSuccess(String data) {
        showToast("地址已发送成功");
        finish();
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
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
    protected RefundPresenter getPresenter() {
        return new RefundPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_seller_agree_return;
    }
}
