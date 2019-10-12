package com.jjz.energy.ui.home.commodity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.adapter.CommentAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseApplication;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.commodity.GoodsDetailsBean;
import com.jjz.energy.presenter.home.CommodityDetailsPresenter;
import com.jjz.energy.ui.ImagePagerActivity;
import com.jjz.energy.ui.mine.MineLikeCommodityActivity;
import com.jjz.energy.ui.mine.information.HomePageActivity;
import com.jjz.energy.ui.mine.shop_order.SureBuyActivity;
import com.jjz.energy.ui.notice.IMActivity;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.home.ICommodityView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 商品详情
 * @author: create by chenhao on 2019/8/5
 */
public class CommodityDetailActivity extends BaseActivity <CommodityDetailsPresenter> implements ICommodityView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_commodity_new_money)
    TextView tvCommodityNewMoney;
    @BindView(R.id.tv_commodity_old_money)
    TextView tvCommodityOldMoney;
    @BindView(R.id.tv_commodity_freight)
    TextView tvCommodityFreight;
    @BindView(R.id.tv_talk)
    TextView tvTalk;
    @BindView(R.id.tv_commodity_title)
    TextView tvCommodityTitle;
    @BindView(R.id.tv_commodity_is_new)
    TextView tvCommodityIsNew;
    @BindView(R.id.rv_commodity_photo)
    RecyclerView rvCommodityPhoto;
    @BindView(R.id.tv_commodity_content)
    TextView tvCommodityContent;
    @BindView(R.id.tv_commodity_pageviews)
    TextView tvCommodityPageviews;
    @BindView(R.id.img_seller_head)
    ImageView imgSellerHead;
    @BindView(R.id.tv_seller_name)
    TextView tvSellerName;
    @BindView(R.id.tv_seller_online_time)
    TextView tvSellerOnlineTime;
    @BindView(R.id.tv_seller_commodity_sum)
    TextView tvSellerCommoditySum;
    @BindView(R.id.tv_seller_order_sum)
    TextView tvSellerOrderSum;
    @BindView(R.id.tv_fans_sum)
    TextView tvFansSum;
    @BindView(R.id.img_user_head)
    ImageView imgUserHead;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.tv_comment_send)
    TextView tvCommentSend;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_favorites)
    TextView tvFavorites;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.tv_commodity_num)
    TextView tvCommodityNum;
    @BindView(R.id.ll_buyer)
    LinearLayout llBuyer;
    /**
     * 商品ID
     */
    public static final String GOODS_ID = "goods_id";
    /**
     * 该商品的id
     */
    private int goods_id;
    /**
     * 商品详情的图片
     */
    private   CommdityPhotoAdapter mPhotoAdapter;

    /**
     * 卖家的用户信息
     */
    private GoodsDetailsBean.SellerInfoBean mSellerUserInfo ;

    @Override
    protected void initView() {
        goods_id = getIntent().getIntExtra(GOODS_ID, 0);
        //给原价加上中划线
        tvCommodityOldMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvCommodityOldMoney.getPaint().setAntiAlias(true); //去掉锯齿
        initRv();
        //获取商品详情
        mPresenter.getGoodsDetails(PacketUtil.getRequestPacket(Utils.stringToMap(GOODS_ID,
                goods_id + "")));
    }

    /**
     * 初始化列表
     */
    private void initRv() {
        rvComment.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        rvCommodityPhoto.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mPhotoAdapter = new CommdityPhotoAdapter(R.layout.item_commodity_photo, new ArrayList<>());
        rvCommodityPhoto.setAdapter(mPhotoAdapter);


        List<String> list = new ArrayList<>();
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565580470&di=6814f2590982e050de05badee7537513&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fc%2F547441f23ba1c.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564985827305&di=0c8f30df29dd9ecd82752e274ba13da7&imgtype=0&src=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150515%2F9448607_221818217002_2.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564985827226&di=bec368fe3a9a7d24eb693d5e45d82eed&imgtype=" +
                "0&src=http%3A%2F%2Fpic5.nipic.com%2F20091224%2F3822085_091707089473_2.jpg");
        CommentAdapter mCommentAdatper = new CommentAdapter(R.layout.item_comment,list);
        rvComment.setAdapter(mCommentAdatper);

    }

    @Override
    public void isGetGoodsDetailsSuc(GoodsDetailsBean data) {
        //当卖家查看自己的详情时，隐藏掉聊一聊和我想要
        if (data.getSeller_info().getUser_id()==data.getBuyer_info().getUser_id()){
            llBuyer.setVisibility(View.GONE);
            tvTalk.setVisibility(View.GONE);
        }
        //获取商品详情 和卖家信息成功  写入数据
        mSellerUserInfo = data.getSeller_info();
        //数量
        tvCommodityNum.setText("库存:"+data.getGoods_info().getStore_count()+"件");
        //现价
        tvCommodityNewMoney.setText("￥"+data.getGoods_info().getMarket_price());
        //原价
        tvCommodityOldMoney.setText("原价¥"+data.getGoods_info().getShop_price());
        //是否包邮。不包邮就显示运费
        tvCommodityFreight.setText(data.getGoods_info().getShopping_price()==0?"包邮":"运费"+data.getGoods_info().getShopping_price()+"元");
        //是否全新
        tvCommodityIsNew.setVisibility(data.getGoods_info().getIs_mnh()==1? View.VISIBLE : View.GONE);
        //标题  是否全新 如果是 标题前面要多加4个文字的距离
        tvCommodityTitle.setText("\u3000\u3000  "+data.getGoods_info().getGoods_name());
        //商品描述
        tvCommodityContent.setText(data.getGoods_info().getMobile_content());
        //多少人想要 + 浏览数量
        tvCommodityPageviews.setText(data.getGoods_info().getCollect_sum()+"人想要，"+data.getGoods_info().getClick_count()+"次浏览");
        //卖家昵称
        tvSellerName.setText(mSellerUserInfo.getNickname());
        tvToolbarTitle.setText(mSellerUserInfo.getNickname());
        //卖家在售商品数量
        tvSellerCommoditySum.setText(String.valueOf(mSellerUserInfo.getGoods_count()));
        //卖家累积交易
        tvSellerOrderSum.setText(String.valueOf(mSellerUserInfo.getOrder_count()));
        //卖家粉丝
        tvFansSum.setText(String.valueOf(mSellerUserInfo.getFans_count()));
        //卖家头像
        GlideUtils.loadCircleImage(mContext, mSellerUserInfo.getHead_pic(), imgSellerHead);
        //买家头像
        GlideUtils.loadCircleImage(mContext, data.getBuyer_info().getHead_pic(), imgUserHead);
        //刷新商品图片
        mPhotoAdapter.setNewData(Arrays.asList(data.getGoods_info().getGoods_images().split(",")));
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_talk, R.id.ll_seller_commodity_sum, R.id.ll_seller_fans,
            R.id.ll_seller_order_sum, R.id.img_seller_head, R.id.tv_comment_send, R.id.tv_like,
            R.id.tv_favorites, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //聊一聊
            case R.id.tv_talk:
                if (mSellerUserInfo!=null){

                    startActivity(new Intent(mContext, IMActivity.class).putExtra("userName",
                            mSellerUserInfo.getMobile()));
                } else {
                    showToast("获取聊天对象失败");
                }
                break;

                //卖家粉丝
            case R.id.ll_seller_fans:
                break;
            //进入卖家主页
            case R.id.ll_seller_commodity_sum:
            case R.id.img_seller_head:
            case R.id.ll_seller_order_sum:
                if (mSellerUserInfo == null) {
                    showToast("获取卖家信息失败");
                    return;
                }
                startActivity(new Intent(mContext, HomePageActivity.class).putExtra("user_id"
                        , mSellerUserInfo.getUser_id()));
                break;
                //发表评论
            case R.id.tv_comment_send:
                break;
                //想要
            case R.id.tv_like:
                putWant();
                break;
                //收藏夹
            case R.id.tv_favorites:
                startActivity(new Intent(mContext, MineLikeCommodityActivity.class));
                break;
                //立即购买
            case R.id.tv_buy:
                ActivityUtils.startActivity(SureBuyActivity.class);
                break;
        }
    }

    /**
     * 收藏商品
     */
    private void putWant() {

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
    protected CommodityDetailsPresenter getPresenter() {
        return new CommodityDetailsPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_commodity_detail;
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }

    //商品图片
    class CommdityPhotoAdapter extends BaseRecycleNewAdapter<String> {

        public CommdityPhotoAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imgPhoto = helper.getView(R.id.item_img_photo);
            GlideUtils.loadRoundCircleImage(mContext,item,imgPhoto);
            //查看大图
            imgPhoto.setOnClickListener(v -> {
                ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(v.getMeasuredWidth(),
                        v.getMeasuredHeight());
                ImagePagerActivity.startImagePagerActivity(BaseApplication.AppContext, mData, helper.getLayoutPosition(), imageSize);
            });
            }
    }

}
