package com.jjz.energy.ui.jiusu_shop;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.JiuSuShopListAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopBean;
import com.jjz.energy.presenter.jiusu_shop.JiuSuShopPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu_shop.IJiuSuShopView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features:  商家分类列表
 * @author: create by chenhao on 2019/11/25
 */
public class ShopCateListActivity extends BaseActivity<JiuSuShopPresenter> implements IJiuSuShopView, OnRefreshLoadMoreListener {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_shopping_list)
    RecyclerView rvShoppingList;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    /**
     * 页码
     */
    private int mPage=1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;

    private JiuSuShopListAdapter mAdapter;
    /**
     * 指定分类
     */
    private int cate_id;

    @Override
    protected void initView() {
        tvToolbarTitle.setText(getIntent().getStringExtra("title"));
        cate_id = getIntent().getIntExtra("cate_id",0);
        //绑定刷新和加载
        smartRefresh.setOnRefreshLoadMoreListener(this);
        mAdapter = new JiuSuShopListAdapter(R.layout.item_jiusu_shop_list,new ArrayList<>());
        rvShoppingList.setLayoutManager(new LinearLayoutManager(mContext));
        rvShoppingList.setAdapter(mAdapter);
        //跳转详情页面
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        getData(false);
    }

    /**
     * 查询指定分类下的商家
     * @param isLoadMore 是否加载更多
     */
    private void getData(boolean isLoadMore){
        this.isLoadMore = isLoadMore;
        HashMap<String,String> map = new HashMap<>();
        map.put("cate_id",cate_id+"");
        map.put("page",mPage+"");
        mPresenter.getShopList(PacketUtil.getRequestPacket(map),isLoadMore);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        getData(true);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPage=1;
        getData(false);
    }


    @Override
    public void isGetClassAndShopListSuccess(JiuSuShopBean data) {
        if (!StringUtil.isListEmpty(data.getList()) && data.getList().size()>8){
            smartRefresh.setEnableLoadMore(true);     //有够长的数据就开启加载更多
        }else{
            smartRefresh.setEnableLoadMore(false);     //否则关闭
        }
        //加载更多
        if (isLoadMore) {
            mAdapter.addNewData(data.getList());
        } else {
            //刷新数据，无数据显示空页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "该分类还没有入驻的商家", true, v -> {
                    getData(false);
                }));
            }
        }
        closeRefresh(smartRefresh);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isLoadMore){
            mPage--;
        }
        closeRefresh(smartRefresh);
        showToast(msg);
    }

    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showLoading() {
        startProgressDialog(); }
    @Override
    public void stopLoading() { stopProgressDialog();
    }
    @Override
    protected int layoutId() {return R.layout.act_jiusu_shopping_list;
    }
    @Override
    protected JiuSuShopPresenter getPresenter() {return new JiuSuShopPresenter(this);
    }


}
