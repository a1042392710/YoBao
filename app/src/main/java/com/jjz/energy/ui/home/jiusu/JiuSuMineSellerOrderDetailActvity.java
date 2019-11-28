package com.jjz.energy.ui.home.jiusu;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.jiusu.OrderDetailBean;
import com.jjz.energy.presenter.jiusu.MineOrderDetailPresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IMineOrderDetailView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 卖家订单详情
 * @author: create by chenhao on 2019/4/16
 */
public class JiuSuMineSellerOrderDetailActvity extends BaseActivity<MineOrderDetailPresenter> implements IMineOrderDetailView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_status)
    TextView tvStatus;
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

    @Override
    protected MineOrderDetailPresenter getPresenter() {
        return new MineOrderDetailPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_seller_order_detail;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("交易详情");
        mPresenter.getSellerOrderDetail(PacketUtil.getRequestPacket(Utils.stringToMap("order_sn",getIntent().getStringExtra("order_sn"))));
    }
    /**
     * 根据状态值改变字体颜色
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
        //状态
        tvStatus.setText(data.getOrder_state());
        tvStatus.setTextColor(getTextColor(data.getOrder_status()));
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


    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }

}
