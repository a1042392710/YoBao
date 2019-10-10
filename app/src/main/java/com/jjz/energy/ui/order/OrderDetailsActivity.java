package com.jjz.energy.ui.order;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.OrderDetailsStatusAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

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

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_order_details;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("订单详情");

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        rvOrderStatus.setLayoutManager(new GridLayoutManager(this,5));
        OrderDetailsStatusAdapter mAdapter = new OrderDetailsStatusAdapter(R.layout.item_order_status,list);
        rvOrderStatus.setAdapter(mAdapter);
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
