package com.jjz.energy.ui.community;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.community.CommunityBean;
import com.jjz.energy.presenter.community.CommunityPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.home.ICommunityView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 社区
 * @author: create by chenhao on 2019/8/14
 */
public class CommunityFragment extends BaseFragment <CommunityPresenter>implements ICommunityView, OnRefreshLoadMoreListener {

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
        rvCommunity.setAdapter(mAdapter);
        //进入详情
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext,CommunityDetailActivity.class));
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



    @Override
    public void isGetPostListSuc(CommunityBean data) {
        if (!StringUtil.isListEmpty(data.getList()) && data.getList().size()>8){
            smartRefresh.setEnableLoadMore(true);     //有够长的数据就开启加载更多
        }else{
            smartRefresh.setEnableLoadMore(false);     //否则关闭
        }
        //加载更多
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
           mAdapter.addNewData(data.getList());
        } else {
            //刷新数据，无数据显示空页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "社区还没有帖子", false,
                        null));
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
        } else {
            showToast(msg);
        }
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
     * 社区列表
     */
    class CommunityAdapter extends BaseRecycleNewAdapter<CommunityBean.ListBean>{

        public CommunityAdapter(int layoutResId, @Nullable List<CommunityBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CommunityBean.ListBean item) {
            ImageView imgHead = helper.getView(R.id.item_img_user_head);
            GlideUtils.loadCircleImage(mContext,"http://b-ssl.duitang.com/uploads/item/201809/26/20180926162125_vjbwi.jpg",imgHead);
            RecyclerView rv_photo = helper.getView(R.id.item_rv_photo);
            rv_photo.setLayoutManager(new GridLayoutManager(mContext,2));
//            rv_photo.setAdapter(new PhotoAdapter(R.layout.item_photo,list));
            imgHead.setOnClickListener(v -> {
                //查看大图
//                    ImagePagerActivity.ImageSize imageSize =
//                            new ImagePagerActivity.ImageSize(v.getMeasuredWidth(),
//                                    v.getMeasuredHeight());
//                    ImagePagerActivity.startImagePagerActivity(BaseApplication.AppContext, mData,0
//                            , imageSize);
            });
        }
    }



}
