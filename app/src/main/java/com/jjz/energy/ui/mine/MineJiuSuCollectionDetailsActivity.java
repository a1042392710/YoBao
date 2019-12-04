package com.jjz.energy.ui.mine;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.jiusu_shop.JiuSuShoppingDetailsBean;
import com.jjz.energy.presenter.jiusu_shop.JiuSuShopPresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.SpUtil;
import com.jjz.energy.view.jiusu_shop.IJiuSuShopView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 店内收款记录详情
 * @author: create by chenhao on 2019/11/25
 */
public class MineJiuSuCollectionDetailsActivity extends BaseActivity<JiuSuShopPresenter> implements IJiuSuShopView {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_order_sn)
    TextView tvOrderSn;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_order_phone)
    TextView tvOrderPhone;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_old_money)
    TextView tvOrderOldMoney;
    @BindView(R.id.tv_order_new_money)
    TextView tvOrderNewMoney;
    /**
     * 订单编号
     */
    private String order_sn;
    /**
     * 将数据存下来
     */
    private JiuSuShoppingDetailsBean mDetailsBean;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("收款详情");
        order_sn = getIntent().getStringExtra(Constant.ORDER_SN);
        mPresenter.getJiuSuCollectionDetails(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.ORDER_SN,
                order_sn)));
    }

    @Override
    public void isGetJiusuShoppingDetailsSuc(JiuSuShoppingDetailsBean data) {
        mDetailsBean = data;
        //获取经纬度
        String lng = SpUtil.init(mContext).readString(Constant.LOCATION_LNG);
        String lat = SpUtil.init(mContext).readString(Constant.LOCATION_LAT);
        double mLng = StringUtil.isEmpty(lng) ? 0 : Double.valueOf(lng);
        double mLat = StringUtil.isEmpty(lat) ? 0 : Double.valueOf(lat);
      tvUserName.setText("用户昵称："+data.getNickname());
        tvOrderSn.setText("订单编号：" + data.getOrder_sn());
        tvOrderPhone.setText("手机号：" + data.getShop_phone());
        tvOrderTime.setText("支付时间：" + DateUtil.longToDate(data.getPay_time(), null));
        tvOrderOldMoney.setText("总价：" + data.getTotal_amount() + "元");
        tvOrderNewMoney.setText("实付：" + data.getOrder_amount() + "元");

    }


    @OnClick({R.id.ll_toolbar_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;

        }
    }

    @Override
    protected JiuSuShopPresenter getPresenter() {
        return new JiuSuShopPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_jiusu_collection_details;
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
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


}
