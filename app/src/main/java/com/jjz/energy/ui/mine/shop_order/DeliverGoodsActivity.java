package com.jjz.energy.ui.mine.shop_order;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.order.ExpressCompanyBean;
import com.jjz.energy.presenter.order.ExpressPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
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
     * 发货
     */
    private void putExpressInfo(){
        if (StringUtil.isEmpty(etExpressNumber.getText().toString())){
            showToast("请填写物流单号");
            return;
        }
        if (mExpressCompanyBean==null){
            showToast("请选择物流公司");
            return;
        }
        HashMap<String,String>map = new HashMap<>();
        map.put(Constant.ORDER_SN,order_sn);
        map.put("物流单号",etExpressNumber.getText().toString());
        map.put("物流公司",mExpressCompanyBean.getId());
        mPresenter.putExpressInfo(PacketUtil.getRequestPacket(map));
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_express_company, R.id.tv_submit, R.id.tv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //选择物流公司
            case R.id.tv_express_company:
                startActivityForResult(new Intent(mContext, ExpressCompanyActivity.class), 10);
                break;
            //提交数据
            case R.id.tv_submit:
                putExpressInfo();
                break;
                //无需寄件
            case R.id.tv_toolbar_right:
                break;
        }
    }


    /*
     * 存下物流公司的信息
     */
    private ExpressCompanyBean mExpressCompanyBean ;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10 && resultCode==RESULT_OK){
            //写入物流公司
            mExpressCompanyBean = (ExpressCompanyBean) data.getSerializableExtra(Constant.INTENT_KEY_OBJECT);
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
