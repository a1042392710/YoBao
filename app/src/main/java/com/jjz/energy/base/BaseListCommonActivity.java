package com.jjz.energy.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.MineJiuSuShippingAdpter;
import com.jjz.energy.entry.jiusu_shop.JiuSuShoppingBean;
import com.jjz.energy.presenter.jiusu_shop.JiuSuShopPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu_shop.IJiuSuShopView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features:  列表的模板  直接复制
 * @author: create by chenhao on 2019/11/25
 */
public class BaseListCommonActivity extends BaseActivity<JiuSuShopPresenter> implements IJiuSuShopView, OnRefreshLoadMoreListener {

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

    private MineJiuSuShippingAdpter mAdapter;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("标题");
        //绑定刷新和加载
        smartRefresh.setOnRefreshLoadMoreListener(this);
        mAdapter = new MineJiuSuShippingAdpter(R.layout.item_mine_buyer, new ArrayList<>());
        rvShoppingList.setLayoutManager(new LinearLayoutManager(mContext));
        rvShoppingList.setAdapter(mAdapter);
        //跳转详情页面
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        getData(false);
    }

    /**
     * 获取数据
     * @param isLoadMore 是否加载更多
     */
    private void getData(boolean isLoadMore){
        this.isLoadMore = isLoadMore;
        mPresenter.getJiuSuShoppingList(PacketUtil.getRequestPacket(Utils.stringToMap("page",mPage+"")),isLoadMore);
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
    public void isGetJiusuShoppingListSuc(JiuSuShoppingBean data) {
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
