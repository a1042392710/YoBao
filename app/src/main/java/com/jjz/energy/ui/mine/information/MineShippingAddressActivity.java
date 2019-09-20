package com.jjz.energy.ui.mine.information;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.AddressManagerAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 管理收货地址
 * @author: create by chenhao on 2019/9/3
 */
public class MineShippingAddressActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_address)
    RecyclerView rvAddress;
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_shipping_address;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("收货地址");
        rvAddress.setLayoutManager(new LinearLayoutManager(mContext));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        AddressManagerAdapter managerAdapter = new AddressManagerAdapter(R.layout.item_shipping_address,list);
        rvAddress.setAdapter(managerAdapter);
        managerAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext,UpdateAddressActivity.class));
        });
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
    stopProgressDialog();
    }



    @OnClick({R.id.ll_toolbar_left, R.id.tv_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //添加收货地址
            case R.id.tv_add_address:
                startActivity(new Intent(mContext,MineShippingAddressActivity.class));
                break;
        }
    }
}
