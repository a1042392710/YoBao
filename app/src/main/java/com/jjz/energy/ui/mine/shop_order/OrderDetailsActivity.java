package com.jjz.energy.ui.mine.shop_order;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.OrderDetailsStatusAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 订单详情
 * @author: create by chenhao on 2019/10/10
 */
public class OrderDetailsActivity extends BaseActivity {


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

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    /**
     * 订单sn
     */
    private String order_sn;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("订单详情");
        order_sn  = getIntent().getStringExtra(Constant.ORDER_SN);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        rvOrderStatus.setLayoutManager(new GridLayoutManager(this, 5));
        OrderDetailsStatusAdapter mAdapter =
                new OrderDetailsStatusAdapter(R.layout.item_order_status, list);
        rvOrderStatus.setAdapter(mAdapter);
    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_talk_seller, R.id.tv_logistics_details,
            R.id.tv_order_lable_one, R.id.tv_order_lable_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.tv_talk_seller:
                //和买家或者卖家聊天
                break;
            case R.id.tv_logistics_details:
                //查看物流详情
                break;
            case R.id.tv_order_lable_one:
                //按钮一
                lableCilck(tvOrderLableOne.getText().toString());
                break;
            case R.id.tv_order_lable_two:
                //按钮二
                lableCilck(tvOrderLableTwo.getText().toString());
                break;
        }
    }

    /**
     * 底部两个按钮的点击事件
     */
    private void lableCilck(String str){

        switch (str){

            case "查看评价":
                break;
            case "评价":
                break;
            case "提醒发货":
                break;
            case "确认收货":
                break;
            case "取消订单":
                break;
            case "申请退款":
                break;

        }
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
