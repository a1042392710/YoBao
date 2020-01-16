package com.jjz.energy.ui.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.jjz.energy.R;
import com.jjz.energy.adapter.HomeCommondityPagerAdapter;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.entry.event.LocationEvent;
import com.jjz.energy.entry.home.HomeDetailBean;
import com.jjz.energy.presenter.home.HomePresenter;
import com.jjz.energy.ui.MainActivity;
import com.jjz.energy.ui.city.CityPickerActivity;
import com.jjz.energy.ui.home.commodity.HomeCommodityFragment;
import com.jjz.energy.ui.home.entrust.EntrustListActivity;
import com.jjz.energy.ui.home.jiusu.JiuSuHomeActivity;
import com.jjz.energy.ui.home.login.LoginActivity;
import com.jjz.energy.ui.home.logistics.LogisticsActivity;
import com.jjz.energy.ui.jiusu_shop.JiuSuShopActivity;
import com.jjz.energy.ui.notice.NoticeListActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideImageLoader;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.view.home.IHomeView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

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
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    /**
     * 商品分类列表
     */
    private HomeDetailBean mHomeDetail;
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        initListener();
        //获取商品分类
        mPresenter.getSortAndBanner(PacketUtil.getRequestPacket(null));
    }

    /**
     * 初始化banner
     */
    private void initBanner(List<com.jjz.energy.entry.home.Banner> homeBeans) {
        //处理好数据放入banner中
        List<String> list = new ArrayList<>();
        for (com.jjz.energy.entry.home.Banner homeBean : homeBeans) {
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
     * 处理各种监听
     */
    private void initListener() {
        //上拉加载 下拉刷新
        smartRefresh.setOnRefreshListener(refreshLayout -> {
            //刷新列表数据
            mPresenter.getSortAndBanner(PacketUtil.getRequestPacket(null));
            //重新定位
            ((MainActivity)getActivity()).requestLocation();
        });

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

    private Badge mNoticeUnRead;
    @Override
    public void isGetClassificationSuc(HomeDetailBean data) {
        //首页小红点
        if (mNoticeUnRead != null) {
            mNoticeUnRead.setBadgeNumber(data.getUnread_num());
        } else {
            mNoticeUnRead = new QBadgeView(mContext).bindTarget(imgNotice)
                    .setBadgeGravity(Gravity.TOP | Gravity.END).setBadgeTextSize(8, true)
                    .setBadgeNumber(data.getUnread_num()).setGravityOffset(2, 0, true);
        }
        if (mHomeDetail == null) {
            //将商品分类存下来
            mHomeDetail = data;
            //初始化Tablayout 和viewpager
            viewPager.setAdapter(new HomeCommondityPagerAdapter(getFragmentManager(),data.getCateList()));
            viewPager.setOffscreenPageLimit(3);
            tablayout.setupWithViewPager(viewPager);
            //初始化banner
            initBanner(data.getBannerList());
        }else{
            //刷新当前页面的那个商品列表
          FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) viewPager.getAdapter();
          HomeCommodityFragment fragment = (HomeCommodityFragment)adapter.instantiateItem(viewPager,tablayout.getSelectedTabPosition());
          fragment.refresh();
        }
        closeRefresh(smartRefresh);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.tv_city, R.id.card_search, R.id.img_notice, R.id.ll_logistics,R.id.ll_shop_discount,
            R.id.ll_insurance, R.id.ll_old, R.id.ll_charitable,R.id.ll_jiusu,R.id.ll_education ,R.id.ll_commission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //切换城市
            case R.id.tv_city:
                startActivity(new Intent(mContext, CityPickerActivity.class));
                break;
            //搜索
            case R.id.card_search:
                //需要验证登录
                if (!UserLoginBiz.getInstance(mContext).detectUserLoginStatus()){
                    startActivity(new Intent(mContext,LoginActivity.class));
                }else {
                    startActivity(new Intent(mContext, SearchActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }
                break;
            //通知
            case R.id.img_notice:
                if (mNoticeUnRead!=null) {
                    mNoticeUnRead.setBadgeNumber(0);
                }
                //需要验证登录
                ActivityUtils.startActivity(!UserLoginBiz.getInstance(mContext).detectUserLoginStatus() ? LoginActivity.class : NoticeListActivity.class);
                break;
            //委托专区
            case R.id.ll_commission:
                //需要验证登录
                ActivityUtils.startActivity(!UserLoginBiz.getInstance(mContext).detectUserLoginStatus() ? LoginActivity.class : EntrustListActivity.class);
                break;
            //物流
            case R.id.ll_logistics:
//                showToast("暂未开放");
                startActivity(new Intent(mContext, LogisticsActivity.class));
                break;
            //保险
            case R.id.ll_insurance:
                showToast("暂未开放");
//                startActivity(new Intent(mContext, InsuranceActivity.class));
                break;
            //养老
            case R.id.ll_old:
                showToast("暂未开放");
//                startActivity(new Intent(mContext, PensionActivity.class));
                break;
            //公益
            case R.id.ll_charitable:
                showToast("暂未开放");
//                startActivity(new Intent(mContext, CharitableActivity.class));
                break;
            //教育
            case R.id.ll_education:
                showToast("暂未开放");
//                startActivity(new Intent(mContext, EducationActivity.class));
                break;
            //久速专区
            case R.id.ll_jiusu:
                //需要验证登录
                ActivityUtils.startActivity(!UserLoginBiz.getInstance(mContext).detectUserLoginStatus() ? LoginActivity.class : JiuSuHomeActivity.class);
                break;
            //久速商家
            case R.id.ll_shop_discount:
                //需要验证登录
                if (!UserLoginBiz.getInstance(mContext).detectUserLoginStatus()){
                    startActivity(new Intent(mContext,LoginActivity.class));
                }else {
                    startActivity(new Intent(mContext, JiuSuShopActivity.class));
                }
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
        closeRefresh(smartRefresh);
    }
}
