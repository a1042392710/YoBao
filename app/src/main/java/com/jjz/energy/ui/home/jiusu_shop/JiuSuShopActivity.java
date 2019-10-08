package com.jjz.energy.ui.home.jiusu_shop;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.adapter.JiuSuShopListAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 久速入驻商家
 * @author: create by chenhao on 2019/10/8
 */
public class JiuSuShopActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.rv_shop_type)
    RecyclerView rvShopType;
    @BindView(R.id.rv_shop_list)
    RecyclerView rvShopList;
    /**
     * 商户类型
     */
    private JiuSuShopTypeAdapter mShopTypeAdapter;
    /**
     * 商户列表
     */
    private JiuSuShopListAdapter mShopListAdapter;


    @Override
    protected void initView() {
        initRv();
    }

    /**
     * 初始化商户Rv
     */
    private void initRv() {
        List<String> list = new ArrayList<>();
        list.add("汽车市场");
        list.add("家居日用");
        list.add("数码家电");
        list.add("美食餐饮");
        list.add("精品酒店");
        list.add("丽日美发");
        list.add("文学图书");
        list.add("卡拉OK");
        list.add("服饰箱包");
        list.add("全部分类");
        //商户
        rvShopType.setLayoutManager(new GridLayoutManager(this, 5));
        mShopTypeAdapter = new JiuSuShopTypeAdapter(R.layout.item_jius_shop_type, list);
        rvShopType.setAdapter(mShopTypeAdapter);
        //商户列表  显示一些推荐商户
        rvShopList.setLayoutManager(new LinearLayoutManager(this));
        List<String> list2 = new ArrayList<>();
        list2.add("");
        list2.add("");
        list2.add("");
        mShopListAdapter = new JiuSuShopListAdapter(R.layout.item_jiusu_shop_list, list2);
        rvShopList.setAdapter(mShopListAdapter);

    }

    @Override
    public void showLoading() { startProgressDialog(); }

    @Override
    public void stopLoading() { stopProgressDialog(); }

    @Override
    protected BasePresenter getPresenter() { return null; }

    @Override
    protected int layoutId() { return R.layout.act_jiusu_shop; }

    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }

    /**
     * 商户类型
     */
    class JiuSuShopTypeAdapter extends BaseRecycleNewAdapter<String> {

        public JiuSuShopTypeAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}
