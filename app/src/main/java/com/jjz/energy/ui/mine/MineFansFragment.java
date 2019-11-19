package com.jjz.energy.ui.mine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseLazyFragment;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.mine.MineLikeAndFansBean;
import com.jjz.energy.presenter.mine.MineLikeAndFansPresenter;
import com.jjz.energy.ui.mine.information.HomePageActivity;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.AesUtils;
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
public class MineFansFragment extends BaseLazyFragment<MineLikeAndFansPresenter> implements IMineLikeAndFansView {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;

    private MineFansAdapter mAdapter;

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
         mAdapter = new MineFansAdapter(R.layout.item_mine_fans, new ArrayList<>());
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
        mPresenter.getFansList(PacketUtil.getRequestPacket(Utils.stringToMap("page",mPage+"")),isLoadMore);
    }

    /**
     * 关注 or 取消关注
     */
    private void cancleAndFocus(int user_id,String act){
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", AesUtils.encrypt(String.valueOf(user_id), AesUtils.KEY, AesUtils.IV));
        //关注 or 取消关注 要做的操作
        map.put("act",act);
        mPresenter.setFocusUser(PacketUtil.getRequestPacket(map));
    }

    @Override
    public void isGetMineFansSuc(MineLikeAndFansBean data) {
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mAdapter.addNewData(data.getList()))
                mSmartRefreshLayout.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "您还没有粉丝", false,
                        null));
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
        showToast("操作成功");
        int is_focus = mAdapter.getData().get(selectPosition).getIs_focus();
        //关注 或 取消关注
        mAdapter.getData().get(selectPosition).setIs_focus(is_focus==1?0:1);
        //没数据了显示无数据页面
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isLoadMore){
            mPage--;
        }
        closeRefresh(mSmartRefreshLayout);
        showToast(msg);
    }

    private  class MineFansAdapter extends BaseRecycleNewAdapter<MineLikeAndFansBean.ListBean>{


        public MineFansAdapter(int layoutResId, @Nullable List<MineLikeAndFansBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MineLikeAndFansBean.ListBean item) {

            ImageView item_img_head = helper.getView(R.id.item_img_head);
            GlideUtils.loadCircleImage(mContext,item.getHead_pic(),item_img_head);

            helper.setText(R.id.item_tv_user_name,item.getNickname());
            helper.setText(R.id.item_tv_user_desc,item.getDesc());
            TextView tvIsLike = helper.getView(R.id.item_tv_is_like);
            if (item.getIs_focus()==1){
                tvIsLike.setBackgroundResource(R.drawable.bg_gray_solid_b5);
                tvIsLike.setText("取消关注");
            }else{
                tvIsLike.setText("关注");
                tvIsLike.setBackgroundResource(R.drawable.bg_orange_solid);
            }
            helper.getView(R.id.item_tv_is_like).setOnClickListener(v -> {
                selectPosition = helper.getLayoutPosition();
                //关注 or 取消关注
                cancleAndFocus(item.getUser_id(),item.getIs_focus()==1?"cancel":"focus");
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
