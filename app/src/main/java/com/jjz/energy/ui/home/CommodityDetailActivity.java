package com.jjz.energy.ui.home;

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
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.ui.ImagePagerActivity;
import com.jjz.energy.ui.mine.MineLikeCommodityActivity;
import com.jjz.energy.ui.mine.shop_order.SureBuyActivity;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 商品详情
 * @author: create by chenhao on 2019/8/5
 */
public class CommodityDetailActivity extends BaseActivity {
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
    @BindView(R.id.tv_seller_response_rate)
    TextView tvSellerResponseRate;
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

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_commodity_detail;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("南昌彭于晏");
        initRv();
        //给原价加上中划线
        tvCommodityOldMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvCommodityOldMoney.getPaint().setAntiAlias(true); //去掉锯齿
    }

    /**
     * 初始化列表
     */
    private void initRv() {
        //卖家头像
        GlideUtils.loadCircleImage(mContext,"http://b-ssl.duitang.com/uploads/item/201410/20/20141020224133_Ur54c.jpeg",imgSellerHead);
        //买家头像
        GlideUtils.loadCircleImage(mContext,"http://cdn.duitang.com/uploads/item/201410/26/20141026191422_yEKyd.thumb.700_0.jpeg",imgUserHead);
        rvComment.setLayoutManager(new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        rvCommodityPhoto.setLayoutManager(new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        List<String> list = new ArrayList<>();
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565580470&di=6814f2590982e050de05badee7537513&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fc%2F547441f23ba1c.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564985827305&di=0c8f30df29dd9ecd82752e274ba13da7&imgtype=0&src=http%3A%2F%2Fpic67.nipic.com%2Ffile%2F20150515%2F9448607_221818217002_2.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564985827226&di=bec368fe3a9a7d24eb693d5e45d82eed&imgtype=" +
                "0&src=http%3A%2F%2Fpic5.nipic.com%2F20091224%2F3822085_091707089473_2.jpg");
        CommdityPhotoAdapter mPhotoAdapter = new CommdityPhotoAdapter(R.layout.item_commodity_photo,list);
        rvCommodityPhoto.setAdapter(mPhotoAdapter);
        CommentAdapter mCommentAdatper = new CommentAdapter(R.layout.item_comment,list);
        rvComment.setAdapter(mCommentAdatper);

    }


    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_talk, R.id.ll_seller_commodity_sum,
            R.id.ll_seller_order_sum, R.id.img_seller_head, R.id.tv_comment_send, R.id.tv_like,
            R.id.tv_favorites, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //聊一聊
            case R.id.tv_talk:
                break;
                //卖家在售商品
            case R.id.ll_seller_commodity_sum:
                break;
                //卖家交易订单
            case R.id.ll_seller_order_sum:
                break;
                //卖家头像
            case R.id.img_seller_head:
                startActivity(new Intent(mContext,HomePageActivity.class));
                break;
                //发表评论
            case R.id.tv_comment_send:
                break;
                //想要
            case R.id.tv_like:
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
