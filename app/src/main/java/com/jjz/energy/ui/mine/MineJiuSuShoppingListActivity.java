package com.jjz.energy.ui.mine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.MineJiuSuShippingAdpter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.jiusu_shop.JiuSuShoppingBean;
import com.jjz.energy.presenter.jiusu_shop.JiuSuShopPresenter;
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
 * @Features: 久速商家店内消费记录
 * @author: create by chenhao on 2019/11/25
 */
public class MineJiuSuShoppingListActivity extends BaseActivity<JiuSuShopPresenter> implements IJiuSuShopView, OnRefreshLoadMoreListener {

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
        tvToolbarTitle.setText("店内消费记录");
        //绑定刷新和加载
        smartRefresh.setOnRefreshLoadMoreListener(this);
        mAdapter = new MineJiuSuShippingAdpter(R.layout.item_jiusu_shopping, new ArrayList<>());
        rvShoppingList.setLayoutManager(new LinearLayoutManager(mContext));
        rvShoppingList.setAdapter(mAdapter);
        //跳转详情页面
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext, MineJiuSuShoppingDetailsActivity.class).putExtra("id", 0));
        });

    }

    /**
     * 查询消费记录
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
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mAdapter.addNewData(data.getList()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_data, "您还没有久速商户店内消费记录", false,
                        null));
                smartRefresh.setEnableLoadMore(false);
            } else {
                //有数据就开启加载更多
                if (data.getList().size() > 8){
                    smartRefresh.setEnableLoadMore(true);
                }else{
                    smartRefresh.setEnableLoadMore(false);
                }
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
