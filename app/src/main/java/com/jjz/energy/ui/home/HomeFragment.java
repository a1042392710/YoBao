package com.jjz.energy.ui.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.HomeGoodsAdapter;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.entry.GoodsBean;
import com.jjz.energy.entry.GoodsClassificationBean;
import com.jjz.energy.entry.HomeDetailBean;
import com.jjz.energy.entry.event.LocationEvent;
import com.jjz.energy.presenter.home.HomePresenter;
import com.jjz.energy.ui.city.CityPickerActivity;
import com.jjz.energy.ui.home.charitable.CharitableActivity;
import com.jjz.energy.ui.home.education.EducationActivity;
import com.jjz.energy.ui.home.insurance.InsuranceActivity;
import com.jjz.energy.ui.home.jiusu_shop.JiuSuShopActivity;
import com.jjz.energy.ui.home.logistics.LogisticsActivity;
import com.jjz.energy.ui.home.pension.PensionActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideImageLoader;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.home.IHomeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 首页
 * @author: create by chenhao on 2019/5/31
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements IHomeView {

    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.card_search)
    CardView cardSearch;
    @BindView(R.id.img_notice)
    ImageView imgNotice;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.ll_logistics)
    LinearLayout llLogistics;
    @BindView(R.id.ll_insurance)
    LinearLayout llInsurance;
    @BindView(R.id.ll_old)
    LinearLayout llOld;
    @BindView(R.id.ll_charitable)
    LinearLayout llCharitable;
    @BindView(R.id.ll_education)
    LinearLayout llEducation;
    @BindView(R.id.ll_jiusu)
    LinearLayout llJiusu;
    @BindView(R.id.ll_same_city)
    LinearLayout llSameCity;
    @BindView(R.id.ll_barter)
    LinearLayout llBarter;
    @BindView(R.id.ll_shop_discount)
    LinearLayout llShopDiscount;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.rvType)
    RecyclerView rvType;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    /**
     * 页码
     */
    private int mPage = 1;
    /**
     * 是否为加载更多
     */
    private boolean isLoadMore = false;
    /**
     * 商品分类列表
     */
    private HomeDetailBean mHomeDetail;
    /**
     * 商品分类Id
     */
    private int mGoodsTypeId = 0;

    private HomeGoodsAdapter mGoodsAdapter;

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        initRvAndRefresh();
        initListener();
        //获取商品分类
        mPresenter.getSortAndBanner(PacketUtil.getRequestPacket(null));
        //加载数据
        getData(false);
    }



    /**
     * 初始化banner
     */
    private void initBanner(List<com.jjz.energy.entry.Banner> homeBeans) {
        //处理好数据放入banner中
        List<String> list = new ArrayList<>();
        for (com.jjz.energy.entry.Banner homeBean : homeBeans) {
            list.add(homeBean.getImage());
        }
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //轮播点击事件
        banner.setOnBannerListener(position -> {
        });
        banner.start();
    }

    /**
     * 初始化商品分类列表 和 下拉上拉
     */
    private void initRvAndRefresh(){
        rvType.setLayoutManager(new GridLayoutManager(mContext, 2));
        //商品适配器
        mGoodsAdapter = new HomeGoodsAdapter(R.layout.item_commodity_grid, new ArrayList<>());
        rvType.setAdapter(mGoodsAdapter);
        mGoodsAdapter.setOnItemClickListener((adapter, view, position) ->
                startActivity(new Intent(mContext, CommodityDetailActivity.class)));


    }


    /**
     * 处理各种监听
     */
    private void initListener() {
        smartRefresh.setEnableRefresh(true);
        smartRefresh.setEnableLoadMore(true);
        //上拉加载 下拉刷新
        smartRefresh.setOnRefreshListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //获取指定类别的商品
                getData(true);
                mPage++;
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //刷新列表数据
                mPresenter.getSortAndBanner(PacketUtil.getRequestPacket(null));
                //重置各种状态后 刷新商品信息
                getData(false);
                mGoodsTypeId=0;
                mPage=0;
            }
        });


        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中时 重置页码 刷新列表
                mPage=0;
                //记录选中的商品类型
                mGoodsTypeId = mHomeDetail.getCateList().get(tab.getPosition()).getId();
                //访问数据
                getData(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 获取数据
     * @param isLoadMore
     */
    private void getData (boolean isLoadMore){
        this.isLoadMore = isLoadMore;
        mPresenter.getGoodsList(PacketUtil.getRequestPacket(Utils.stringToMap("cat_id",mGoodsTypeId+"")),isLoadMore);
    }

    /**
     * 当前城市
     */
    private String mCityName;

    //接受定位消息，显示城市名称
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCity(LocationEvent locationEvent) {
        mCityName = locationEvent.getEventMsg();
        //显示当前城市
        if (!StringUtil.isEmpty(mCityName)) {
            tvCity.setText(mCityName);
        }
    }



    @Override
    public void isGetClassificationSuc(HomeDetailBean data) {

        //将商品分类存下来
        mHomeDetail = data;
        //清空table
        tablayout.removeAllTabs();
        for (GoodsClassificationBean datum : data.getCateList()) {
            //添加table
            tablayout.addTab(tablayout.newTab().setText(datum.getMobile_name()));
        }
        //初始化banner
        initBanner(data.getBannerList());


    }

    @Override
    public void isGetGoodsSuc(List<GoodsBean> data) {
        //todo 刷新商品列表信息
        closeRefresh(smartRefresh);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.tv_city, R.id.card_search, R.id.img_notice, R.id.ll_logistics,R.id.ll_shop_discount,
            R.id.ll_insurance, R.id.ll_old, R.id.ll_charitable,R.id.ll_jiusu,R.id.ll_education})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //切换城市
            case R.id.tv_city:
                startActivity(new Intent(mContext, CityPickerActivity.class));
                break;
            //搜索
            case R.id.card_search:
                startActivity(new Intent(mContext, SearchActivity.class).putExtra(SearchActivity.SEARCH_TYPE,SearchActivity.SEARCH_COMMODITY)
                        , ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
            //通知
            case R.id.img_notice:
                break;
            //物流
            case R.id.ll_logistics:
                startActivity(new Intent(mContext, LogisticsActivity.class));
                break;
            //保险
            case R.id.ll_insurance:
                startActivity(new Intent(mContext, InsuranceActivity.class));
                break;
            //养老
            case R.id.ll_old:
                startActivity(new Intent(mContext, PensionActivity.class));
                break;
            //公益
            case R.id.ll_charitable:
                startActivity(new Intent(mContext, CharitableActivity.class));
                break;
            //教育
            case R.id.ll_education:
                startActivity(new Intent(mContext, EducationActivity.class));
                break;
            //久速专区
            case R.id.ll_jiusu:
                break;
            //久速商家
            case R.id.ll_shop_discount:
                startActivity(new Intent(mContext, JiuSuShopActivity.class));
                break;
            //以物易物
            case R.id.ll_barter:
                break;
            //同城
            case R.id.ll_same_city:
                break;
        }
    }

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(this);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }

    @Override
    public void isGetGoodsFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
        //请求失败时页码归位
        mPage--;
        closeRefresh(smartRefresh);
    }
}
