package com.jjz.energy.ui.mine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.MineLikeAndFansBean;
import com.jjz.energy.presenter.mine.MineLikeAndFansPresenter;
import com.jjz.energy.ui.mine.information.HomePageActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.mine.IMineLikeAndFansView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @Features: 我的粉丝
 * @author: create by chenhao on 2019/8/6
 */
public class MineLikeFragment extends BaseFragment<MineLikeAndFansPresenter> implements IMineLikeAndFansView {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;

    private MineLikeAdapter mAdapter;

    /**
     * 页码
     */
    private int mPage = 1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;
    /**
     * 当前操作的itemPosision
     */
    private int selectPosition;



    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MineLikeAdapter(R.layout.item_mine_fans, new ArrayList<>());
        rvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext, HomePageActivity.class).putExtra("user_id",mAdapter.getData().get(position).getUser_id()));
        });
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
        });
        getData(false);
    }

    /**
     * 获取数据
     */
    private void getData(boolean isLoadMore){
        this.isLoadMore = isLoadMore;
        mPresenter.getFocusList(PacketUtil.getRequestPacket(Utils.stringToMap("page",mPage+"")),isLoadMore);
    }

    /**
     * 取消关注
     */
    private void cancleFocus(int user_id){
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id",user_id+"");
        //取消关注
        map.put("act","cancel");
        mPresenter.setFocusUser(PacketUtil.getRequestPacket(map));
    }

    //获取我的关注
    @Override
    public void isGetMineLikeSuc(MineLikeAndFansBean data) {
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mAdapter.addNewData(data.getList()))
                mSmartRefreshLayout.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data,"您还没有关注用户",false,null));
                mSmartRefreshLayout.setEnableLoadMore(false);
            } else {
                //有数据就开启加载更多
                mSmartRefreshLayout.setEnableLoadMore(true);
            }
        }
        closeRefresh(mSmartRefreshLayout);
    }

    //取消关注的返回结果
    @Override
    public void isFocusUserSuccess(String data) {
        //取消关注成功
        mAdapter.remove(selectPosition);
        //没数据了显示无数据页面
        if (StringUtil.isListEmpty(mAdapter.getData())){
            mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data,"您还没有关注用户",false,null));
            mSmartRefreshLayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isLoadMore){
            mPage--;
        }
        closeRefresh(mSmartRefreshLayout);
        showToast(msg);
    }

    private  class MineLikeAdapter extends BaseRecycleNewAdapter<MineLikeAndFansBean.ListBean>{


        public MineLikeAdapter(int layoutResId, @Nullable List<MineLikeAndFansBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MineLikeAndFansBean.ListBean item) {

            ImageView item_img_head = helper.getView(R.id.item_img_head);
            GlideUtils.loadCircleImage(mContext,item.getHead_pic(),item_img_head);

            helper.setText(R.id.item_tv_user_name,item.getNickname());
            helper.setText(R.id.item_tv_user_desc,item.getDesc());
            //取消关注
            helper.getView(R.id.item_tv_is_like).setOnClickListener(v -> {
                selectPosition = helper.getLayoutPosition();
                cancleFocus(item.getUser_id());
            });

        }
    }

    @Override
    protected MineLikeAndFansPresenter getPresenter() {
        return new MineLikeAndFansPresenter(this);
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
}
