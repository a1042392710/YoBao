package com.jjz.energy.ui.community;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
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
import com.jjz.energy.adapter.PhotoAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.community.CommunityBean;
import com.jjz.energy.entry.community.CommunityCommentBean;
import com.jjz.energy.presenter.community.CommunityPresenter;
import com.jjz.energy.ui.mine.information.HomePageActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.SoftKeyBoardListener;
import com.jjz.energy.view.home.ICommunityView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 帖子详情
 * @author: create by chenhao on 2019/8/26
 */
public class CommunityDetailActivity extends BaseActivity<CommunityPresenter> implements ICommunityView, OnLoadMoreListener {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_user_head)
    ImageView imgUserHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_put_time)
    TextView tvPutTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.img_one_photo)
    ImageView imgOnePhoto;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_put_comment)
    TextView tvPutComment;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.view_keybord)
    View view_keybord;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;

    /**
     * 图片
     */
    private PhotoAdapter mPhotoAdapter;
    /**
     * 页码
     */
    private int  mPage=1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;

    /**
     * 这条帖子的 id
     */
    private int id;

    /**
     * 评论的适配器
     */
    private CommentAdapter mCommentAdapter ;
    /**
     * 列表页面传过来的数据
     */
    private CommunityBean.ListBean mListBean;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("详情");
        id = getIntent().getIntExtra("id",0);
        mListBean = (CommunityBean.ListBean) getIntent().getSerializableExtra(Constant.INTENT_KEY_OBJECT);
        initRv();
        initData();
        initEditHight();
        //获取评论数据
        getCommentData(false);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (mListBean!=null){
            GlideUtils.loadHead( mContext,mListBean.getHead_pic(),imgUserHead);
            tvContent.setText(mListBean.getContent());
            tvLike.setText(mListBean.getTop_num());
            tvLike.setText(mListBean.getTop_num());
            tvUserName.setText(mListBean.getNickname());
            tvPutTime.setText(DateUtil.getTimeFormatText(new Date(mListBean.getAdd_time()*1000L)));
            if (!StringUtil.isEmpty(mListBean.getImages())){
                List<String> list = Arrays.asList(mListBean.getImages().split(","));
                //放入图片的数据
                mPhotoAdapter.notifyChangeData(list);
            }
            if (!StringUtil.isEmpty(mListBean.getMsg_num())&&0<Integer.valueOf(mListBean.getMsg_num())){
                tvCommentNum.setText("全部评论("+mListBean.getMsg_num()+")");
            }
        }
    }

    /**
     * 使键盘能够弹起
     */
    private void initEditHight() {
        //监听软键盘弹出，并获取软键盘高度
        SoftKeyBoardListener.setListener(this,
                new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {
                        LinearLayout.LayoutParams layoutParams =
                                (LinearLayout.LayoutParams) view_keybord.getLayoutParams();
                        layoutParams.height = height;
                        view_keybord.setLayoutParams(layoutParams);
                    }

                    @Override
                    public void keyBoardHide(int height) {
                        LinearLayout.LayoutParams layoutParams =
                                (LinearLayout.LayoutParams) view_keybord.getLayoutParams();
                        layoutParams.height = 0;
                        view_keybord.setLayoutParams(layoutParams);
                    }
                });
    }

    /**
     * 初始化列表
     */
    private void initRv() {
        smartRefresh.setOnLoadMoreListener(this);
        mPhotoAdapter =new PhotoAdapter(R.layout.item_photo,new ArrayList<>());
        rvPhoto.setLayoutManager(new GridLayoutManager(mContext,3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvPhoto.setAdapter(mPhotoAdapter);
        mCommentAdapter = new CommentAdapter(R.layout.item_community_comment,new ArrayList<>());
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(mCommentAdapter);
        //评论
        mCommentAdapter.setOnItemClickListener((adapter, view, position) -> showReplyView("",""));
        //滑动的时候，将软键盘隐藏
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (nestedScrollView, i, i1, i2, i3) -> {
                    disMissSoftKeyboard(); });
    }

    /**
     * 获取评论列表
     */
    private void getCommentData(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        HashMap<String,String> map = new HashMap<>();
        map.put("page",mPage+"");
        map.put("timeline_id",id+"");
        mPresenter.getPostComment(PacketUtil.getRequestPacket(map),isLoadMore);
    }

    @Override
    public void isGetPostCommentSuc(CommunityCommentBean data) {
        if (!StringUtil.isListEmpty(data.getList()) && data.getList().size()>8){
            smartRefresh.setEnableLoadMore(true);     //有够长的数据就开启加载更多
        }else{
            smartRefresh.setEnableLoadMore(false);     //否则关闭
        }
        //加载更多
        if (isLoadMore) {
            mCommentAdapter.addNewData(data.getList());
        } else {
            //刷新数据，无数据显示空页面
            if (!mCommentAdapter.notifyChangeData(data.getList())) {
                mCommentAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "还没有人评论", false ,null));
            }
        }
        closeRefresh(smartRefresh);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        getCommentData(true);
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
        etComment .requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(etComment, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isLoadMore){
            mPage--;
        }
        closeRefresh(smartRefresh);
        //还需要控制评论列表的数据
        showToast(msg);
    }

    @Override
    protected CommunityPresenter getPresenter() {
        return new CommunityPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_community_detail;
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    @OnClick({R.id.ll_toolbar_left, R.id.img_user_head, R.id.tv_put_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //个人主页
            case R.id.img_user_head:
                startActivity(new Intent(mContext, HomePageActivity.class));
                break;
            //发布评论
            case R.id.tv_put_comment:
                break;
        }
    }

    class CommentAdapter extends BaseRecycleNewAdapter<CommunityCommentBean.ListBean> {

        public CommentAdapter(int layoutResId, @Nullable List<CommunityCommentBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CommunityCommentBean.ListBean item) {
            ImageView img = helper.getView(R.id.item_img_head);
            helper.setText(R.id.item_tv_user_name,item.getFrom_username());
            //评价内容
            if (!StringUtil.isEmpty(item.getTo_username())){
                helper.setText(R.id.item_tv_comment,"@"+item.getTo_username()+" : "+item.getContent());
            }else{
                helper.setText(R.id.item_tv_comment,item.getContent());
            }
            //头像
            GlideUtils.loadHead(mContext,item.getFrom_pic(),img);
            //时间
            helper.setText(R.id.item_tv_time,DateUtil.getTimeFormatText(new Date(item.getReply_time()*1000L)));
            img.setOnClickListener(v -> {
                //todo 进用户主页/分个人和商家
            });
        }
    }

}
