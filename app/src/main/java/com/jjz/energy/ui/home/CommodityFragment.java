package com.jjz.energy.ui.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.HomeCommodityTypeAdapter;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Features: 宝贝列表
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
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        HomeCommodityTypeAdapter commodityTypeAdapter =
                new HomeCommodityTypeAdapter(R.layout.item_commodity_grid, list);
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
