package com.jjz.energy.ui.home.commodity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.commodity.HomePageCommentBean;
import com.jjz.energy.presenter.mine.HomePagePresenter;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.mine.IHomePageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @Features: 评价列表
 * @author: create by chenhao on 2019/8/6
 */
public class HomePageCommentFragment extends BaseFragment<HomePagePresenter> implements IHomePageView {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;


    private CommentAdapter mAdapter ;

    /**
     * 页码
     */
    private int mPage = 1;

    /*
     * 是否加载更多
     */
    private boolean isLoadMore;

    /**
     * 用户id
     */
    private int user_id;

    @Override
    protected void initView() {
        //用户id
        Bundle bundle = getArguments();
        user_id = bundle.getInt("user_id");
        initRvAndRefresh();
        getData(false);

    }
    /**
     * 初始化监听
     */
    private void initRvAndRefresh() {
        //禁止刷新
        smartRefresh.setEnableRefresh(false);
        //初始化 rv
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CommentAdapter(R.layout.item_homepage_comment,
                new ArrayList<>());
        rvList.setAdapter(mAdapter);
        //上拉加载
        smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            //获取指定类别的商品
            getData(true);
        });

    }

    /**
     * 获取评价列表
     * @param isLoadMore  是否加载更多
     */
    private void getData(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        HashMap<String,String> map = new HashMap<>();
        map.put("page",mPage+"");
        map.put(Constant.USER_ID,user_id+"");
        mPresenter.getUserComments(PacketUtil.getRequestPacket(map),isLoadMore);
    }

    @Override
    public void isGetUserComments(HomePageCommentBean data) {
        //加载更多
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mAdapter.addNewData(data.getList()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mAdapter.notifyChangeData(data.getList())){
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data,"还没有对你的评论",true, v -> {
                    //点击刷新
                    getData(false);
                }));
                smartRefresh.setEnableLoadMore(false);
            }else{
                //有数据就开启加载更多
                smartRefresh.setEnableLoadMore(true);
            }
        }
        closeRefresh(smartRefresh);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        //请求失败时页码归位
        if (isLoadMore){
            mPage--;
        }
        closeRefresh(smartRefresh);
        showToast(msg);
    }

    @Override
    protected HomePagePresenter getPresenter() {
        return new HomePagePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    class CommentAdapter extends BaseRecycleNewAdapter<HomePageCommentBean.ListBean>{

        public CommentAdapter(int layoutResId, @Nullable List<HomePageCommentBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomePageCommentBean.ListBean item) {
            //用户头像
            ImageView item_img_my_head = helper.getView(R.id.item_img_my_head);
            //评价等级
            ImageView item_img_my_evaluate_mark = helper.getView(R.id.item_img_my_evaluate_mark);
            //评价图片
            ImageView item_img_my_evaluate = helper.getView(R.id.item_img_my_evaluate);
            //有图显示，没有就隐藏
            item_img_my_evaluate.setVisibility(0==0? View.VISIBLE:View.GONE);
            //名称
            helper.setText(R.id.item_tv_my_name,"");
            //内容
            helper.setText(R.id.item_tv_my_evaluate,"");
            //时间
            helper.setText(R.id.item_tv_my_evaluate_time,"");



        }
    }

}
