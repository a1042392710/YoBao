package com.jjz.energy.ui.home.commodity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.adapter.CommentExpandAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseApplication;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.CommentBean;
import com.jjz.energy.entry.commodity.GoodsDetailsBean;
import com.jjz.energy.presenter.home.CommodityDetailsPresenter;
import com.jjz.energy.ui.ImagePagerActivity;
import com.jjz.energy.ui.mine.MineLikeCommodityActivity;
import com.jjz.energy.ui.mine.information.HomePageActivity;
import com.jjz.energy.ui.mine.shop_order.SureBuyActivity;
import com.jjz.energy.ui.notice.IMActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.SoftKeyBoardListener;
import com.jjz.energy.view.home.ICommodityView;
import com.jjz.energy.widgets.CustomExpandableListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jjz.energy.base.Constant.GOODS_ID;

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
    @BindView(R.id.expandable_comment)
    CustomExpandableListView expandableListView;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_favorites)
    TextView tvFavorites;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.ll_buyer)
    LinearLayout llBuyer;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.ll_reply)
    LinearLayout llReply;
    @BindView(R.id.img_reply_head)
    ImageView imgReplyHead;
    @BindView(R.id.et_reply_content)
    EditText etReplyContent;
    @BindView(R.id.tv_reply_send)
    TextView tvReplySend;
    @BindView(R.id.tv_commodity_location)
    TextView tvCommodityLocation;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    /**
     * 该商品的id
     */
    private int goods_id;
    /**
     * 商品详情的图片
     */
    private   CommdityPhotoAdapter mPhotoAdapter;
    /**
     * 商品详情的数据
     */
    private GoodsDetailsBean mGoodsInfo ;
    /**
     * 评论的适配器
     */
    private CommentExpandAdapter mCommentAdapter ;

    /**
     * 页码
     */
    private int  mPage=1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;

    @Override
    protected void initView() {
        goods_id = getIntent().getIntExtra(GOODS_ID, 0);
        //给原价加上中划线
        tvCommodityOldMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvCommodityOldMoney.getPaint().setAntiAlias(true); //去掉锯齿
        initRv();
        initEdit();
        //获取商品详情
        mPresenter.getGoodsDetails(PacketUtil.getRequestPacket(Utils.stringToMap(GOODS_ID,
                goods_id + "")));
        //获取商品留言
        getCommentData(false);
    }

    /**
     * 发表评论
     */
    private void sendComment(Map<String,String> hashMap,String content) {
        hashMap.put("content",content);
        mPresenter.putComment(PacketUtil.getRequestPacket(hashMap));
    }

    /**
     * 收藏商品
     */
    private void putWant() {
        if (mGoodsInfo==null) {
            showToast("数据获取失败");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(Constant.GOODS_ID, mGoodsInfo.getGoods_info().getGoods_id() + "");
        //   act :  collect/cance 收藏   取消收藏
        map.put("act", isCollect == 0 ? "collect" : "cancel");
        mPresenter.putCollect(PacketUtil.getRequestPacket(map));
    }

    /**
     * 获取商品留言
     */
    private void getCommentData(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(GOODS_ID, goods_id + " ");
        hashMap.put("page", mPage+"");
        mPresenter.getGoodsComment(PacketUtil.getRequestPacket(hashMap),isLoadMore);
    }

    /**
     * 初始化列表
     */
    private void initRv() {
        rvCommodityPhoto.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mPhotoAdapter = new CommdityPhotoAdapter(R.layout.item_commodity_photo, new ArrayList<>());
        rvCommodityPhoto.setAdapter(mPhotoAdapter);

        //评论数据初始化并展开所有回复
        expandableListView.setGroupIndicator(null);//隐藏标识箭头
        mCommentAdapter = new CommentExpandAdapter(mContext,new ArrayList<>());
        expandableListView.setAdapter(mCommentAdapter);
        //父容器的点击事件
        expandableListView.setOnGroupClickListener(
                (expandableListView, view, groupPosition, l) -> {
                    //点击回复留言  第一层
                    showReplyView("comment_id",String.valueOf(mCommentAdapter.getCommentBeanList()
                            .get(groupPosition).getComment_id()));
                    return true;
                });
        //子项的点击事件
        expandableListView.setOnChildClickListener(
                (expandableListView, view, groupPosition, childPosition, l) -> {
                    //点击留言回复 第二层
                    showReplyView("reply_id",String.valueOf(mCommentAdapter.getCommentBeanList()
                            .get(groupPosition).getReply_list().get(childPosition).getReply_id()));
                    return false;
                });
        //加载更多
        smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            getCommentData(true);
        });
        //滑动的时候，将软键盘隐藏
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (nestedScrollView, i, i1, i2, i3) -> {
                    disMissSoftKeyboard();
                });
    }

    //获取评论列表成功
    @Override
    public void isGetCommentSuc(CommentBean data) {
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mCommentAdapter.addChangeData(data.getList()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            //刷新数据
            if (mCommentAdapter.notifyChangeData(data.getList())){
                //有数据就开启加载更多
                smartRefresh.setEnableLoadMore(true);
            }
        }
        //全部展开
        for(int i = 0; i < mCommentAdapter.getCommentBeanList().size(); i++) {
            expandableListView.expandGroup(i);
        }
        closeRefresh(smartRefresh);
    }

    //提交留言成功
    @Override
    public void isPutCommentSuc(String data) {
        etComment.setText("");
        etReplyContent.setText("");
        //发布评论成功
        mPage=1;
        getCommentData(false);
    }

    /**
     * 是否收藏该商品 1 收藏 0 未收藏
     */
    private int isCollect  = 0;
    //收藏或取消收藏成功
    @Override
    public void isPutCollectSuc(String data) {
        //操作成功后 保存相应的值  收藏成0功就为1  取消成功就为0
        isCollect = isCollect==1?0:1;
        //设置相应的图片
        Drawable drawable= getResources().getDrawable(R.mipmap.ic_checked_like);
        Drawable drawable2= getResources().getDrawable(R.mipmap.ic_unchecked_like);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        drawable2.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvLike.setCompoundDrawables(isCollect == 1 ? drawable:drawable2,null,null, null);
    }

    //获取商品详情成功
    @SuppressLint("SetTextI18n")
    @Override
    public void isGetGoodsDetailsSuc(GoodsDetailsBean data) {
        //当卖家查看自己的详情时，隐藏掉聊一聊和我想要
        if (data.getSeller_info().getUser_id()==data.getBuyer_info().getUser_id()){
            llBuyer.setVisibility(View.GONE);
            tvTalk.setVisibility(View.GONE);
        }
        //商品所有信息存下来  写入数据
        mGoodsInfo = data;
        //发布于哪里
        tvCommodityLocation.setText("发布于"+data.getGoods_info().getCity());
        //现价
        tvCommodityNewMoney.setText(data.getGoods_info().getShop_price());
        //原价
        tvCommodityOldMoney.setText("原价¥"+data.getGoods_info().getMarket_price());
        //是否包邮。不包邮就显示运费
        tvCommodityFreight.setText(data.getGoods_info().getShopping_price()==0?"包邮":"运费"+data.getGoods_info().getShopping_price()+"元");
        //是否全新
        tvCommodityIsNew.setVisibility(data.getGoods_info().getIs_mnh() == 1 ? View.VISIBLE :
                View.GONE);
        //标题  是否全新 如果是 标题前面要多加4个文字的距离
        tvCommodityTitle.setText("\u3000\u3000  " + data.getGoods_info().getGoods_name());
        //刷新商品图片
        mPhotoAdapter.setNewData(Arrays.asList(data.getGoods_info().getGoods_images().split(",")));
        //商品描述
        tvCommodityContent.setText(data.getGoods_info().getMobile_content());
        //多少人想要 + 浏览数量 + 库存
        tvCommodityPageviews.setText(data.getGoods_info().getCollect_sum()+"人想要 | "
                +data.getGoods_info().getClick_count()+"次浏览 | "
                +"库存:"+data.getGoods_info().getStore_count()+"件");
        //卖家昵称
        tvSellerName.setText(data.getSeller_info().getNickname());
        tvToolbarTitle.setText(data.getSeller_info().getNickname());
        //卖家在售商品数量
        tvSellerCommoditySum.setText(String.valueOf(data.getSeller_info().getGoods_count()));
        //卖家累积交易
        tvSellerOrderSum.setText(String.valueOf(data.getSeller_info().getOrder_count()));
        //卖家粉丝
        tvFansSum.setText(String.valueOf(data.getSeller_info().getFans_count()));
        //卖家头像
        GlideUtils.loadCircleImage(mContext, data.getSeller_info().getHead_pic(), imgSellerHead);
        //买家头像
        GlideUtils.loadCircleImage(mContext, data.getBuyer_info().getHead_pic(), imgUserHead);
        //买家头像
        GlideUtils.loadCircleImage(mContext, data.getBuyer_info().getHead_pic(), imgReplyHead);
        isCollect = data.getGoods_info().getIs_collect();
       //是否收藏
        Drawable drawable= getResources().getDrawable(R.mipmap.ic_checked_like);
        Drawable drawable2= getResources().getDrawable(R.mipmap.ic_unchecked_like);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        tvLike.setCompoundDrawables(isCollect == 1 ? drawable:drawable2,null,null, null);
    }


    /**
     * 提交的键值对
     */
    private String reply_key , reply_value;

    /**
     * 展示回复的View  Type 1 comment_id  2 reply_id
     */
    private void  showReplyView(String reply_key ,String reply_value ){
        this.reply_key = reply_key ;
        this.reply_value = reply_value ;
        //显示输入框和软键盘
        llReply.setVisibility(View.VISIBLE);
        showInput(etReplyContent);
    }

    /**
     * 显示键盘
     */
    public void showInput (EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }


    @OnClick({R.id.ll_toolbar_left, R.id.tv_talk, R.id.ll_seller_commodity_sum, R.id.ll_seller_fans,
            R.id.ll_seller_order_sum, R.id.img_seller_head, R.id.tv_comment_send, R.id.tv_like,R.id.tv_reply_send,
            R.id.tv_favorites, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //聊一聊
            case R.id.tv_talk:
                if (mGoodsInfo!=null){
                    startActivity(new Intent(mContext, IMActivity.class).putExtra("userName",
                            mGoodsInfo.getSeller_info().getMobile()));
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
                if (mGoodsInfo == null) {
                    showToast("获取卖家信息失败");
                    return;
                }
                startActivity(new Intent(mContext, HomePageActivity.class).putExtra("user_id"
                        , mGoodsInfo.getSeller_info().getUser_id()));
                break;
                //发表评论
            case R.id.tv_comment_send:
                if (StringUtil.isEmpty(etComment.getText().toString())){
                    return;
                }
                sendComment(Utils.stringToMap(Constant.GOODS_ID,mGoodsInfo.getGoods_info().getGoods_id()+""),etComment.getText().toString());
                break;
            //发送回复
            case R.id.tv_reply_send:
                if (StringUtil.isEmpty(etReplyContent.getText().toString())){
                    return;
                }
                sendComment(Utils.stringToMap(reply_key, reply_value),
                        etReplyContent.getText().toString());
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
                startActivity(new Intent(mContext, SureBuyActivity.class)
                        .putExtra(GOODS_ID, mGoodsInfo != null ? mGoodsInfo.getGoods_info().getGoods_id() : -1));
                break;

        }
    }

    /**
     * 监听键盘
     */
    private void initEdit() {
        //软键盘缩回时，如果 回复框 还在的话就隐藏
        SoftKeyBoardListener.setListener(this,
                new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {

                    }

                    @Override
                    public void keyBoardHide(int height) {
                        if (llReply.getVisibility() == View.VISIBLE) {
                            llReply.setVisibility(View.GONE);
                            etReplyContent.setText("");
                        }
                    }
                });
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
        if (isLoadMore){
            mPage--;
        }
        closeRefresh(smartRefresh);
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
