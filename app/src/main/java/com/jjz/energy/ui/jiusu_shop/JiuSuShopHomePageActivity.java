package com.jjz.energy.ui.jiusu_shop;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.adapter.HomePageCommentAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.jiusu_shop.ShopHomePageBean;
import com.jjz.energy.presenter.jiusu_shop.JiuSuShopPresenter;
import com.jjz.energy.util.networkUtil.AesUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu_shop.IJiuSuShopView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 久速商家个人主页
 * @author: create by chenhao on 2019/11/9
 */
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
    private CommodityAdapter mCommodityAdapter;
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


    @Override
    protected JiuSuShopPresenter getPresenter() {
        return new JiuSuShopPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_jiusu_shop_homepage;
    }

    /**
     * 用户id
     */
    private int user_id;

    @Override
    protected void initView() {
        user_id = getIntent().getIntExtra(Constant.USER_ID,0);
        tvToolbarTitle.setText(getIntent().getStringExtra("title"));
        initRv();
    }

    /**
     * 初始化所有的列表和刷新控件
     */
    private void initRv() {
        //评价
        rvComentlist.setLayoutManager(new LinearLayoutManager(this));
        mCommentAdapter = new HomePageCommentAdapter(R.layout.item_homepage_comment,
                new ArrayList<>());
        rvComentlist.setAdapter(mCommentAdapter);
        //推荐商品
        rvCommodity.setLayoutManager(new LinearLayoutManager(this));
        mCommodityAdapter = new CommodityAdapter(R.layout.item_shop_commodity,
                new ArrayList<>());
        rvCommodity.setAdapter(mCommodityAdapter);
        //上拉加载
        smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            //获取指定类别的商品
            getData(true);
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
            getData(false);
        });
    }
    /**
     * 获取评价列表
     *
     * @param isLoadMore 是否加载更多
     */
    private void getData(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        HashMap<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        map.put(Constant.USER_ID, AesUtils.encrypt(String.valueOf(user_id), AesUtils.KEY, AesUtils.IV));
        if (filter_type==1){
            //查询很棒的评价
            map.put("start","3");
        }else if (filter_type==2){
            //查询有图的评价
            map.put("is_set_img","1");
        }else if (filter_type==3){
            //查询差评
            map.put("is_set_img","2");
        }
        mPresenter.getShopHomePageInfo(PacketUtil.getRequestPacket(map), isLoadMore);
    }

    @Override
    public void isGetShopHomePageSuccess(ShopHomePageBean data) {
        //展示数量
        rbCommentAll.setText("全部 "+ data.getTotal_num());
        rbCommentGood.setText("好评 "+ data.getGood_num());
        rbCommentBad.setText("差评 "+ data.getBad_num());
        rbCommentHavePhoto.setText("有图 "+ data.getHave_img_num());

        //显示推荐商品列表
        if (mCommodityAdapter.notifyChangeData(data.getCommodityList())){
            mCommentAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_data, "你还没有发布商品", false,null));
        }
        //加载更多
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mCommentAdapter.addNewData(data.getCommentList()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mCommentAdapter.notifyChangeData(data.getCommentList())) {
                mCommentAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "还没有对你的评论", false,null));
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


    @OnClick({R.id.ll_toolbar_left, R.id.img_call_phone, R.id.rl_location, R.id.tv_go_shop_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //打电话
            case R.id.img_call_phone:
                break;
                //查看地图，导航
            case R.id.rl_location:
                break;
                //到店支付
            case R.id.tv_go_shop_buy:
                break;
        }
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    /**
     * 推荐商品
     */
    class CommodityAdapter extends BaseRecycleNewAdapter<ShopHomePageBean.CommodityListBean>{


        public CommodityAdapter(int layoutResId, @Nullable List<ShopHomePageBean.CommodityListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ShopHomePageBean.CommodityListBean item) {


        }
    }
}
