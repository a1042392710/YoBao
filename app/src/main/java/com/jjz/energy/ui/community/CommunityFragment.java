package com.jjz.energy.ui.community;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.adapter.PhotoAdapter;
import com.jjz.energy.base.BaseLazyFragment;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.community.Community;
import com.jjz.energy.entry.community.CommunityBean;
import com.jjz.energy.presenter.community.CommunityPresenter;
import com.jjz.energy.ui.home.login.LoginActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.view.home.ICommunityView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 社区
 * @author: create by chenhao on 2019/8/14
 */
public class CommunityFragment extends BaseLazyFragment<CommunityPresenter> implements ICommunityView, OnRefreshLoadMoreListener {

    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.img_postings)
    ImageView imgPostings;
    @BindView(R.id.rv_community)
    RecyclerView rvCommunity;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    /**
     * 帖子列表
     */
    private CommunityAdapter mAdapter ;

    /**
     * 页码
     */
    private int mPage = 1;

    /*
     * 是否加载更多
     */
    private boolean isLoadMore;

    @Override
    protected void initView() {
        smartRefresh.setOnRefreshLoadMoreListener(this);
        rvCommunity.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CommunityAdapter(R.layout.item_community,new ArrayList<>());
        ((SimpleItemAnimator)rvCommunity.getItemAnimator()).setSupportsChangeAnimations(false);
        rvCommunity.setAdapter(mAdapter);
        //进入详情
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (UserLoginBiz.getInstance(mContext).detectUserLoginStatus()){
                startActivity(new Intent(mContext,CommunityDetailActivity.class).putExtra("id",mAdapter.getItem(position).getId()).putExtra(Constant.INTENT_KEY_OBJECT,mAdapter.getItem(position)));
            }else{
                startActivity(new Intent(mContext,LoginActivity.class));
            }
        });
        getData(false);
    }

    /**
     * 获取数据
     */
    private void getData(boolean isLoadMore){
        this.isLoadMore = isLoadMore;
        mPresenter.getPostList(PacketUtil.getRequestPacket(Utils.stringToMap("page",mPage+"")),isLoadMore);
    }


    /**
     * 点赞或者取消店在哪 0 未点赞 1 点赞
     */
    private void putLike(int id, int is_like){
        HashMap<String,String> map = new HashMap<>();
        map.put("timeline_id",id+"");
        map.put("act",is_like==0?"cancel":"like");
        mPresenter.putLike(PacketUtil.getRequestPacket(map));
    }

    @Override
    public void isPutPostLikeSuc(String data) {
        //指定操作类型  1 点赞  /  还是 0 取消点赞
        int is_like = mAdapter.getData().get(selectIndex).getIs_like();
        mAdapter.getData().get(selectIndex).setIs_like( is_like == 0?1:0);
        //点赞数量
        int num =  mAdapter.getData().get(selectIndex).getTop_num();
        if (is_like==0){
            //点赞
           mAdapter.getData().get(selectIndex).setTop_num(num+1);
        }else{
            //取消点赞
            mAdapter.getData().get(selectIndex).setTop_num(num-1);
        }

        mAdapter.notifyItemChanged(selectIndex);
    }

    @Override
    public void isGetPostListSuc(CommunityBean data) {
        if (!StringUtil.isListEmpty(data.getList()) && data.getList().size()>8){
            smartRefresh.setEnableLoadMore(true);     //有够长的数据就开启加载更多
        }else{
            smartRefresh.setEnableLoadMore(false);     //否则关闭
        }
        //加载更多
        if (isLoadMore) {
           mAdapter.addNewData(data.getList());
        } else {
            //刷新数据，无数据显示空页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "社区还没有帖子", true, v -> {
                            getData(true);
                        }));
            }
        }
        closeRefresh(smartRefresh);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isNetAndServiceError) {
            //网络错误时刷新页码
            mPage=1;
            mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_timeout, "网络发生错误",true, v -> {
                getData(false);
            }));
        }
        //请求失败时页码归位
        if (isLoadMore) {
            mPage--;
        }
        closeRefresh(smartRefresh);
    }

    @OnClick({ R.id.img_postings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
                //发布帖子
            case R.id.img_postings:
                startActivity(new Intent(mContext, PutCommunityActivity.class));
                break;
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_community;
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
    protected CommunityPresenter getPresenter() {
        return new CommunityPresenter(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        getData(true);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPage=1;
        getData(false);
    }

    /**
     * 记录点赞的条目
     */
    private int selectIndex;

    /**
     * 社区列表
     */
    class CommunityAdapter extends BaseRecycleNewAdapter<Community>{

        public CommunityAdapter(int layoutResId, @Nullable List<Community> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Community item) {
            ImageView imgHead = helper.getView(R.id.item_img_user_head);
            RecyclerView rv_photo = helper.getView(R.id.item_rv_photo);
            TextView item_tv_like = helper.getView(R.id.item_tv_like);
            item_tv_like.setText(item.getTop_num()+"");
            //内容
            helper.setText(R.id.item_tv_content,item.getContent());
            helper.setText(R.id.item_tv_comment,item.getMsg_num()+"");
            //头像
            GlideUtils.loadCircleImage(mContext, item.getHead_pic(), imgHead);
            helper.setText(R.id.item_tv_user_name,item.getNickname());
            helper.setText(R.id.item_tv_put_time, DateUtil.getTimeFormatText(new Date(item.getAdd_time()*1000L)));
            //如果有图片则加载rv
            if (!StringUtil.isEmpty(item.getImages())) {
                List<String> list = Arrays.asList(item.getImages().split(","));
                rv_photo.setVisibility(View.VISIBLE);
                rv_photo.setLayoutManager(new GridLayoutManager(mContext, 2));
                //放入图片的数据
                rv_photo.setAdapter(new PhotoAdapter(R.layout.item_photo ,list));

            } else {
                rv_photo.setVisibility(View.GONE);
            }
            //没点赞
            if (item.getIs_like()==0){
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_unchecked_like_two);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                item_tv_like.setCompoundDrawables(null, null, drawable, null);

            }else{
                //点赞了
                Drawable drawable2 = mContext.getResources().getDrawable(R.mipmap.ic_checked_like_two);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                item_tv_like.setCompoundDrawables(null, null, drawable2, null);
            }
            //点赞
            item_tv_like.setOnClickListener( v -> {
                if (UserLoginBiz.getInstance(mContext).detectUserLoginStatus()){
                    selectIndex = helper.getLayoutPosition();
                    putLike(item.getId(),item.getIs_like() == 0 ? 1 : 0 );
                }else{
                    startActivity(new Intent(mContext,LoginActivity.class));
                }

            });
        }
    }



}
