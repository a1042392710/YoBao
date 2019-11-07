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
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
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
     * 退款编号
     */
     private String return_id ;
    /**
     * 退款类型 0 仅退款 1 退款不退货 2 退货
     */
    private int type ;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("退款详情");
        return_id = getIntent().getStringExtra(Constant.RETURN_ID);
        getData();
    }


    /**
     * 获取退款退货详情数据
     */
    private  void getData(){
        mPresenter.getRefundDetails(PacketUtil.getRequestPacket(Utils.stringToMap("return_goods_id",return_id)));
    }

    //成功获取详情数据
    @Override
    public void isGetRefundDetailsSuccess(RefundDetailsBean data) {
        //退货
        if (data.getType()==1){
            type =2 ;
        }else {
            //仅退款
            if (data.getShipping_status()==0){
                type = 0;
            }else{
                //退款不退货
                type = 1;
            }
        }
        //根据订单状态来显示按钮和相应的控件
        setLableTextAndView(data.getReturn_status(), data);
    }
    /**
     * 根据状态显示文字
     */
    private void setLableTextAndView(int return_status, RefundDetailsBean data) {
        //先隐藏所有控件
        //简单提示
        tvRefundStateToast.setVisibility(View.GONE);
        //所有提示
        tvRefundAllToast.setVisibility(View.GONE);
        //金额
        llReturnSuc.setVisibility(View.GONE);
        //物流
        rlExpressInfo.setVisibility(View.GONE);
        //两个标签
        itemTvLableThree.setVisibility(View.GONE);
        itemTvLableTwo.setVisibility(View.GONE);
        //填写退款信息
        GlideUtils.loadRoundCircleImage(mContext,data.getGoods_images(),imgCommodity);
        //退款原因
        tvRefundReson.setText(data.getReason_txt());
        //商品名
        tvCommodityType.setText(data.getGoods_name());
        //退款金额
        tvRefundMoney.setText("￥"+data.getRefund_money()+"元");
        //申请时间
        tvRefundApplicationTime.setText(DateUtil.longToDate(data.getR_addtime(),null));
        //退款编号
        tvRefundNumber.setText(data.getRefund_sn());

        switch (return_status) {
            //买家取消退款申请
            case -2:
                tvRefundStateToast.setVisibility(View.VISIBLE);
                tvRejectReson.setText("退款关闭");
                //todo 退款时间
                tvTime.setText("退款时间");
                tvRefundStateToast.setText(R.string.refund_buyer_close_money_application_toast);
                break;
            //卖家拒绝退款
            case -1:
                tvRefundStateToast.setVisibility(View.VISIBLE);
                tvRefundAllToast.setVisibility(View.VISIBLE);
                itemTvLableThree.setVisibility(View.VISIBLE);
                itemTvLableTwo.setVisibility(View.VISIBLE);
                tvRejectReson.setText("卖家已拒绝");
                //todo 退款时间
                tvTime.setText("还剩几天");
                //todo 拒绝说明
                tvRefundStateToast.setText("拒绝说明");
                tvRefundAllToast.setText(R.string.refund_buyer_refuse_return_toast_all);
                itemTvLableThree.setText("修改申请");
                itemTvLableTwo.setText("撤销申请");
                break;
            //等待卖家审核
            case 0:
                tvRefundStateToast.setVisibility(View.VISIBLE);
                tvRefundAllToast.setVisibility(View.VISIBLE);
                itemTvLableThree.setVisibility(View.VISIBLE);
                itemTvLableTwo.setVisibility(View.VISIBLE);
                //todo 退款时间
                tvTime.setText("还剩几天");
                tvRejectReson.setText(R.string.refund_buyer_money_application);
                tvRefundStateToast.setText(R.string.refund_buyer_money_application_toast);
                itemTvLableThree.setText("修改申请");
                itemTvLableTwo.setText("撤销申请");
                if (type==0){
                    //仅退款
                    tvRefundAllToast.setText(R.string.refund_buyer_money_application_toast_all);
                }else if (type==1){
                    //退款不退货
                    tvRefundAllToast.setText(R.string.refund_buyer_money_application_toast_all);
                }else{
                    //退货
                    tvRefundAllToast.setText(R.string.refund_buyer_cargo_application_toast_all);
                }
                break;
            //卖家通过审核，等待买家退货
            case 1:
                tvRefundStateToast.setVisibility(View.VISIBLE);
                tvRefundAllToast.setVisibility(View.VISIBLE);
                itemTvLableThree.setVisibility(View.VISIBLE);
                tvTime.setText("还剩几天");
                tvRejectReson.setText(R.string.refund_buyer_cargo_return);
                tvRefundStateToast.setText(R.string.refund_buyer_cargo_return_toast);
                tvRefundAllToast.setText(R.string.refund_buyer_cargo_return_toast_all);
                itemTvLableThree.setText("填写退货物流");
                break;
            //买家已发货
            case 2:
                tvRefundStateToast.setVisibility(View.VISIBLE);
                tvRefundAllToast.setVisibility(View.VISIBLE);
                tvTime.setText("还剩几天");
                tvRejectReson.setText(R.string.refund_buyer_wait_seller_rceiving);
                tvRefundStateToast.setText(R.string.refund_buyer_wait_seller_rceiving_toast);
                tvRefundAllToast.setText(R.string.refund_buyer_wait_seller_rceiving_toast_all);
                rlExpressInfo.setVisibility(View.VISIBLE);
                //todo 写入退货物流信息


                break;
            //退款完成
            case 5:
                llReturnSuc.setVisibility(View.VISIBLE);
                //写入退款金额
                tvReturnSuc.setText("￥"+data.getRefund_money()+"元");
                tvRejectReson.setText("退款成功");
                tvTime.setText(DateUtil.longToDate(data.getRefund_time(),null));
                break;

        }
        stopLoading();
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
                PopWindowUtil.getInstance().showPopupWindow(mContext, "您将撤销本次退款申请，您下次还可以再提交申请", () -> {

                });
                break;
            case "填写退货物流":
                startActivity(new Intent(mContext,ReturnLogisticsActivity.class));
                break;
            case "修改申请":
                //TODO 获取详情数据时能拿到该笔订单的状态
//                startActivity(new Intent(mContext,ApplicationRefundActivity.class).putExtra(Constant.REC_ID,order_sn));
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
                startActivity(new Intent(mContext,RefundHistoryActivity.class).putExtra(Constant.RETURN_ID,return_id));
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
