package com.jjz.energy.ui.mine;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 我卖出的
 * @author: create by chenhao on 2019/7/24
 */
public class MineSellerActivity extends BaseActivity {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_mine_seller)
    RecyclerView rvMineSeller;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_seller;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("我卖出的");
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        MineListAdapter mineListAdapter = new MineListAdapter(R.layout.item_mine_buyer,list);
        rvMineSeller.setLayoutManager(new LinearLayoutManager(mContext));
        rvMineSeller.setAdapter(mineListAdapter);
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

    //列表适配器
    class MineListAdapter extends BaseRecycleNewAdapter<String>{

        public MineListAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}
