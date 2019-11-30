package com.jjz.energy.ui.shop_order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.OrderDetailsStatusAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.order.ShopOrderDetailsBean;
import com.jjz.energy.presenter.order.ShopOrderDetailsPresenter;
import com.jjz.energy.ui.home.commodity.CommodityDetailActivity;
import com.jjz.energy.ui.shop_order.refund_order.ApplicationRefundActivity;
import com.jjz.energy.ui.shop_order.refund_order.BuyerRefundDetailsActivity;
import com.jjz.energy.ui.shop_order.refund_order.RefundTypeSelectActivity;
import com.jjz.energy.ui.shop_order.refund_order.SellerRefundDetailsActivity;
import com.jjz.energy.ui.notice.IMActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.order.IOrderDetalsView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 订单详情
 * @author: create by chenhao on 2019/10/10
 */
public class OrderDetailsActivity extends BaseActivity<ShopOrderDetailsPresenter>implements IOrderDetalsView {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_order_status)
    RecyclerView rvOrderStatus;
    @BindView(R.id.tv_order_sn)
    TextView tvOrderSn;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.img_commodity)
    ImageView imgCommodity;
    @BindView(R.id.tv_talk_seller)
    TextView tvTalkSeller;
    @BindView(R.id.tv_commodity_title)
    TextView tvCommodityTitle;
    @BindView(R.id.tv_commodity_price_and_num)
    TextView tvCommodityPriceAndNum;
    @BindView(R.id.tv_buyer_name)
    TextView tvBuyerName;
    @BindView(R.id.tv_logistics_details)
    TextView tvLogisticsDetails;
    @BindView(R.id.tv_buyer_phone)
    TextView tvBuyerPhone;
    @BindView(R.id.tv_buyer_address)
    TextView tvBuyerAddress;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_order_info_price)
    TextView tvOrderInfoPrice;
    @BindView(R.id.tv_order_info_freight)
    TextView tvOrderInfoFreight;
    @BindView(R.id.tv_order_info_num)
    TextView tvOrderInfoNum;
    @BindView(R.id.tv_order_lable_one)
    TextView tvOrderLableOne;
    @BindView(R.id.tv_order_lable_two)
    TextView tvOrderLableTwo;
    @BindView(R.id.tv_price_title)
    TextView tvPriceTitle;
    @BindView(R.id.tv_system_toast)
    TextView tvSystemToast;
    @BindView(R.id.tv_order_info_integral)
    TextView tvOrderInfoIntegral;
    @BindView(R.id.ll_bottom_btn)
    LinearLayout llBottomBtn;

    /**
     * 订单sn
     */
    private String order_sn;
    /**
     * 用户类型  0是买家 1是卖家    进页面必须要传
     */
    private int user_type;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("订单详情");
        order_sn = getIntent().getStringExtra(Constant.ORDER_SN);
        user_type = getIntent().getIntExtra(Constant.USER_TYPE, 0);
        if (user_type==1){
            tvTalkSeller.setText("联系买家");
        }
        rvOrderStatus.setLayoutManager(new GridLayoutManager(this, 5));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    //获取数据
    private void getData(){
        //查询订单详情
        HashMap<String,String> map = new HashMap<>();
        map.put(Constant.ORDER_SN,order_sn);
        map.put("identity",user_type==0?"buyer":"saler");
        mPresenter.getOrderDetails(PacketUtil.getRequestPacket(map));
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_talk_seller, R.id.tv_logistics_details,
            R.id.tv_order_lable_one, R.id.tv_order_lable_two ,R.id.rl_goods})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.tv_talk_seller:
                if (mData==null){
                    return;
                }
                //和买家或者卖家聊天
                startActivity(new Intent(mContext, IMActivity.class).putExtra("userName",user_type==0?mData.getSeller_mobile():mData.getBuyer_mobile()));
                break;
            case R.id.tv_logistics_details:
                //查看物流详情
                if (mData!=null) {
                    startActivity(new Intent(mContext, ExpressDetailsActivity.class)
                            .putExtra("shipping_no", mData.getShipping_no()));
                }
                break;
            case R.id.tv_order_lable_one:
                //按钮一
                lableCilck(tvOrderLableOne.getText().toString());
                break;
            case R.id.tv_order_lable_two:
                //按钮二
                lableCilck(tvOrderLableTwo.getText().toString());
                break;
            case R.id.rl_goods:
                //商品详情
                if (mData!=null) {
                    startActivity(new Intent(mContext, CommodityDetailActivity.class).putExtra(Constant.GOODS_ID,mData.getGoods_id()));
                }
                break;
        }
    }

    /**
     * 底部两个按钮的点击事件
     */
    private void lableCilck(String str){
        switch (str){
            case "查看评价":
                startActivity(new Intent(mContext, EvaluateDetailsActivity.class).putExtra(Constant.ORDER_SN,   order_sn));
                break;
            case "评价一下":
                startActivityForResult(new Intent(mContext, EvaluateActivity.class).putExtra(Constant.ORDER_SN, order_sn),10);
                break;
            case "提醒发货":
                PopWindowUtil.getInstance().showPopupWindow(mContext, "您将提醒卖家发货，一天只能提醒一次", () -> {
                    mPresenter.remindShipment(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.ORDER_SN,order_sn)));
                });
                break;
            case "去发货":
                startActivityForResult(new Intent(mContext,DeliverGoodsActivity.class).putExtra(Constant.ORDER_SN,order_sn),10);
                break;
            case "提醒收货":
                PopWindowUtil.getInstance().showPopupWindow(mContext, "您将提醒买家收货，一天只能提醒一次", () -> {
                    mPresenter.remindReceipt(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.ORDER_SN,order_sn)));
                });
                break;
            case "确认收货":
                PopWindowUtil.getInstance().showPopupWindow(mContext, "您是否确认收货？", () -> {
                    mPresenter.confirmReceipt(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.ORDER_SN,order_sn)));
                });
                break;
            case "取消订单":
                PopWindowUtil.getInstance().showPopupWindow(mContext, "您是否确定取消该笔订单？", () -> {
                    mPresenter.cancelOrder(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.ORDER_SN,order_sn)));
                });
                break;
            case "申请退款":
                startActivityForResult(new Intent(mContext, ApplicationRefundActivity.class).putExtra(Constant.REC_ID,mData.getRec_id()).putExtra("type",Constant.RETURN_MONEY),10);
                break;
            case "退款详情":
                //卖家详情
                if (user_type == 1){
                    startActivity(new Intent(mContext, SellerRefundDetailsActivity.class).putExtra(Constant.RETURN_ID,mData.getReturn_id()));
                }else{
                    //买家详情
                    startActivity(new Intent(mContext, BuyerRefundDetailsActivity.class).putExtra(Constant.RETURN_ID,mData.getReturn_id()));
                }
                break;
            //进入选择服务方式页面
            case "我要退款":
                startActivityForResult(new Intent(mContext, RefundTypeSelectActivity.class).putExtra(Constant.INTENT_KEY_OBJECT,mData),10);
                break;
        }
    }


    /**
     * 设置底部文字
     */
    private void setBottomText(int status) {
        llBottomBtn.setVisibility(View.GONE);
        tvOrderLableOne.setVisibility(View.GONE);
        tvOrderLableTwo.setVisibility(View.GONE);
        switch (status) {
            //待发货
            case 1:
                llBottomBtn.setVisibility(View.VISIBLE);
                //买家
                if (user_type==0) {
                    tvOrderLableOne.setVisibility(View.VISIBLE);
                    tvOrderLableTwo.setVisibility(View.VISIBLE);
                    tvOrderLableOne.setText("申请退款");
                    tvOrderLableTwo.setText("提醒发货");
                }else{
                    tvOrderLableOne.setVisibility(View.VISIBLE);
                    tvOrderLableTwo.setVisibility(View.VISIBLE);
                    tvOrderLableOne.setText("取消订单");
                    tvOrderLableTwo.setText("去发货");
                }
                break;

            //待收货
            case 2:
                llBottomBtn.setVisibility(View.VISIBLE);
                //距离确认收货的时间
                String time = DateUtil.dateDiff(System.currentTimeMillis(),
                        mData.getEnd_time() * 1000L);
                //买家
                if (user_type == 0) {
                    //设置文字提示
                    tvSystemToast.setVisibility(View.VISIBLE);
                    SpannableString spannableString = new SpannableString(time+"后，您仍未确认收货，系统会自动确认，钱款将会打到您的账户中");
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#fe8977"));
                    spannableString.setSpan(colorSpan, 0, time.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    tvSystemToast.setText(spannableString);

                    tvOrderLableOne.setVisibility(View.VISIBLE);
                    tvOrderLableTwo.setVisibility(View.VISIBLE);
                    tvOrderLableOne.setText("我要退款");
                    tvOrderLableTwo.setText("确认收货");
                }else{
                    tvSystemToast.setVisibility(View.VISIBLE);
                    //设置文字提示
                    SpannableString spannableString = new SpannableString(time+"后，买家仍未确认收货，系统会自动确认，钱款将会打到您的账户中");
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#fe8977"));
                    spannableString.setSpan(colorSpan, 0, time.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    tvSystemToast.setText(spannableString);
                    tvOrderLableTwo.setVisibility(View.VISIBLE);
                    tvOrderLableTwo.setText("提醒收货");
                }
                break;
            //待评价
            case 3:
                llBottomBtn.setVisibility(View.VISIBLE);
                tvOrderLableTwo.setVisibility(View.VISIBLE);
                tvOrderLableTwo.setText("评价一下");
                break;
            //交易关闭
            case 4:
                break;
            //交易完成
            case 5:
                llBottomBtn.setVisibility(View.VISIBLE);
                tvOrderLableTwo.setVisibility(View.VISIBLE);
                tvOrderLableTwo.setText("查看评价");
                break;

        }
    }

    /**
     * 订单详情数据
     */
    private ShopOrderDetailsBean mData;

    //获取订单详情成功
    @SuppressLint("SetTextI18n")
    @Override
    public void isGetOrderDetailsSuc(ShopOrderDetailsBean data) {
        mData = data;
        //没有物流信息，就表示为见面交易
        if (StringUtil.isEmpty(data.getShipping_no())){
            tvLogisticsDetails.setVisibility(View.INVISIBLE);
        }else{
            tvLogisticsDetails.setText("物流详情");
            tvLogisticsDetails.setVisibility(View.VISIBLE);
        }

        if (!StringUtil.isEmpty(data.getReturn_id())&&-2!=data.getReturn_status()){
            llBottomBtn.setVisibility(View.VISIBLE);
            tvOrderLableTwo.setVisibility(View.VISIBLE);
            tvOrderLableOne.setVisibility(View.GONE);
            tvOrderLableTwo.setText("退款详情");
        }else{
            //设置底部按钮文字
            setBottomText(data.getStatus());
        }
        //交易状态
        tvOrderState.setText(data.getState());
        //订单状态
        rvOrderStatus.setAdapter(new OrderDetailsStatusAdapter(R.layout.item_order_status, data.getStatusList(),data.getStatus()));
        //订单编号
        tvOrderSn.setText("订单编号："+data.getOrder_sn());
        //交易时间
        tvOrderTime.setText("交易时间："+ DateUtil.longToDate(data.getPay_time(),null));

        //商品图片
        GlideUtils.loadRoundCircleImage(mContext,data.getGoods_images(),imgCommodity);
        //商品名称
        tvCommodityTitle.setText(data.getGoods_name());
        //商品价格和数量
        tvCommodityPriceAndNum.setText("￥"+data.getGoods_price()+" x"+data.getGoods_num());
        //收货人
        tvBuyerName.setText(data.getConsignee());
        //联系方式
        tvBuyerPhone.setText(data.getMobile());
        //联系地址
        tvBuyerAddress.setText(data.getFull_address());
        //付款方式
        tvPayType.setText(data.getPay_name());
        //商品总额
        tvOrderInfoPrice.setText("￥"+data.getGoods_price());
        //运费
        tvOrderInfoFreight.setText("￥"+data.getShipping_price());
        //数量
        tvOrderInfoNum.setText("x"+data.getGoods_num());
        //实付金额
        tvPriceTitle.setText("实付金额：￥"+data.getOrder_amount());
        //todo 积分优惠金额
        tvOrderInfoIntegral.setText("￥"+data.getOrder_amount());

    }

    //确认收货
    @Override
    public void isConfirmReceiptSuc(String data) {
        showToast("收货成功");
        getData();
    }

    //取消订单
    @Override
    public void isCancelOrderSuc(String data) {
        showToast("取消订单成功");
        finish();
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }

    @Override
    protected ShopOrderDetailsPresenter getPresenter() {
        return new ShopOrderDetailsPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_order_details;
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
