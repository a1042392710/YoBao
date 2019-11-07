package com.jjz.energy.ui.notice;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenhao 2018/11/27
 * @function 消息总列表
 */
public class SystemNoticeActivity extends BaseActivity  {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_system_notice)
    ImageView imgSystemNotice;
    @BindView(R.id.tv_system_title)
    TextView tvSystemTitle;
    @BindView(R.id.tv_system_content)
    TextView tvSystemContent;
    @BindView(R.id.tv_system_time)
    TextView tvSystemTime;
    @BindView(R.id.rl_system)
    RelativeLayout rlSystem;
    @BindView(R.id.img_order_notice)
    ImageView imgOrderNotice;
    @BindView(R.id.tv_order_title)
    TextView tvOrderTitle;
    @BindView(R.id.tv_order_content)
    TextView tvOrderContent;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.img_logistics_notice)
    ImageView imgLogisticsNotice;
    @BindView(R.id.tv_logistics_title)
    TextView tvLogisticsTitle;
    @BindView(R.id.tv_logistics_content)
    TextView tvLogisticsContent;
    @BindView(R.id.tv_logistics_time)
    TextView tvLogisticsTime;
    @BindView(R.id.rl_logistics)
    RelativeLayout rlLogistics;
    @BindView(R.id.img_message_notice)
    ImageView imgMessageNotice;
    @BindView(R.id.tv_message_title)
    TextView tvMessageTitle;
    @BindView(R.id.tv_message_content)
    TextView tvMessageContent;
    @BindView(R.id.tv_message_time)
    TextView tvMessageTime;
    @BindView(R.id.rl_message)
    RelativeLayout rlMessage;


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_system_notice;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("消息");
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @OnClick({R.id.ll_toolbar_left, R.id.rl_system, R.id.rl_order, R.id.rl_logistics,
            R.id.rl_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //系统消息
            case R.id.rl_system:
                break;
                //订单消息
            case R.id.rl_order:
                break;
                //通知消息
            case R.id.rl_logistics:
                break;
                //留言评论消息
            case R.id.rl_message:
                break;
        }
    }
}
