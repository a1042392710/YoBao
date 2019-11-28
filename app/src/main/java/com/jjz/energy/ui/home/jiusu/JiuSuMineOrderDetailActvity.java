package com.jjz.energy.ui.home.jiusu;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.jiusu.OrderDetailBean;
import com.jjz.energy.presenter.jiusu.MineOrderDetailPresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IMineOrderDetailView;
import com.jjz.energy.zxing.encode.CodeCreator;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 订单详情
 * @author: create by chenhao on 2019/4/16
 */
public class JiuSuMineOrderDetailActvity extends BaseActivity<MineOrderDetailPresenter> implements IMineOrderDetailView {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_service_point_name)
    TextView tvServicePointName;
    @BindView(R.id.tv_service_point_address)
    TextView tvServicePointAddress;
    @BindView(R.id.tv_service_point_phone)
    TextView tvServicePointPhone;
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_oil_price)
    TextView tvOilPrice;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.img_vcode)
    ImageView imgVcode;
    @BindView(R.id.ll_vcode)
    LinearLayout llVcode;
    @BindView(R.id.tv_cancle_order)
    TextView tvCancleOrder;
    @BindView(R.id.ll_integral)
    LinearLayout llIntegral;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;

    private String order_sn;
    @Override
    protected MineOrderDetailPresenter getPresenter() {
        return new MineOrderDetailPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_order_detail;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("交易详情");
        order_sn = getIntent().getStringExtra("order_sn");
        mPresenter.getOrderDetail(PacketUtil.getRequestPacket(Utils.stringToMap("order_sn",
                order_sn  )));
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    /**
     * 根据状态值改变字体颜色
     *
     * @param status 0未确认 1已确认 3 交易关闭 4 交易成功
     * @return color
     */
    private int getTextColor(int status) {
        switch (status) {
            case 3:
                return mContext.getResources().getColor(R.color.text_red_f04f4f);
            case 4:
                return mContext.getResources().getColor(R.color.text_green_26a69a);
            default:
                return mContext.getResources().getColor(R.color.text_orange_f7bc2e);
        }
    }


    @Override
    public void isSuccess(OrderDetailBean data) {
        //待卖家确认
        if (0==data.getOrder_status()){
            tvCancleOrder.setVisibility(View.VISIBLE);
        }
        //状态
        tvStatus.setText(data.getOrder_state());
        tvStatus.setTextColor(getTextColor(data.getOrder_status()));
        //商铺名称
        tvServicePointName.setText(data.getShop_name());
        //商铺地址
        tvServicePointAddress.setText(data.getShop_address());
        //商铺电话
        tvServicePointPhone.setText(data.getShop_phone());
        //订单编号
        tvOrderId.setText(data.getOrder_sn());
        //下单时间
        tvTime.setText(DateUtil.longToDate(data.getAdd_time(),null));
        //油价
        tvOilPrice.setText(data.getGoods_price() + "元(升)");
        //数量
        tvSum.setText(data.getGoods_num() + "");
        //显示折扣金额
        if (!StringUtil.isEmpty(data.getIntegral_money())){
            llIntegral.setVisibility(View.VISIBLE);
            tvIntegral.setText("¥"+data.getIntegral_money());
        }
        //总价
        tvTotal.setText(data.getOrder_amount() + "元");
        Map itemMap = (Map) JSON.parse(data.getQr_code());
        //生成二维码
        if (!StringUtil.isEmpty((String) itemMap.get("qr_code"))) {
            llVcode.setVisibility(View.VISIBLE);
            //生成二维码 带logo
            Bitmap bitmap = CodeCreator.createQRCode(data.getQr_code(), 400, 400, null);
            if (bitmap != null) {
                imgVcode.setImageBitmap(bitmap);
            }
        }
    }

    //取消订单
    @Override
    public void isCancleOrderSuccess(String data) {
        showToast("取消成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }



    @OnClick({R.id.ll_toolbar_left, R.id.tv_cancle_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.tv_cancle_order:
                mPresenter.cancleOrder(PacketUtil.getRequestPacket(Utils.stringToMap("order_sn",order_sn)));
                break;
        }
    }
}
