package com.jjz.energy.ui.mine;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.MineFansAndLikeAdapter;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.ui.mine.information.HomePageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Features: 我关注的
 * @author: create by chenhao on 2019/8/6
 */
public class MineLikeFragment extends BaseFragment {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
            List<String> list = new ArrayList<>();
            list.add("");
            list.add("");
            list.add("");
            list.add("");
            list.add("");
            list.add("");
            MineFansAndLikeAdapter mAdapter =
                    new MineFansAndLikeAdapter(R.layout.item_mine_fans, list);
            rvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> startActivity(new Intent(mContext, HomePageActivity.class)));
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
