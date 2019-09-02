package com.jjz.energy.ui.home;

import android.content.Intent;
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
import com.jjz.energy.adapter.HomeCommodityTypeAdapter;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.entry.event.LocationEvent;
import com.jjz.energy.ui.charitable.CharitableActivity;
import com.jjz.energy.ui.city.CityPickerActivity;
import com.jjz.energy.ui.education.EducationActivity;
import com.jjz.energy.ui.insurance.InsuranceActivity;
import com.jjz.energy.ui.logistics.LogisticsActivity;
import com.jjz.energy.ui.pension.PensionActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideImageLoader;
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
public class HomeFragment extends BaseFragment {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_service_point_distance)
    TextView tvServicePointDistance;
    @BindView(R.id.tv_location_description)
    TextView tvLocationDescription;
    @BindView(R.id.ll_location_distance)
    LinearLayout llLocationDistance;
    @BindView(R.id.tv_business_hours)
    TextView tvBusinessHours;
    @BindView(R.id.ll_business_hours)
    LinearLayout llBusinessHours;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.rvType)
    RecyclerView rvType;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.card_search)
    CardView cardSearch;
    @BindView(R.id.img_notice)
    ImageView imgNotice;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
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



    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        //初始化banner
        List<String> beans = new ArrayList<>();
        beans.add("http://pic1.win4000.com/wallpaper/0/559b8b91b3afa.jpg");
        beans.add("http://img.daimg.com/uploads/allimg/130707/3-130FH30508.jpg");
        beans.add("http://img.daimg.com/uploads/allimg/120716/3-120G6150G2.jpg");
        initBanner(beans);
        //初始化Yo专区
        initYoBao();
        //初始化Tablayou和商品分类列表
        initRv();
    }

    /**
     * 初始化banner
     */
    private void initBanner(List<String> homeBeans) {
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(homeBeans);
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
     * 初始化商品分类列表
     */
    private void initRv() {
        //给Tablayout和rv赋值
        tablayout.addTab(tablayout.newTab().setText("手机"));
        tablayout.addTab(tablayout.newTab().setText("图书"));
        tablayout.addTab(tablayout.newTab().setText("二手家电"));
        tablayout.addTab(tablayout.newTab().setText("汽车用品"));
        tablayout.addTab(tablayout.newTab().setText("服饰箱包"));
        tablayout.addTab(tablayout.newTab().setText("数码3C"));

        rvType.setLayoutManager(new GridLayoutManager(mContext, 2));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        HomeCommodityTypeAdapter commodityTypeAdapter =
                new HomeCommodityTypeAdapter(R.layout.item_commodity_grid, list);
        rvType.setAdapter(commodityTypeAdapter);
        commodityTypeAdapter.setOnItemClickListener((adapter, view, position) -> startActivity(new Intent(mContext,CommodityDetailActivity.class)));
    }

    /**
     * 初始化Yo专区
     */
    private void initYoBao() {

    }





    @OnClick({R.id.tv_city, R.id.card_search, R.id.img_notice,R.id.ll_logistics, R.id.ll_insurance, R.id.ll_old, R.id.ll_charitable,
            R.id.ll_education})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //切换城市
            case R.id.tv_city:
                startActivity(new Intent(mContext, CityPickerActivity.class));
                break;
            //搜索
            case R.id.card_search:
                startActivity(new Intent(mContext, SearchActivity.class));
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
        }
    }

    /**
     * 当前城市
     */
    private String mCityName ;
    //接受定位消息，显示城市名称
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  setCity(LocationEvent locationEvent){
        mCityName = locationEvent.getEventMsg();
        //显示当前城市
        if (!StringUtil.isEmpty(mCityName)){
            tvCity.setText(mCityName);
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
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
}
