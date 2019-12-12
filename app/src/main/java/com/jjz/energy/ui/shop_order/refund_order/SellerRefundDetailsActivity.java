package com.jjz.energy.ui.shop_order.refund_order;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.enums.RefundOrderStatusEnum;
import com.jjz.energy.entry.order.RefundDetailsBean;
import com.jjz.energy.presenter.order.RefundPresenter;
import com.jjz.energy.ui.shop_order.ExpressDetailsActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.order.IRefundView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Features: 卖家查看退款详情
 * @author: create by chenhao on 2019/11/4
 */
@RuntimePermissions
public class SellerRefundDetailsActivity extends BaseActivity<RefundPresenter>implements IRefundView {

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
     private String return_id ;
    /**
     * 退款类型 0 仅退款 1 退款不退货 2 退货
     */
    private int type ;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("退款详情");
        return_id = getIntent().getStringExtra(Constant.RETURN_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        setLableTextAndView(data);
    }

    /**
     * 保存数据源
     */
    private RefundDetailsBean mDetailsBean;

    /**
     * 根据状态显示文字
     */
    private void setLableTextAndView(RefundDetailsBean data) {
        mDetailsBean = data;
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

        switch (data.getReturn_status()) {
            //卖家拒绝退款
            case -1:
                tvRefundStateToast.setVisibility(View.VISIBLE);
                itemTvLableThree.setVisibility(View.VISIBLE);
                tvRejectReson.setText(R.string.refund_seller_refuse_return);
                //退款时间
                tvTime.setText("还剩"+DateUtil.dateDiff(System.currentTimeMillis(),mDetailsBean.getEnd_time()*1000L));
                tvRefundStateToast.setText(R.string.refund_seller__refuse_return_toast);
                itemTvLableThree.setText("同意退款");
                break;
                //等待卖家审核
            case 0:
                tvRefundStateToast.setVisibility(View.VISIBLE);
                tvRefundAllToast.setVisibility(View.VISIBLE);
                itemTvLableThree.setVisibility(View.VISIBLE);
                itemTvLableTwo.setVisibility(View.VISIBLE);
                //退款时间
                tvTime.setText("还剩"+DateUtil.dateDiff(System.currentTimeMillis(),mDetailsBean.getEnd_time()*1000L));
                if (type == 0) {
                    //仅退款
                    tvRejectReson.setText(R.string.refund_seller_audit);
                    tvRefundStateToast.setText(R.string.refund_seller_audit_toast);
                    tvRefundAllToast.setText(R.string.refund_seller_audit_toast_all);
                    itemTvLableTwo.setText("拒绝申请");
                    itemTvLableThree.setText("同意退款");
                } else if (type == 1) {
                    //退款不退货
                    tvRejectReson.setText(R.string.refund_seller_audit);
                    tvRefundStateToast.setText(R.string.refund_seller_audit_toast);
                    tvRefundAllToast.setText(R.string.refund_seller_audit_toast_all_two);
                    itemTvLableTwo.setText("拒绝申请");
                    itemTvLableThree.setText("同意退款");
                } else {
                    //退货
                    tvRejectReson.setText(R.string.refund_seller_cargo_application);
                    tvRefundStateToast.setText(R.string.refund_seller_cargo_application_toast);
                    tvRefundAllToast.setText(R.string.refund_seller_cargo_application_toast_all);
                    itemTvLableTwo.setText("拒绝退货申请");
                    itemTvLableThree.setText("同意退货");
                }
                break;
            //卖家通过审核，等待买家退货
            case 1:
                tvRefundStateToast.setVisibility(View.VISIBLE);
                itemTvLableThree.setVisibility(View.VISIBLE);
                tvRejectReson.setText(R.string.refund_seller_wait_receipt);
                tvRefundStateToast.setText(R.string.refund_seller_wait_receipt_toast);
                itemTvLableThree.setText("收到货，同意退款");
                //退款时间
                tvTime.setText("还剩"+DateUtil.dateDiff(System.currentTimeMillis(),mDetailsBean.getEnd_time()*1000L));
                break;
                //买家已发货
            case 2:
                tvRefundStateToast.setVisibility(View.VISIBLE);
                itemTvLableThree.setVisibility(View.VISIBLE);
                itemTvLableTwo.setVisibility(View.VISIBLE);
                rlExpressInfo.setVisibility(View.VISIBLE);
                tvRejectReson.setText(R.string.refund_seller_confirm_receipt);
                tvRefundStateToast.setText(R.string.refund_seller_confirm_receipt_toast);
                itemTvLableThree.setText("同意退款");
                itemTvLableTwo.setText("拒绝退款");
                //退款时间
                tvTime.setText("还剩"+DateUtil.dateDiff(System.currentTimeMillis(),mDetailsBean.getEnd_time()*1000L));
                //写入退货物流信息
                tvRefundExpressInfoTitle.setText("退货物流："+ mDetailsBean.getTrick().getShipping_name()+"("+mDetailsBean.getTrick().getCourier_number()+")" );
                //详情
                tvRefundExpressInfoContent.setText(mDetailsBean.getTrick().getAcceptStation());
                //时间
                tvRefundExpressInfoTime.setText(mDetailsBean.getTrick().getAcceptTime());
                break;
                //退款完成
            case 5:
                llReturnSuc.setVisibility(View.VISIBLE);
                //写入退款金额
                tvReturnSuc.setText("退款金额：￥"+data.getRefund_money()+"元");
                tvRejectReson.setText("退款成功");
                tvTime.setText(DateUtil.longToDate(data.getRefund_time(),null));
                break;
                //买家取消退款申请
            case -2:
                tvRefundStateToast.setVisibility(View.VISIBLE);
                tvRejectReson.setText("退款关闭");
                tvTime.setText(DateUtil.longToDate(data.getCanceltime(),null));
                tvRefundStateToast.setText("买家已撤销本次退款申请");
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
            case "同意退款":
            case "收到货，同意退款":
                PopWindowUtil.getInstance().showPopupWindow(mContext, "确认后，您将退款给买家", () -> {
                    HashMap<String,String>map = new HashMap<>();
                    map.put("id",return_id);
                    map.put("status", RefundOrderStatusEnum.RETURN_SUC.getIndex()+"");
                   mPresenter.sellerAgreeReturnMoney(PacketUtil.getRequestPacket(map));
                });
                break;
            case "拒绝申请":
            case "拒绝退款":
            case "拒绝退货申请":
                startActivity(new Intent(mContext,SellerRefuseApplicationActivity.class).putExtra(Constant.RETURN_ID,return_id));
                break;
            case "同意退货":
                startActivity(new Intent(mContext,SellerAgreeReturnActivity.class).putExtra(Constant.RETURN_ID,return_id).putExtra(Constant.ORDER_SN,mDetailsBean.getOrder_sn()));
                break;
            case "客服介入":
                PopWindowUtil.getInstance().showPopupWindow(mContext, "您将拨打客服电话:"+"400-1070-400", () -> {
                    callPhone();
                });
                break;

        }
    }

    //卖家同意退款
    @Override
    public void isSellerAgreeReturnMoneySuccess(String data) {
        showToast("操作成功");
        getData();
    }

    @OnClick({ R.id.ll_toolbar_left, R.id.item_tv_lable_one, R.id.item_tv_lable_two,R.id.tv_history,
            R.id.item_tv_lable_three ,R.id.rl_express_info})
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
            //物流详情
            case R.id.rl_express_info:
                startActivity(new Intent(mContext, ExpressDetailsActivity.class)
                        .putExtra("shipping_no", mDetailsBean.getTrick().getCourier_number())
                        .putExtra(Constant.RETURN_ID,return_id));
                break;
        }
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callPhone() {
        if (mDetailsBean!=null) {
            // 拨号：激活系统的拨号组件
            Intent intent = new Intent(); // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            Uri data = Uri.parse("tel:" + "4001070400"); // 设置数据
            intent.setData(data);
            startActivity(intent); // 激活Activity组件
        }
    }

    //给用户解释为什么要申请权限
    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showCallPhone(final PermissionRequest request) {
        //唤起打电话权限
        PopWindowUtil.getInstance().showPopupWindow(mContext, "没有电话权限可不能打电话哦", () -> {
            request.proceed();//继续执行请求
        });
    }


    @Override
    protected RefundPresenter getPresenter() {
        return new RefundPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_seller_refund_details;
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
