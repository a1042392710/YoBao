package com.jjz.energy.ui.mine.shop_order.refund_order;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.order.RefundDetailsBean;
import com.jjz.energy.presenter.order.RefundPresenter;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.order.IRefundView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 买家查看退款详情
 * @author: create by chenhao on 2019/11/4
 */
public class BuyerRefundDetailsActivity extends BaseActivity<RefundPresenter>implements IRefundView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_reject_reson)
    TextView tvRejectReson;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_refund_state_toast)
    TextView tvRefundStateToast;
    @BindView(R.id.img_test_one)
    ImageView imgTestOne;
    @BindView(R.id.tv_refund_express_info_title)
    TextView tvRefundExpressInfoTitle;
    @BindView(R.id.tv_refund_express_info_content)
    TextView tvRefundExpressInfoContent;
    @BindView(R.id.tv_refund_express_info_time)
    TextView tvRefundExpressInfoTime;
    @BindView(R.id.rl_express_info)
    RelativeLayout rlExpressInfo;
    @BindView(R.id.tv_refund_all_toast)
    TextView tvRefundAllToast;
    @BindView(R.id.item_tv_lable_one)
    TextView itemTvLableOne;
    @BindView(R.id.item_tv_lable_two)
    TextView itemTvLableTwo;
    @BindView(R.id.item_tv_lable_three)
    TextView itemTvLableThree;
    @BindView(R.id.img_commodity)
    ImageView imgCommodity;
    @BindView(R.id.tv_commodity_type)
    TextView tvCommodityType;
    @BindView(R.id.item_rl_commodity)
    RelativeLayout itemRlCommodity;
    @BindView(R.id.tv_refund_reson)
    TextView tvRefundReson;
    @BindView(R.id.tv_refund_money)
    TextView tvRefundMoney;
    @BindView(R.id.tv_refund_application_time)
    TextView tvRefundApplicationTime;
    @BindView(R.id.tv_refund_number)
    TextView tvRefundNumber;
    @BindView(R.id.ll_return_suc)
    LinearLayout llReturnSuc;
    @BindView(R.id.tv_return_suc)
    TextView tvReturnSuc;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    /**
     * 订单编号
     */
     private String order_sn ;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("退款详情");
        order_sn = getIntent().getStringExtra(Constant.ORDER_SN);
        //获取退款退货详情页面
        mPresenter.getRefundDetails(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.ORDER_SN,order_sn)));
    }

    //成功获取详情数据
    @Override
    public void isGetRefundDetailsSuccess(RefundDetailsBean data) {

    }

    /**
     * 各标签的点击事件
     * @param str
     */
    private void onLableClick(String str){
        switch (str){
            case "客服介入":
                break;

            case "撤销申请":
                break;

            case "填写退货物流":
                startActivity(new Intent(mContext,ReturnLogisticsActivity.class));
                break;

            case "修改申请":
                startActivity(new Intent(mContext,ApplicationRefundActivity.class).putExtra(Constant.ORDER_SN,order_sn));
                break;
        }
    }


    @OnClick({R.id.ll_toolbar_left, R.id.item_tv_lable_one, R.id.item_tv_lable_two,R.id.tv_history,
            R.id.item_tv_lable_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.item_tv_lable_one:
                onLableClick(itemTvLableOne.getText().toString());
                break;
            case R.id.item_tv_lable_two:
                onLableClick(itemTvLableTwo.getText().toString());
                break;
            case R.id.item_tv_lable_three:
                onLableClick(itemTvLableThree.getText().toString());
                break;
                //协商历史
            case R.id.tv_history:
                break;
        }
    }

    @Override
    protected RefundPresenter getPresenter() {
        return new RefundPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_buyer_refund_details;
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
    public void isFail(String msg) {
        showToast(msg);
    }
}
