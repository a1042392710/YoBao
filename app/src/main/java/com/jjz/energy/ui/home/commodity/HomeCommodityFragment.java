package com.jjz.energy.ui.home.commodity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.HomeGoodsAdapter;
import com.jjz.energy.base.BaseLazyFragment;
import com.jjz.energy.entry.GoodsListBean;
import com.jjz.energy.presenter.home.HomeCommodityPresenter;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.home.IHomeCommodityView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * @Features: 商品列表
 * @author: create by chenhao on 2019/10/11
 */
public class HomeCommodityFragment extends BaseLazyFragment<HomeCommodityPresenter> implements IHomeCommodityView {
    @BindView(R.id.rvType)
    RecyclerView rvType;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    /**
     * 页码
     */
    private int  mPage=1;
    /**
     * 分类Id
     */
    private int typeId = 0;
    /**
     * 商品分类
     */
    public static final String GOODS_TYPE = "type";

    private boolean isLoadMore;
    /**
     * 商品列表
     */
    private HomeGoodsAdapter mGoodsAdapter;

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        typeId = bundle.getInt(GOODS_TYPE);
        initRv();
        initListener();
        getData(false);
    }

    /**
     * 初始化列表
     */
    private void initRv() {
        rvType.setLayoutManager(new GridLayoutManager(mContext, 2));
        //商品适配器
        mGoodsAdapter = new HomeGoodsAdapter(R.layout.item_commodity_grid, new ArrayList<>());
        //解决图片闪烁的问题
        mGoodsAdapter.setHasStableIds(true);
        ((DefaultItemAnimator) rvType.getItemAnimator()).setSupportsChangeAnimations(false);
        rvType.setAdapter(mGoodsAdapter);
        //子项点击事件
        mGoodsAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(CommodityDetailActivity.GOODS_ID,
                    mGoodsAdapter.getData().get(position).getGoods_id());
            loginStartActivity(CommodityDetailActivity.class, bundle);
        });
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        //上拉加载
        smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            //获取指定类别的商品
            getData(true);
        });
    }

    /**
     * 获取数据
     * @param isLoadMore
     */
    private void getData (boolean isLoadMore){
        this.isLoadMore = isLoadMore;
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("cat_id",typeId+"");
        hashMap.put("page", mPage + "");
        mPresenter.getGoodsList(PacketUtil.getRequestPacket(hashMap), isLoadMore);
    }

    /**
     * 刷新页面
     */
    public void refresh() {
        mPage = 1;
        getData(false);
    }

    @Override
    public void isGetGoodsSuc(GoodsListBean data) {
        //加载更多
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mGoodsAdapter.addNewData(data.getList()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mGoodsAdapter.notifyChangeData(data.getList())){
                mGoodsAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_notice,"这里还没有宝贝",true, v -> {
                    //点击刷新
                    getData(false);
                }));
            }else{
                //有数据就开启加载更多
                smartRefresh.setEnableLoadMore(true);
            }
        }
        closeRefresh(smartRefresh);
    }

    @Override
    public void isGetGoodsFail(String msg, boolean isNetAndServiceError) {
        //请求失败时页码归位
        if (isLoadMore){
            mPage--;
        }
        closeRefresh(smartRefresh);
        showToast(msg);
    }


    @Override
    protected int layoutId() {
        return R.layout.fragment_home_commodity;
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    protected HomeCommodityPresenter getPresenter() {
        return new HomeCommodityPresenter(this);
    }


}
