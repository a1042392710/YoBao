package com.jjz.energy.ui.home.logistics;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 物流详情
 * @author: create by chenhao on 2019/6/28
 */
public class LogisticsDetailActivity extends BaseActivity {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_time_info)
    TextView tvTimeInfo;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.tv_first_content)
    TextView tvFirstContent;
    @BindView(R.id.ll_first)
    LinearLayout llFirst;
    @BindView(R.id.item_ll_mark)
    LinearLayout itemLlMark;
    @BindView(R.id.item_tv_end)
    TextView itemTvEnd;
    @BindView(R.id.item_tv_end_content)
    TextView itemTvEndContent;
    @BindView(R.id.ll_end)
    LinearLayout llEnd;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_map)
    TextView tvMap;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.cv_weight)
    CardView cvWeight;
    @BindView(R.id.tv_volume)
    TextView tvVolume;
    @BindView(R.id.cv_volume)
    CardView cvVolume;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.tv_item_money)
    TextView tvItemMoney;
    @BindView(R.id.tv_item_weight)
    TextView tvItemWeight;
    @BindView(R.id.tv_item_volume)
    TextView tvItemVolume;
    @BindView(R.id.tv_item_desc)
    TextView tvItemDesc;
    @BindView(R.id.tv_sender)
    TextView tvSender;
    @BindView(R.id.tv_sender_phone)
    TextView tvSenderPhone;
    @BindView(R.id.img_sender_phone)
    ImageView imgSenderPhone;
    @BindView(R.id.tv_receiver)
    TextView tvReceiver;
    @BindView(R.id.tv_receiver_phone)
    TextView tvReceiverPhone;
    @BindView(R.id.img_receiver_phone)
    ImageView imgReceiverPhone;
    @BindView(R.id.cv_call_phone)
    CardView cvCallPhone;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_logistics_detail;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("订单详情");
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
    stopProgressDialog();
    }


    @OnClick({R.id.ll_toolbar_left, R.id.img_sender_phone, R.id.img_receiver_phone,
            R.id.cv_call_phone ,R.id.tv_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //打电话给收货人
            case R.id.img_receiver_phone:
                break;
            //打电话给货主
            case R.id.img_sender_phone:
            case R.id.cv_call_phone:
                break;
            //查看地图
            case R.id.tv_map:
                break;
        }
    }
}
