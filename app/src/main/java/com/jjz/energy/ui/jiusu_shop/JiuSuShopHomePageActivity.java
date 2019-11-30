package com.jjz.energy.ui.jiusu_shop;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.HomePageCommentAdapter;
import com.jjz.energy.adapter.ShopHomePageCommodityAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.commodity.HomePageCommentBean;
import com.jjz.energy.entry.jiusu_shop.ShopHomePageBean;
import com.jjz.energy.presenter.jiusu_shop.JiuSuShopPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideImageLoader;
import com.jjz.energy.util.networkUtil.AesUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.jiusu_shop.IJiuSuShopView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Features: 久速商家个人主页
 * @author: create by chenhao on 2019/11/9
 */
@RuntimePermissions
public class JiuSuShopHomePageActivity extends BaseActivity <JiuSuShopPresenter> implements IJiuSuShopView {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.img_location)
    ImageView imgLocation;
    @BindView(R.id.tv_location_content)
    TextView tvLocationContent;
    @BindView(R.id.tv_location_content_desc)
    TextView tvLocationContentDesc;
    @BindView(R.id.img_call_phone)
    ImageView imgCallPhone;
    @BindView(R.id.rl_location)
    RelativeLayout rlLocation;
    @BindView(R.id.view_one)
    View viewOne;
    @BindView(R.id.tv_go_shop_buy)
    TextView tvGoShopBuy;
    @BindView(R.id.ll_go_shop_buy)
    LinearLayout llGoShopBuy;
    @BindView(R.id.view_two)
    View viewTwo;
    @BindView(R.id.tv_all_commodity)
    TextView tvAllCommodity;
    @BindView(R.id.ll_title_two)
    LinearLayout llTitleTwo;
    @BindView(R.id.rv_commodity)
    RecyclerView rvCommodity;
    @BindView(R.id.view_three)
    View viewThree;
    @BindView(R.id.tv_test_one)
    TextView tvTestOne;
    @BindView(R.id.rb_comment_all)
    RadioButton rbCommentAll;
    @BindView(R.id.rb_comment_good)
    RadioButton rbCommentGood;
    @BindView(R.id.rb_comment_bad)
    RadioButton rbCommentBad;
    @BindView(R.id.rb_comment_have_photo)
    RadioButton rbCommentHavePhoto;
    @BindView(R.id.rg_comment)
    RadioGroup rgComment;
    @BindView(R.id.rv_comentlist)
    RecyclerView rvComentlist;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    private HomePageCommentAdapter mCommentAdapter;
    private ShopHomePageCommodityAdapter mCommodityAdapter;
    /**
     * 页码
     */
    private int mPage = 1;

    /*
     * 是否加载更多
     */
    private boolean isLoadMore;


    /**
     * 筛选类型 0 全部  1 很棒    2 有图  3差
     */
    private int filter_type = 0;
    /**
     * 商家id
     */
    private int shop_id;

    @Override
    protected void initView() {
        shop_id = getIntent().getIntExtra(Constant.SHOP_ID,0);
        tvToolbarTitle.setText("商家主页");
        initRv();
        //获取商家基础信息
        mPresenter.getShopHomePageInfo(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.SHOP_ID,AesUtils.encrypt(String.valueOf(shop_id), AesUtils.KEY, AesUtils.IV))));
        //获取商家评价
        getCommentData(false);
    }

    /**
     * 初始化所有的列表和刷新控件
     */
    private void initRv() {
        //评价
        rvComentlist.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mCommentAdapter = new HomePageCommentAdapter(R.layout.item_homepage_comment,
                new ArrayList<>());
        rvComentlist.setAdapter(mCommentAdapter);
        //推荐商品
        rvCommodity.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mCommodityAdapter = new ShopHomePageCommodityAdapter(R.layout.item_shop_commodity,
                new ArrayList<>());
        rvCommodity.setAdapter(mCommodityAdapter);
        //上拉加载
        smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            //获取评价
            getCommentData(true);
        });
        //选中的时候
        rgComment.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId==R.id.rb_comment_all){
                filter_type=0;
            }else if (checkedId==R.id.rb_comment_good){
                filter_type=1;
            }else if (checkedId==R.id.rb_comment_have_photo){
                filter_type=2;
            }else if (checkedId==R.id.rb_comment_bad){
                filter_type=3;
            }
            mPage=1;
            getCommentData(false);
        });
    }
    /**
     * 获取评价列表
     *
     * @param isLoadMore 是否加载更多
     */
    private void getCommentData(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        HashMap<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        map.put("shop_id", AesUtils.encrypt(String.valueOf(shop_id), AesUtils.KEY, AesUtils.IV));
        if (filter_type==1){
            //查询很棒的评价
            map.put("start","3");
        }else if (filter_type==2){
            //查询有图的评价
            map.put("is_set_img","1");
        }else if (filter_type==3){
            //查询差评
            map.put("start","1");
        }
        mPresenter.getShopCommentList(PacketUtil.getRequestPacket(map), isLoadMore);
    }

    /**
     * 将数据存下来
     */
    private ShopHomePageBean mShopHomePageBean;

    @Override
    public void isGetShopHomePageSuccess(ShopHomePageBean data) {
        mShopHomePageBean = data;
        //轮播图
        List<String> list = Arrays.asList(data.getHeader_img().split(","));
        initBanner(list);
        //展示商家信息
        tvShopName.setText(data.getShop_name());
        tvDesc.setText(data.getShop_desc());
        tvLocationContent.setText(data.getPoiaddress());
        tvLocationContentDesc.setText(data.getPoiname());
        tvGoShopBuy.setText("扫描店内二维码支付享受积分抵扣"+(data.getRebate()*10)+"折优惠");
        tvDiscount.setText((data.getRebate()*10)+"折起 | 人均："+data.getAvg_tax()+"元");
        //显示推荐商品列表
        if (mCommodityAdapter.notifyChangeData(data.getCommodityList())){
            mCommodityAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_data, "商家还没有发布商品", false,null));
        }
    }

    /**
     * 初始化banner
     */
    private void initBanner(List<String> homeBeans) {
        if (StringUtil.isListEmpty(homeBeans)){
            return;
        }
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
    @Override
    public void isGetShopHomePageCommentSuccess(HomePageCommentBean data) {
        //展示数量
        rbCommentAll.setText("全部 "+ data.getTotal_num());
        rbCommentGood.setText("好评 "+ data.getGood_num());
        rbCommentBad.setText("差评 "+ data.getBad_num());
        rbCommentHavePhoto.setText("有图 "+ data.getHave_img_num());
        //加载更多
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mCommentAdapter.addNewData(data.getList()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mCommentAdapter.notifyChangeData(data.getList())) {
                smartRefresh.setEnableLoadMore(false);
            } else {
                //有数据就开启加载更多
                if (mCommentAdapter.getData().size() > 8) {
                    smartRefresh.setEnableLoadMore(true);
                }
            }
        }
        closeRefresh(smartRefresh);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        //请求失败时页码归位
        if (isLoadMore) {
            mPage--;
        }
        closeRefresh(smartRefresh);
        showToast(msg);
    }


    @OnClick({R.id.ll_toolbar_left, R.id.img_call_phone, R.id.rl_location, R.id.tv_go_shop_buy, R.id.tv_all_commodity ,R.id.tv_go_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //打电话
            case R.id.img_call_phone:
                JiuSuShopHomePageActivityPermissionsDispatcher.callWithCheck(this);
                break;
                //查看地图，导航
            case R.id.rl_location:
                break;
                //到店支付
            case R.id.tv_go_pay:
                if (mShopHomePageBean==null){
                    return;
                }
                startActivity(new Intent(mContext,ShopSureBuyActivity.class).putExtra(Constant.INTENT_KEY_OBJECT,mShopHomePageBean));
                break;
                //全部商品
            case R.id.tv_all_commodity:
                if (mShopHomePageBean==null){
                    return;
                }
                startActivity(new Intent(mContext,JiuSuShopAllGoodsActivity.class).putExtra(Constant.SHOP_ID,mShopHomePageBean.getId()));
                break;
        }
    }
    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void call() {
        if (mShopHomePageBean!=null){
            String phone = mShopHomePageBean.getShop_phone();
            // 拨号：激活系统的拨号组件
            Intent intent = new Intent(); // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            Uri data = Uri.parse("tel:" + phone); // 设置数据
            intent.setData(data);
            startActivity(intent); // 激活Activity组件aodian
        }
    }
    //给用户解释为什么要申请权限
    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showCallPhone(final PermissionRequest request) {
        //唤起打电话权限
        PopWindowUtil.getInstance().showPopupWindow(mContext, "没有电话权限可不能打电话哦", () -> {
            request.proceed();//继续执行请求
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        JiuSuShopHomePageActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
    protected JiuSuShopPresenter getPresenter() {
        return new JiuSuShopPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_jiusu_shop_homepage;
    }

}
