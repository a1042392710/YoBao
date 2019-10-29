package com.jjz.energy.ui.mine.shop_order;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.presenter.order.EvaluatePresenter;
import com.jjz.energy.view.order.IEvaluateView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 查看评价
 * @author: create by chenhao on 2019/10/26
 */
public class EvaluateDetailsActivity extends BaseActivity<EvaluatePresenter> implements IEvaluateView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_commodity)
    ImageView imgCommodity;
    @BindView(R.id.tv_commodity_title)
    TextView tvCommodityTitle;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.img_her_head)
    ImageView imgHerHead;
    @BindView(R.id.tv_her_name)
    TextView tvHerName;
    @BindView(R.id.tv_her_evaluate)
    TextView tvHerEvaluate;
    @BindView(R.id.tv_her_evaluate_time)
    TextView tvHerEvaluateTime;
    @BindView(R.id.img_her_evaluate)
    ImageView imgHerEvaluate;
    @BindView(R.id.img_her_evaluate_mark)
    ImageView imgHerEvaluateMark;
    @BindView(R.id.rv_her)
    RelativeLayout rvHer;
    @BindView(R.id.img_my_head)
    ImageView imgMyHead;
    @BindView(R.id.tv_my_name)
    TextView tvMyName;
    @BindView(R.id.tv_my_evaluate)
    TextView tvMyEvaluate;
    @BindView(R.id.tv_my_evaluate_time)
    TextView tvMyEvaluateTime;
    @BindView(R.id.img_my_evaluate)
    ImageView imgMyEvaluate;
    @BindView(R.id.img_my_evaluate_mark)
    ImageView imgMyEvaluateMark;
    @BindView(R.id.rv_mine)
    RelativeLayout rvMine;
    @BindView(R.id.tv_evaluate_toast)
    TextView tvEvaluateToast;
    /**
     * 订单编号
     */
    private String order_sn;



    @Override
    protected void initView() {
        order_sn = getIntent().getStringExtra(Constant.ORDER_SN );
        tvToolbarTitle.setText("评价详情");
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
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }

    @Override
    protected EvaluatePresenter getPresenter() {
        return new EvaluatePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_evaluate_details;
    }


    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }
}
