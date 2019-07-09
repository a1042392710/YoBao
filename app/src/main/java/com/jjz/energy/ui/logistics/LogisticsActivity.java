package com.jjz.energy.ui.logistics;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 物流中心
 * @author: create by chenhao on 2019/6/28
 */
public class LogisticsActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rb_all)
    RadioButton rbAll;
    @BindView(R.id.rb_nearby)
    RadioButton rbNearby;
    @BindView(R.id.rg_goods)
    RadioGroup rgGoods;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.img_sort)
    ImageView imgSort;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_logistics;
    }

    @Override
    protected void initView() {
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        LogisticsAdapter mAdapter = new LogisticsAdapter(R.layout.item_logistics,list);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext,LogisticsDetailActivity.class));
        });
        rvGoods.setAdapter(mAdapter);

    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    @OnClick({R.id.img_back, R.id.tv_first, R.id.img_sort, R.id.tv_end, R.id.tv_time, R.id.tv_sort})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
                //起点
            case R.id.tv_first:

                break;
                //起点终点翻转
            case R.id.img_sort:
                break;
                //终点
            case R.id.tv_end:
                break;
                //时间
            case R.id.tv_time:
                break;
                //排序
            case R.id.tv_sort:
                break;
        }
    }
    /**
     * 物流列表
     */
    class LogisticsAdapter extends BaseRecycleNewAdapter<String>{

        public LogisticsAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

}
