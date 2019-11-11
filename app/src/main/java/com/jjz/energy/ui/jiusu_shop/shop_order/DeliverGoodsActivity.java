package com.jjz.energy.ui.jiusu_shop.shop_order;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.mine.AddressBean;
import com.jjz.energy.entry.order.ExpressAddressInfoBean;
import com.jjz.energy.entry.order.ExpressCompanyBean;
import com.jjz.energy.presenter.order.ExpressPresenter;
import com.jjz.energy.ui.mine.shipping_address.AddressManagerActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.order.IExpressView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 我要发货
 * @author: create by chenhao on 2019/10/18
 */
public class DeliverGoodsActivity extends BaseActivity<ExpressPresenter>implements IExpressView {


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
    @BindView(R.id.tv_address_list)
    TextView tvAddressList;

    /**
     * 订单编号
     */
    private String order_sn;

    @Override
    protected void initView() {
        order_sn = getIntent().getStringExtra(Constant.ORDER_SN);
        tvToolbarTitle.setText("我要发货");
        tvToolbarRight.setText("无需寄件");
        getExpressInfo();
    }

    /**
     * 获取物流地址信息
     */
    private void getExpressInfo(){
        mPresenter.getExpressAddressInfo(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.ORDER_SN,order_sn)));
    }

    /**
     * 面交
     */
    private void putMeetingTransaction(){
        HashMap<String,String>map = new HashMap<>();
        map.put(Constant.ORDER_SN,order_sn);
        //正常快递
        map.put("act","self");
        mPresenter.putExpressInfo(PacketUtil.getRequestPacket(map));
    }

    /**
     * 发货
     */
    private void putExpressInfo(){
        if (StringUtil.isEmpty(tvSellerNameAndPhone.getText().toString())){
            showToast("请去地址簿添加地址");
            return;
        }
        if (StringUtil.isEmpty(etExpressNumber.getText().toString())){
            showToast("请填写物流单号");
            return;
        }
        if (mExpressCompanyBean==null){
            showToast("请选择物流公司");
            return;
        }

        String toast= "请确认您的物流单号和物流公司是否填写准确。\n\n快递单号："+etExpressNumber.getText().toString()+"\n物流公司："+mExpressCompanyBean.getName();
        PopWindowUtil.getInstance().showPopupWindow(mContext, toast, () -> {
            HashMap<String,String>map = new HashMap<>();
            map.put(Constant.ORDER_SN,order_sn);
            //正常快递
            map.put("act","send");
            map.put("shipping_no",etExpressNumber.getText().toString());
            map.put("shipping_code",mExpressCompanyBean.getId());
            map.put("address_id",seller_address_id+"");
            mPresenter.putExpressInfo(PacketUtil.getRequestPacket(map));
        });
    }


    @Override
    public void isGetExpressAddressInfoSuc(ExpressAddressInfoBean data) {
        //寄件人的 address_id
        seller_address_id = data.getSend_info().getAddress_id();
        //收货人信息
        tvBuyerAddress.setText(data.getFull_address());
        //收货人名字电话
        tvBuyerNameAndPhone.setText(data.getConsignee()+" " + data.getMobile());

        if (null==data.getSend_info()) {
            tvSellerNameAndPhone.setText("去地址簿添加地址");
        }else{
            tvSellerNameAndPhone.setText(data.getSend_info().getConsignee()+" "+data.getSend_info().getMobile());
            tvSellerAddress.setText(data.getSend_info().getFull_address());
        }
    }

    @Override
    public void isPutExpressInfoSuc(String data) {
        showToast("发货成功");
        finish();

    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_express_company, R.id.tv_submit, R.id.tv_toolbar_right, R.id.tv_address_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //选择物流公司
            case R.id.tv_express_company:
                startActivityForResult(new Intent(mContext, ExpressCompanyActivity.class), Constant.SELECT_COMPANY_CODE);
                break;
            //提交数据
            case R.id.tv_submit:
                putExpressInfo();
                break;
                //无需寄件
            case R.id.tv_toolbar_right:
                PopWindowUtil.getInstance().showPopupWindow(mContext, "是否无需寄件，选择见面交易？", () -> {
                    putMeetingTransaction();
                });
                break;
                //地址簿
            case R.id.tv_address_list:
                startActivityForResult(new Intent(mContext, AddressManagerActivity.class).putExtra(AddressManagerActivity.SELECT_ADDRESS,true),10);
                break;
        }
    }

    /*
     * 存下物流公司的信息
     */
    private ExpressCompanyBean mExpressCompanyBean ;
    /**
     * 寄件人的id
     */
    private int seller_address_id;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //收货地址
        if (resultCode == AddressManagerActivity.RESULT_ADDRESS) {
            AddressBean.ListBean bean =
                    (AddressBean.ListBean) data.getSerializableExtra(Constant.INTENT_KEY_OBJECT);
            //显示收货地址
            String addressStr = bean.getArea() + bean.getAddress();
            //地址
            tvSellerAddress.setText(addressStr.replace(" ", ""));
            //地址id
            seller_address_id = bean.getAddress_id();
            //人名和电话
            tvSellerNameAndPhone.setText(bean.getConsignee() + " " + bean.getMobile());
        }
        //物流公司
        if (requestCode == Constant.SELECT_COMPANY_CODE && resultCode == RESULT_OK) {
            //写入物流公司
            mExpressCompanyBean =
                    (ExpressCompanyBean) data.getSerializableExtra(Constant.INTENT_KEY_OBJECT);
            tvExpressCompany.setText(mExpressCompanyBean.getName());
        }
    }

    @Override
    protected ExpressPresenter getPresenter() {
        return new ExpressPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_deliver_goods;
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
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }
}
