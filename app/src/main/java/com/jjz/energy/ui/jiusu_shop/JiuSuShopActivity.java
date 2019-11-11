package com.jjz.energy.ui.jiusu_shop;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.adapter.JiuSuShopListAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.jiusu_shop.JiuSuShopBean;
import com.jjz.energy.presenter.jiusu_shop.JiuSuShopPresenter;
import com.jjz.energy.ui.home.SearchActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu_shop.IJiuSuShopView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 久速入驻商家
 * @author: create by chenhao on 2019/10/8
 */
public class JiuSuShopActivity extends BaseActivity <JiuSuShopPresenter> implements IJiuSuShopView, OnLoadMoreListener {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.rv_shop_type)
    RecyclerView rvShopType;
    @BindView(R.id.rv_shop_list)
    RecyclerView rvShopList;
    @BindView(R.id.card_search)
    CardView cardSearch;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    /**
     * 商户类型
     */
    private JiuSuShopTypeAdapter mShopTypeAdapter;
    /**
     * 商户列表
     */
    private JiuSuShopListAdapter mShopListAdapter;
    /**
     * 页码
     */
    private int mPage = 1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;

    @Override
    protected void initView() {
        initRv();
    }

    /**
     * 初始化商户Rv
     */
    private void initRv() {
        smartRefresh.setOnLoadMoreListener(this);
        //商户
        rvShopType.setLayoutManager(new GridLayoutManager(this, 5));
        mShopTypeAdapter = new JiuSuShopTypeAdapter(R.layout.item_jius_shop_type, new ArrayList<>());
        rvShopType.setAdapter(mShopTypeAdapter);
        //商户列表  显示一些推荐商户
        rvShopList.setLayoutManager(new LinearLayoutManager(this));
        mShopListAdapter = new JiuSuShopListAdapter(R.layout.item_jiusu_shop_list, new ArrayList<>());
        rvShopList.setAdapter(mShopListAdapter);
        getData(false);
    }

    /**
     * 获取数据
     */
    private void getData(boolean isLoadMore){
        this.isLoadMore = isLoadMore;
        mPresenter.getShopList(PacketUtil.getRequestPacket(Utils.stringToMap("page",mPage+"")),isLoadMore);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        getData(true);
    }

    @Override
    public void isGetClassAndShopListSuccess(JiuSuShopBean data) {
        //绑定分类信息
        if (StringUtil.isListEmpty(mShopTypeAdapter.getData())){
            mShopTypeAdapter.addNewData(data.getClass_list());
        }
        //商家列表数据
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mShopListAdapter.addNewData(data.getShop_list()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mShopListAdapter.notifyChangeData(data.getShop_list())) {
                mShopListAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "您还没有物流消息", false,
                        null));
                smartRefresh.setEnableLoadMore(false);
            } else {
                if (data.getShop_list().size()>6){
                    //有数据就开启加载更多
                    smartRefresh.setEnableLoadMore(true);
                }else{
                    //有数据就开启加载更多
                    smartRefresh.setEnableLoadMore(false);
                }
            }
        }
        closeRefresh(smartRefresh);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.ll_toolbar_left, R.id.card_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //搜索 进入搜索页面
            case R.id.card_search:
                startActivity(new Intent(mContext, SearchActivity.class).putExtra(SearchActivity.SEARCH_TYPE,SearchActivity.SEARCH_SHOP)
                        , ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
        }
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }

    @Override
    public void showLoading() { startProgressDialog(); }

    @Override
    public void stopLoading() { stopProgressDialog(); }

    @Override
    protected JiuSuShopPresenter getPresenter() { return new JiuSuShopPresenter(this); }

    @Override
    protected int layoutId() { return R.layout.act_jiusu_shop; }


    /**
     * 商户类型
     */
    class JiuSuShopTypeAdapter extends BaseRecycleNewAdapter<JiuSuShopBean.ClassListBean> {

        public JiuSuShopTypeAdapter(int layoutResId, @Nullable List<JiuSuShopBean.ClassListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, JiuSuShopBean.ClassListBean item) {
            helper.setText(R.id.item_tv_shop_type_name, item.getType());
            ImageView img = helper.getView(R.id.item_img_shop_type);
            GlideUtils.loadImage(mContext,item.getImg(),img);
        }
    }
}
