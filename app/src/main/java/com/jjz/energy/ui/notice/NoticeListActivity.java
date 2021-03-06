package com.jjz.energy.ui.notice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.NoticeListInfo;
import com.jjz.energy.presenter.home.NoticePresenter;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.home.INoticeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * @author chenhao 2018/11/27
 * @function 消息总列表
 */
public class NoticeListActivity extends BaseActivity<NoticePresenter> implements INoticeView,
        OnRefreshListener {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_system_title)
    TextView tvSystemTitle;
    @BindView(R.id.tv_system_content)
    TextView tvSystemContent;
    @BindView(R.id.tv_system_time)
    TextView tvSystemTime;
    @BindView(R.id.rl_system)
    RelativeLayout rlSystem;
    @BindView(R.id.tv_order_title)
    TextView tvOrderTitle;
    @BindView(R.id.tv_order_content)
    TextView tvOrderContent;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.tv_message_title)
    TextView tvMessageTitle;
    @BindView(R.id.tv_message_content)
    TextView tvMessageContent;
    @BindView(R.id.tv_message_time)
    TextView tvMessageTime;
    @BindView(R.id.rl_message)
    RelativeLayout rlMessage;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tv_evaluation_content)
    TextView tvEvaluationContent;
    @BindView(R.id.tv_evaluation_time)
    TextView tvEvaluationTime;
    //红点
    private Badge mOrderRed, mSystemRed, mMessageRed ,mEvaluationRed;

    private ImageView imgMessageNotice;
    private ImageView imgLogisticsNotice;
    private ImageView imgOrderNotice;
    private ImageView imgSystemNotice;
    private ImageView imgEvaluationNotice;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("消息");
        imgMessageNotice = findViewById(R.id.img_message_notice);
        imgOrderNotice = findViewById(R.id.img_order_notice);
        imgSystemNotice = findViewById(R.id.img_system_notice);
        imgEvaluationNotice = findViewById(R.id.img_evaluation_notice);
        smartRefresh.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getNoticeListInfo(PacketUtil.getRequestPacket(null), true);
    }

    @Override
    public void isGetNoticeListInfoSuc(NoticeListInfo data) {
        //设置各项数据
        if (data.getMessages() != null) {
            if (mMessageRed != null) {
                mMessageRed.setBadgeNumber( data.getMessages().getUnread_num());
            } else {
                mMessageRed = new QBadgeView(mContext).bindTarget(imgMessageNotice).setBadgeGravity(Gravity.TOP | Gravity.END)
                        .setBadgeTextSize(8, true).setBadgeNumber(data.getMessages().getUnread_num()).setGravityOffset(2, 0,
                                true);
            }
            tvMessageContent.setText(data.getMessages().getMessages());
            tvMessageTime.setText(DateUtil.longToDate(data.getMessages().getLast_time(), null));
        } else {
            tvMessageTime.setText("");
            tvMessageContent.setText("暂无新消息");
            if (mMessageRed != null) {
                mMessageRed.setBadgeNumber(0);
            }
        }
        //设置各项数据
        if (data.getOrder() != null) {
            if (mOrderRed != null) {
                mOrderRed.setBadgeNumber( data.getOrder().getUnread_num());
            } else {
                mOrderRed = new QBadgeView(mContext).bindTarget(imgOrderNotice).setBadgeGravity(Gravity.TOP | Gravity.END)
                        .setBadgeTextSize(8, true).setBadgeNumber(data.getOrder().getUnread_num()).setGravityOffset(2, 0,
                                true);
            }
            tvOrderContent.setText(data.getOrder().getMessages());
            tvOrderTime.setText(DateUtil.longToDate(data.getOrder().getLast_time(), null));
        } else {
            tvOrderTime.setText("");
            tvOrderContent.setText("暂无新消息");
            if (mOrderRed != null) {
                mOrderRed.setBadgeNumber(0);
            }
        }
        //设置各项数据
        if (data.getSystem() != null) {
            if (mSystemRed != null) {
                mSystemRed.setBadgeNumber( data.getSystem().getUnread_num());
            } else {
                mSystemRed = new QBadgeView(mContext).bindTarget(imgSystemNotice).setBadgeGravity(Gravity.TOP | Gravity.END)
                        .setBadgeTextSize(8, true).setBadgeNumber(data.getSystem().getUnread_num()).setGravityOffset(2, 0, true);
            }
            tvSystemContent.setText(data.getSystem().getMessages());
            tvSystemTime.setText(DateUtil.longToDate(data.getSystem().getLast_time(), null));
        } else {
            tvSystemTime.setText("");
            tvSystemContent.setText("暂无新消息");
            if (mSystemRed != null) {
                mSystemRed.setBadgeNumber(0);
            }
        }
        //设置各项数据
        if (data.getCommunity() != null) {
            if (mEvaluationRed != null) {
                mEvaluationRed.setBadgeNumber( data.getCommunity().getUnread_num());
            } else {
                mEvaluationRed = new QBadgeView(mContext).bindTarget(imgEvaluationNotice).setBadgeGravity(Gravity.TOP | Gravity.END)
                        .setBadgeTextSize(8, true).setBadgeNumber(data.getCommunity().getUnread_num()).setGravityOffset(2, 0, true);
            }
            tvEvaluationContent.setText(data.getCommunity().getMessages());
            tvEvaluationTime.setText(DateUtil.longToDate(data.getCommunity().getLast_time(), null));
        } else {
            tvEvaluationTime.setText("");
            tvEvaluationContent.setText("暂无新消息");
            if (mEvaluationRed != null) {
                mEvaluationRed.setBadgeNumber(0);
            }
        }


        closeRefresh(smartRefresh);
    }

    @OnClick({R.id.ll_toolbar_left, R.id.rl_system, R.id.rl_order,
            R.id.rl_message ,R.id.rl_evaluation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //系统消息
            case R.id.rl_system:
                startActivity(new Intent(mContext, SystemNoticeActivity.class));
                break;
            //订单消息
            case R.id.rl_order:
                startActivity(new Intent(mContext, OrderNoticeActivity.class));
                break;

            //留言消息
            case R.id.rl_message:
                startActivity(new Intent(mContext, CommentNoticeActivity.class));
                break;
            //社区消息
            case R.id.rl_evaluation:
                startActivity(new Intent(mContext, CommunityNoticeActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected NoticePresenter getPresenter() {
        return new NoticePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_system_notice;
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
        closeRefresh(smartRefresh);
        showToast(msg);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getNoticeListInfo(PacketUtil.getRequestPacket(null), false);
    }
}
