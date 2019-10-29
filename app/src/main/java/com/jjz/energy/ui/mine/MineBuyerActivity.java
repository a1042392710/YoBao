package com.jjz.energy.ui.mine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.mine.MineBuyerBean;
import com.jjz.energy.entry.enums.ShopOrderStatusEnum;
import com.jjz.energy.presenter.mine.MineBuyerPresenter;
import com.jjz.energy.ui.mine.information.HomePageActivity;
import com.jjz.energy.ui.mine.shop_order.EvaluateActivity;
import com.jjz.energy.ui.mine.shop_order.EvaluateDetailsActivity;
import com.jjz.energy.ui.mine.shop_order.OrderDetailsActivity;
import com.jjz.energy.ui.notice.IMActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.mine.IMineBuyerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 我买到的
 * @author: create by chenhao on 2019/7/24
 */
public class MineBuyerActivity extends BaseActivity <MineBuyerPresenter>implements IMineBuyerView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_mine_buyer)
    RecyclerView rvMineBuyer;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    /**
     * 页码
     */
    private int mPage=1;
    /*
     * 是否加载更多
     */
    private boolean isLoadMore;
    /**
     * 当前操作的itemPosision
     */
    private int selectPosition;
    /**
     * 数据适配器
     */
    private MineListAdapter mAdapter;

    @Override
    protected void initView() {
        tvToolbarTitle.setText("我买到的");
        mAdapter = new MineListAdapter(R.layout.item_mine_buyer,new ArrayList<>());
        rvMineBuyer.setLayoutManager(new LinearLayoutManager(mContext));
        rvMineBuyer.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext, OrderDetailsActivity.class).putExtra(Constant.ORDER_SN,mAdapter.getData().get(selectPosition).getOrder_sn()).putExtra(Constant.USER_TYPE,0));
        });
        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage=1;
        getData(false);
    }

    /**
     * 获取数据
     */
    private void getData(boolean isLoadMore){
        this.isLoadMore = isLoadMore;
        mPresenter.getMyBuyer(PacketUtil.getRequestPacket(Utils.stringToMap("page",mPage+"")),isLoadMore);
    }


    @Override
    public void isSuccess(MineBuyerBean data) {
        if (!StringUtil.isListEmpty(data.getList())){
            tvToolbarTitle.setText("我买到的("+data.getCount()+")");
        }
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mAdapter.addNewData(data.getList()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_data, "您还没有购买商品", false,
                        null));
                smartRefresh.setEnableLoadMore(false);
            } else {
                //有数据就开启加载更多
                smartRefresh.setEnableLoadMore(true);
            }
        }
        closeRefresh(smartRefresh);
    }

    //收货成功，并且刷新当条数据
    @Override
    public void isConfirmReceiptSuc(String data) {
        showToast("收货成功");
        MineBuyerBean.MineBuyerListBean bean = mAdapter.getData().get(selectPosition);
        bean .setState("待评价");
        bean .setStatus(ShopOrderStatusEnum.TO_EVALUATE.getIndex());
        mAdapter.notifyItemChanged(selectPosition);
    }


    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
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

    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected MineBuyerPresenter getPresenter() {
        return new MineBuyerPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_buyer;
    }


    //列表适配器
    class MineListAdapter extends BaseRecycleNewAdapter<MineBuyerBean.MineBuyerListBean>{

        public MineListAdapter(int layoutResId, @Nullable List<MineBuyerBean.MineBuyerListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MineBuyerBean.MineBuyerListBean item) {

            TextView tvLableOne =  helper.getView(R.id.item_tv_lable_one);
            TextView tvLableTwo =  helper.getView(R.id.item_tv_lable_two);

            //用户头像
            ImageView item_img_user_head = helper.getView(R.id.item_img_user_head);
            GlideUtils.loadCircleImage(mContext,item.getHead_pic(),item_img_user_head);
            //商品图
            ImageView item_img_commodity = helper.getView(R.id.item_img_commodity);
            GlideUtils.loadRoundCircleImage(mContext,item.getGoods_images(),item_img_commodity);
            //用户昵称
            helper.setText(R.id.item_tv_user_name, item.getNickname());
            //标题
            helper.setText(R.id.item_tv_title, item.getGoods_name());
            //订单状态
            helper.setText(R.id.item_tv_order_state, item.getState());
            //价格
            helper.setText(R.id.item_tv_new_money, item.getGoods_price());
            //原价
            helper.setText(R.id.item_tv_old_money, "原价￥" + item.getMarket_price());
            //设置底部标签文字
            setLableText(helper.getView(R.id.item_tv_lable_one),
                    helper.getView(R.id.item_tv_lable_two), item.getStatus());
            //标签一
            tvLableOne.setOnClickListener(v -> {
                //记录我点了哪个item
                selectPosition = helper.getLayoutPosition();
                lableClick(tvLableOne.getText().toString(), item);
            });
            //标签二
            tvLableTwo.setOnClickListener(v -> {
                //记录我点了哪个item
                selectPosition = helper.getLayoutPosition();
                lableClick(tvLableTwo.getText().toString(), item);
            });
            //个人主页
            helper.getView(R.id.ll_user_info).setOnClickListener(v -> {
                startActivity(new Intent(mContext, HomePageActivity.class).putExtra(Constant.USER_ID,item.getUser_id()));
            });
        }

        /**
         * 标签点击事件
         */
        private void lableClick(String btnStr,MineBuyerBean.MineBuyerListBean data){
            switch (btnStr) {
                case "提醒发货":
                    break;
                case "联系卖家":
                    startActivity(new Intent(mContext, IMActivity.class).putExtra("userName",data.getMobile()));
                    break;
                case "申请退款":
                    break;
                case "确认收货":
                    PopWindowUtil.getInstance().showPopupWindow(mContext, "点击按钮确认收货", () -> {
                        mPresenter.confirmReceipt(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.ORDER_SN, data.getOrder_sn())));
                    });
                    break;
                case "评价一下":
                    startActivity(new Intent(mContext, EvaluateActivity.class).putExtra(Constant.ORDER_SN, data.getOrder_sn()));
                    break;
                case "查看评价":
                    startActivity(new Intent(mContext, EvaluateDetailsActivity.class).putExtra(Constant.ORDER_SN, data.getOrder_sn()));
                    break;
            }
        }

        /**
         * 设置标签文字
         */
        private void setLableText(TextView textOne, TextView textTwo, int status) {
            textOne.setVisibility(View.VISIBLE);
            textTwo.setVisibility(View.VISIBLE);
            switch (status) {
                //待发货
                case 1:
                    textOne.setText("提醒发货");
                    textTwo.setText("联系卖家");
                    break;
                //待收货
                case 2:
                    textOne.setText("申请退款");
                    textTwo.setText("确认收货");
                    break;
                //待评价
                case 3:
                    textOne.setText("评价一下");
                    textTwo.setText("联系卖家");
                    break;
                //交易关闭
                case 4:
                    textOne.setVisibility(View.GONE);
                    textTwo.setText("联系卖家");
                    break;
                //交易完成
                case 5:
                    textOne.setText("查看评价");
                    textTwo.setText("联系卖家");
                    break;

            }

        }
    }
}
