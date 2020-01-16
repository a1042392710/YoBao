package com.jjz.energy.ui.home.entrust;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import com.jjz.energy.entry.home.EntrustListBean;
import com.jjz.energy.presenter.home.EntrustListPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.home.IEntrustListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Features: 我发布的委托
 * @author: create by chenhao on 2020/1/6
 */
@RuntimePermissions
public class PutEntrustFragment extends BaseFragment<EntrustListPresenter>implements IEntrustListView {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;

    private MineEntrustAdapter mAdapter;

    /**
     * 页码
     */
    private int mPage = 1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;

    @Override
    protected EntrustListPresenter getPresenter() {
        return new EntrustListPresenter(this);
    }

    @Override
    protected void initView() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MineEntrustAdapter(R.layout.item_mine_entrust, new ArrayList<>());
        rvList.setAdapter(mAdapter);

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
        HashMap<String,String> map  = new HashMap<>();
        map.put("page",mPage+"");
        map.put("mine","1");
        mPresenter.getMinePutEntrustList(PacketUtil.getRequestPacket(map),isLoadMore);
    }

    /**
     * 完成委托
     */
    private int finishPosition;
    public void finishEntrust(String order_sn ,int position){
        finishPosition = position;
        mPresenter.finishEntrust(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.ORDER_SN,order_sn)));
    }

    /**
     * 取消委托
     */
    private int canclePosition;
    public void cancleEntrust(String order_sn ,int position){
        canclePosition = position;
        mPresenter.cancleEntrust(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.ORDER_SN,order_sn)));
    }


    @Override
    public void isGetEntrustListSuc(EntrustListBean data) {
        //获取我接受的委托
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mAdapter.addNewData(data.getList()))
                mSmartRefreshLayout.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data,"您还没有发布委托",false,null));
                mSmartRefreshLayout.setEnableLoadMore(false);
            } else {
                //有数据就开启加载更多
                mSmartRefreshLayout.setEnableLoadMore(true);
            }
        }
        closeRefresh(mSmartRefreshLayout);
    }


    @Override
    public void isFinishEntrustSuc(String msg) {
        showToast("您已完结此单，赏金已到接单人账户");
        mAdapter.getData().get(finishPosition).setStatus(4);
        mAdapter.getData().get(finishPosition).setState("已完成");
        mAdapter.notifyItemChanged(finishPosition);
    }

    @Override
    public void isCancleEntrustSuc(String msg) {
        showToast("取消委托成功");
        mAdapter.getData().get(canclePosition).setStatus(3);
        mAdapter.getData().get(canclePosition).setState("已取消");
        mAdapter.notifyItemChanged(canclePosition);
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isLoadMore){
            mPage--;
        }
        closeRefresh(mSmartRefreshLayout);
        showToast(msg);
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
    protected int layoutId() {
        return R.layout.fragment_list;
    }

    private  class MineEntrustAdapter extends BaseRecycleNewAdapter<EntrustListBean.ListBean> {


        public MineEntrustAdapter(int layoutResId, @Nullable List<EntrustListBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, EntrustListBean.ListBean item) {
            helper.setText(R.id.item_tv_user_name,item.getNickname());
            helper.setText(R.id.item_tv_content,item.getDemand_content());
            helper.setText(R.id.item_tv_order_price, "¥"+item.getMoney());
            //订单状态:0已发布,1已接单 2接单人已完成，3,已取消,4已完成
            helper.setText(R.id.item_tv_order_state, item.getState());
            //打电话
            helper.getView(R.id.item_tv_talk).setVisibility(!StringUtil.isEmpty(item.getNickname())? View.VISIBLE:View.GONE);
            helper.getView(R.id.item_tv_talk).setOnClickListener(v -> {
                PopWindowUtil.getInstance().showPopupWindow(mContext, "您将拨打电话:"+item.getMobile(), () -> {
                    PutEntrustFragmentPermissionsDispatcher.callPhoneWithCheck(PutEntrustFragment.this, item.getMobile());
                });
            });
            //取消委托
            helper.getView(R.id.item_tv_order_cancle).setVisibility(item.getStatus()>=3?View.GONE:View.VISIBLE);
            helper.getView(R.id.item_tv_order_cancle).setOnClickListener(v -> {
                PopWindowUtil.getInstance().showPopupWindow(mContext, "是否确认取消该次委托？", () -> {
                    cancleEntrust(item.getOrder_sn(),helper.getLayoutPosition());
                });
            });
            //接单人确认完成订单后才会出现
            helper.getView(R.id.item_tv_order_finish).setVisibility(item.getStatus() == 2 ?
                    View.VISIBLE : View.GONE);
            //确认委托完成
            helper.getView(R.id.item_tv_order_finish).setOnClickListener(v -> {
                PopWindowUtil.getInstance().showPopupWindow(mContext, "是否确认接单人已完成委托，确认后赏金将打给对方？",
                        () -> {
                    finishEntrust(item.getOrder_sn(), helper.getLayoutPosition());
                });
            });
            //头像
            ImageView item_img_head = helper.getView(R.id.item_img_head);
            item_img_head.setVisibility(StringUtil.isEmpty(item.getHead_pic()) ?
                    View.GONE : View.VISIBLE);
            GlideUtils.loadHead(mContext, item.getHead_pic(), item_img_head);

        }
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callPhone(String phone) {
        if (!StringUtil.isEmpty(phone)) {
            // 拨号：激活系统的拨号组件
            Intent intent = new Intent(); // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            Uri data = Uri.parse("tel:" + phone); // 设置数据
            intent.setData(data);
            startActivity(intent); // 激活Activity组件
        }
    }

    //给用户解释为什么要申请权限
    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showCallPhone(final PermissionRequest request) {
        //唤起打电话权限
        PopWindowUtil.getInstance().showPopupWindow(mContext, "没有电话权限可不能打电话哦", () -> {
            request.proceed();//继续执行请求
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PutEntrustFragmentPermissionsDispatcher.onRequestPermissionsResult(this,
                requestCode, grantResults);
    }
}
