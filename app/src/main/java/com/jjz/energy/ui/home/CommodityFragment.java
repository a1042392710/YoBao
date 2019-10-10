package com.jjz.energy.ui.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.HomeGoodsAdapter;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.base.BasePresenter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @Features: 商品列表
 * @author: create by chenhao on 2019/8/6
 */
public class CommodityFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        rvList.setLayoutManager(new GridLayoutManager(mContext, 2));

        HomeGoodsAdapter commodityTypeAdapter =
                new HomeGoodsAdapter(R.layout.item_commodity_grid, new ArrayList<>());
        rvList.setAdapter(commodityTypeAdapter);

        commodityTypeAdapter.setOnItemClickListener((adapter, view, position) -> startActivity(new Intent(mContext,CommodityDetailActivity.class)));
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }
}
