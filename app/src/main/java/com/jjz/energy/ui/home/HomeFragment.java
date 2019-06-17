package com.jjz.energy.ui.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.HomeAdapter;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.HomeBean;
import com.jjz.energy.entry.HomeItemEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Features: 首页
 * @author: create by chenhao on 2019/5/31
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    /**
     * 适配器
     */
    private HomeAdapter mHomeAdapter;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        rvHome.setLayoutManager(new LinearLayoutManager(mContext));
        List<HomeItemEntry> list = new ArrayList<>();
        HomeBean homeBean = new HomeBean();
        //banner 的数据
        List<String>bannerList = new ArrayList<>();
        bannerList.add("http://img5.imgtn.bdimg.com/it/u=3300305952,1328708913&fm=26&gp=0.jpg");
        bannerList.add("http://img4.imgtn.bdimg.com/it/u=2153937626,1074119156&fm=26&gp=0.jpg");
        bannerList.add("http://img2.imgtn.bdimg.com/it/u=1409224092,1124266154&fm=26&gp=0.jpg");
        homeBean.setBannerList(bannerList);
        list.add(new HomeItemEntry(1,homeBean));
        list.add(new HomeItemEntry(2,new HomeBean()));
        list.add(new HomeItemEntry(3,new HomeBean()));
        mHomeAdapter = new HomeAdapter(list);
        rvHome.setAdapter(mHomeAdapter);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

}
