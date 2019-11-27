package com.jjz.energy.ui.jiusu_shop;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.ShopHomePageCommodityAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.jiusu_shop.ShopHomePageBean;
import com.jjz.energy.presenter.mine.HomePagePresenter;
import com.jjz.energy.ui.home.commodity.CommodityDetailActivity;
import com.jjz.energy.util.networkUtil.AesUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.mine.IHomePageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 个人主页 商品列表
 * @author: create by chenhao on 2019/8/6
 */
public class JiuSuShopAllGoodsActivity extends BaseActivity<HomePagePresenter> implements IHomePageView {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;

    private ShopHomePageCommodityAdapter mAdapter;

    /**
     * 页码
     */
    private int mPage = 1;

    /*
     * 是否加载更多
     */
    private boolean isLoadMore;

    /**
     * 用户id
     */
    private int shop_id;

    @Override
    protected HomePagePresenter getPresenter() {
        return new HomePagePresenter(this);
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("全部商品");
        //用户id
        shop_id = getIntent().getIntExtra(Constant.SHOP_ID, 0);
        initRvAndRefresh();
        getData(false);
    }

    /**
     * 初始化监听
     */
    private void initRvAndRefresh() {
        //禁止刷新
        mSmartRefreshLayout.setEnableRefresh(false);
        //初始化 rv
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ShopHomePageCommodityAdapter(R.layout.item_shop_all_commodity,
                new ArrayList<>());
        rvList.setAdapter(mAdapter);
        //点击子项进详情页面
        mAdapter.setOnItemClickListener((adapter, view, position) ->
                startActivity(new Intent(mContext, CommodityDetailActivity.class)
                        .putExtra(Constant.GOODS_ID,
                                mAdapter.getData().get(position)  .getGoods_id())));
        //上拉加载
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            //获取指定类别的商品
            getData(true);
        });

    }

    /**
     * 获取数据
     *
     * @param isLoadMore
     */
    private void getData(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("shop_id", AesUtils.encrypt(String.valueOf(shop_id), AesUtils.KEY,
                AesUtils.IV));
        hashMap.put("page", mPage + "");
        mPresenter.getShopAllGoods(PacketUtil.getRequestPacket(hashMap), isLoadMore);
    }


    @Override
    public void isGetShopAllGoods(ShopHomePageBean data) {
        //加载更多
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mAdapter.addNewData(data.getCommodityList()))
                mSmartRefreshLayout.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mAdapter.notifyChangeData(data.getCommodityList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_data, "这里还没有商品", false,
                        null));
                mSmartRefreshLayout.setEnableLoadMore(false);
            } else {
                //有数据就开启加载更多
                if (mAdapter.getData().size() > 8) {
                    mSmartRefreshLayout.setEnableLoadMore(true);
                }
            }
        }
        closeRefresh(mSmartRefreshLayout);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        //请求失败时页码归位
        if (isLoadMore) {
            mPage--;
        }
        closeRefresh(mSmartRefreshLayout);
        showToast(msg);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_jiusu_shop_all_goods_list;
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }
}
