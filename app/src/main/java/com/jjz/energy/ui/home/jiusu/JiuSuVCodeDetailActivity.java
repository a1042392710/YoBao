package com.jjz.energy.ui.home.jiusu;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.jiusu.OrderDetailBean;
import com.jjz.energy.presenter.jiusu.JiuSuMinePresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.mine.IPersonalInformationView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 二维码详情
 * @author: create by chenhao on 2019/4/19
 */
public class JiuSuVCodeDetailActivity extends BaseActivity<JiuSuMinePresenter> implements IPersonalInformationView {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_buyer_nickname)
    TextView tvBuyerNickname;
    @BindView(R.id.tv_buyer_phone)
    TextView tvBuyerPhone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_oil_price)
    TextView tvOilPrice;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_finish)
    TextView tvFinish;

    private String qr_code;

    @Override
    protected JiuSuMinePresenter getPresenter() {
        return new JiuSuMinePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_vcode_detail;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("扫码详情");
        qr_code = getIntent().getStringExtra("qr_code");
        mPresenter.scanQrOrder(PacketUtil.getRequestPacket(Utils.stringToMap("qr_code",qr_code)));
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
                finish();
                break;
            //核销订单
            case R.id.tv_finish:
                mPresenter.finishOrder(PacketUtil.getRequestPacket(Utils.stringToMap("qr_code",
                        qr_code)));
                break;
        }
    }
    //查询订单信息
    @Override
    public void isOrderInfoSuccess(OrderDetailBean data) {
        //买家昵称
        tvBuyerNickname.setText(data.getNickname());
        //买家电话
        tvBuyerPhone.setText(data.getMobile());
        //订单编号
        tvOrderId.setText(data.getOrder_sn());
        //下单时间
        tvTime.setText(DateUtil.longToDate(data.getAdd_time(),null));
        //油价
        tvOilPrice.setText(data.getGoods_price()+"元(升)");
        //数量
        tvSum.setText(data.getGoods_num()+"");
        //总价
        tvTotal.setText(data.getOrder_amount()+"元");
    }

    @Override
    public void isFinishSuccess(String data) {
        //结单
        startActivity(new Intent(mContext, JiuSuTranscationSucActivity.class));
        finish();
    }

    @Override
    public void isFail(String msg ,boolean e) {
        tvFinish.setVisibility(View.GONE);
        showToast(msg);
    }
}
