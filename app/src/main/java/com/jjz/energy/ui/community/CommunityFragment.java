package com.jjz.energy.ui.community;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.adapter.PhotoAdapter;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.util.glide.GlideUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 社区
 * @author: create by chenhao on 2019/8/14
 */
public class CommunityFragment extends BaseFragment {

    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.img_postings)
    ImageView imgPostings;
    @BindView(R.id.rv_community)
    RecyclerView rvCommunity;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
    private List<String> list;

    @Override
    protected void initView() {

        rvCommunity.setLayoutManager(new LinearLayoutManager(mContext));
         list = new ArrayList<>();
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566812800708&di=f1b73d47010fc5d0b17d52b7843d1ee1&imgtype=0&src=http%3A%2F%2Fimg4.cache.netease.com%2Fphoto%2F0001%2F2010-04-17%2F64EFS71V05RQ0001.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566812800708&di=f1b73d47010fc5d0b17d52b7843d1ee1&imgtype=0&src=http%3A%2F%2Fimg4.cache.netease.com%2Fphoto%2F0001%2F2010-04-17%2F64EFS71V05RQ0001.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566812800708&di=f1b73d47010fc5d0b17d52b7843d1ee1&imgtype=0&src=http%3A%2F%2Fimg4.cache.netease.com%2Fphoto%2F0001%2F2010-04-17%2F64EFS71V05RQ0001.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566812800708&di=f1b73d47010fc5d0b17d52b7843d1ee1&imgtype=0&src=http%3A%2F%2Fimg4.cache.netease.com%2Fphoto%2F0001%2F2010-04-17%2F64EFS71V05RQ0001.jpg");
        CommunityAdapter communityAdapter = new CommunityAdapter(R.layout.item_community,list);
        rvCommunity.setAdapter(communityAdapter);
        //进入详情
        communityAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext,CommunityDetailActivity.class));
        });
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


    @OnClick({R.id.img_search, R.id.img_postings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_search:
                //搜索
                break;
                //发布帖子
            case R.id.img_postings:
                //发布宝贝
                startActivity(new Intent(mContext, PutCommunityActivity.class));
                break;
        }
    }

    /**
     * 社区列表
     */
    class CommunityAdapter extends BaseRecycleNewAdapter<String>{

        public CommunityAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imgHead = helper.getView(R.id.item_img_user_head);
            GlideUtils.loadCircleImage(mContext,"http://b-ssl.duitang.com/uploads/item/201809/26/20180926162125_vjbwi.jpg",imgHead);
            RecyclerView rv_photo = helper.getView(R.id.item_rv_photo);
            rv_photo.setLayoutManager(new GridLayoutManager(mContext,2));
            rv_photo.setAdapter(new PhotoAdapter(R.layout.item_photo,list));
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
