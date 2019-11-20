package com.jjz.energy.ui.community;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.entry.community.Community;
import com.jjz.energy.entry.community.CommunityCommentBean;
import com.jjz.energy.presenter.community.CommunityPresenter;
import com.jjz.energy.ui.mine.information.HomePageActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
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
    @BindView(R.id.ll_comment)
    LinearLayout llComment;

    /**
     * 页码
     */
    private int  mPage=1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;
    /**
     * 评论的适配器
     */
    private CommentAdapter mCommentAdapter ;
    /**
     * 列表页面传过来的数据
     */
    private Community mListBean;
    /**
     * 评论数量
     */
    private int comment_num;
    /**
     * 提交回复的对象
     */
    private String  reply_id;
    /**
     * 选中的那一条评论的下标
     */
    private int selectIndex = -1;
    /**
     * 当没有数据的时候 ，通过 id 查询数据
     */
    private int id;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("详情");
        id = getIntent().getIntExtra("id", 0);
        mListBean = (Community) getIntent().getSerializableExtra(Constant.INTENT_KEY_OBJECT);
        if (mListBean == null) {
            mPresenter.getPostDetails(PacketUtil.getRequestPacket(Utils.stringToMap("timeline_id", id + "")));
        } else {
            initData();
        }
        initRv();
        initListener();
        //获取评论数据
        getCommentData(false);
    }

    /**
     * 初始化数据
     */
    private void initData() {
            //头像
            GlideUtils.loadHead( mContext,mListBean.getHead_pic(),imgUserHead);
            //帖子内容
            tvContent.setText(mListBean.getContent());
            //点赞数量和是否点赞
            tvLike.setText(mListBean.getTop_num()+"");
            if (mListBean.getIs_like()==0){
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_unchecked_like_two);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvLike.setCompoundDrawables(null, null, drawable, null);
            }else{
                //点赞了
                Drawable drawable2 = mContext.getResources().getDrawable(R.mipmap.ic_checked_like_two);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                tvLike.setCompoundDrawables(null, null, drawable2, null);
            }
            //用户名
            tvUserName.setText(mListBean.getNickname());
            //发布时间
            tvPutTime.setText(DateUtil.getTimeFormatText(new Date(mListBean.getAdd_time()*1000L)));
            //是否有图片
            if (!StringUtil.isEmpty(mListBean.getImages())){
                List<String> list = Arrays.asList(mListBean.getImages().split(","));
                rvPhoto.setAdapter(new PhotoAdapter(R.layout.item_photo_two,list));
            }
    }

    /**
     * 初始化图片和评论列表
     */
    private void initRv() {
        smartRefresh.setOnLoadMoreListener(this);
        mCommentAdapter = new CommentAdapter(R.layout.item_community_comment, new ArrayList<>());
        rvComment.setLayoutManager(new LinearLayoutManager(this));
        rvComment.setAdapter(mCommentAdapter);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        //滑动的时候，将软键盘隐藏
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (nestedScrollView, i, i1, i2, i3) -> {
                    disMissSoftKeyboard(); });

        //点击子项 弹出回复窗口
         mCommentAdapter.setOnItemClickListener((adapter, view, position) -> {
            //记录选中的评论index
            selectIndex = position;
            showReplyView(1,mCommentAdapter.getItem(position).getReply_id() + "",
                    mCommentAdapter.getItem(position).getFrom_username());
        });

        //取消图片的滑动
        rvPhoto.setLayoutManager(new GridLayoutManager(mContext,3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        //监听软键盘的 收回和弹出事件
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
                        llComment .setVisibility(View.GONE);
                        //回到初始状态
                        etComment.setHint("说点什么吧");
                        reply_id="";
                        selectIndex=-1;
                        LinearLayout.LayoutParams layoutParams =
                                (LinearLayout.LayoutParams) view_keybord.getLayoutParams();
                        layoutParams.height = 0;
                        view_keybord.setLayoutParams(layoutParams);
                    }
                });
    }


    /**
     * 展示回复的View  0 评论  1 回复
     */
    private void  showReplyView(int commentType ,String reply_id,String toName){
        this.reply_id = reply_id ;
        //显示输入框和软键盘
        llComment.setVisibility(View.VISIBLE);
        etComment .requestFocus();
        if (commentType==0){
            etComment.setHint("说点什么吧");
        }else{
            etComment.setHint("回复@"+toName+"");
        }
        //展示软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(etComment, InputMethodManager.SHOW_IMPLICIT);
    }


    @Override
    public void isGetPostDetailsSuc(Community data) {
        mListBean = data;
        initData();
    }

    /**
     * 获取评论列表
     */
    private void getCommentData(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        HashMap<String,String> map = new HashMap<>();
        map.put("page",mPage+"");
        int timeline_id  = mListBean!=null?mListBean.getId():id;
        map.put("timeline_id",timeline_id+"");
        mPresenter.getPostComment(PacketUtil.getRequestPacket(map),isLoadMore);
    }

    /**
     * 获取评论列表成功
     */
    @Override
    public void isGetPostCommentSuc(CommunityCommentBean data) {
        if (data.getCount()>0){
            comment_num = data.getCount();
            tvCommentNum.setText("全部评论("+comment_num+")");
        }
        //如果加载更多没有数据的话，就将页码回置
        if (isLoadMore&&StringUtil.isListEmpty(data.getList())){
            mPage--;
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

    /**
     * 提交评论
     */
    private void putComment() {
        HashMap<String,String> map = new HashMap<>();
        map.put("content",etComment.getText().toString());
        map.put("timeline_id",mListBean.getId()+"");
        if (!etComment.getHint().toString().contains("@")&&!StringUtil.isEmpty(reply_id)){
            map.put("reply_id",reply_id);
        }
        mPresenter.putComment(PacketUtil.getRequestPacket(map));
    }
    /**
     * 提交评论成功
     */
    @Override
    public void isPutPostCommentSuc(String data) {
        showToast("评论成功");
        comment_num ++;
        tvCommentNum.setText("全部评论("+comment_num+")");
        //如果当前页面不满10条，则队尾插入一条静态数据，否则不插入
        if (10 > mCommentAdapter.getData().size() || mCommentAdapter.getData().size()%10!=0) {
            UserInfo userInfo = UserLoginBiz.getInstance(mContext).readUserInfo();
            CommunityCommentBean.ListBean listBean = new CommunityCommentBean.ListBean();
            listBean.setContent(etComment.getText().toString());
            listBean.setFrom_pic(userInfo.getHead_pic());
            listBean.setFrom_username(userInfo.getNickname());
            //回复时间
            listBean.setReply_time(System.currentTimeMillis()/1000L);
            //reply_id
            listBean.setReply_id(Integer.valueOf(data));
            if (selectIndex!=-1){
                listBean.setTo_pic(mCommentAdapter.getData().get(selectIndex).getFrom_pic());
                listBean.setTo_username(mCommentAdapter.getData().get(selectIndex).getFrom_username());
            }
            mCommentAdapter.addData(listBean);
            rvComment.smoothScrollToPosition(mCommentAdapter.getData().size()-1);
        }
        etComment.setText("");
    }

    /**
     * 防止多次点击
     */
    private long mLastClickTime;

    @OnClick({R.id.ll_toolbar_left, R.id.img_user_head, R.id.tv_put_comment, R.id.tv_comment, R.id.ll_all_click})
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
                if (StringUtil.isEmpty(etComment.getText().toString())){
                    return;
                }
                long nowTime = System.currentTimeMillis();
                if (nowTime - mLastClickTime > 1000L) {
                    putComment();
                    mLastClickTime = nowTime;
                }
                break;
                //点击评论
            case R.id.tv_comment:
                selectIndex=-1;
                showReplyView(0,"","");
                break;
                //点击屏幕外边，隐藏键盘
            case R.id.ll_all_click:
                disMissSoftKeyboard();
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
//                startActivity(new Intent(mContext,HomePageActivity.class).putExtra(Constant.USER_ID,item.getus));
            });
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        getCommentData(true);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isLoadMore){
            mPage--;
        }
        closeRefresh(smartRefresh);
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

}
