package com.jjz.energy.ui.mine;

import android.content.Intent;
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
import com.jjz.energy.entry.LikeGoodsBean;
import com.jjz.energy.presenter.mine.MineLikeCommodityPresenter;
import com.jjz.energy.ui.home.commodity.CommodityDetailActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.mine.IMineLikeCommodityView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 我收藏的
 * @author: create by chenhao on 2019/7/24
 */
public class MineLikeCommodityActivity extends BaseActivity<MineLikeCommodityPresenter>implements IMineLikeCommodityView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_mine_like)
    RecyclerView rvMineLike;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    /*
     * 我的收藏列表适配器
     */
    private  MineListAdapter mAdapter;
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

    @Override
    protected void initView() {
        tvToolbarTitle.setText("我收藏的");
        mAdapter = new MineListAdapter(R.layout.item_mine_like,new ArrayList<>());
        rvMineLike.setLayoutManager(new LinearLayoutManager(mContext));
        rvMineLike.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext, CommodityDetailActivity.class).putExtra(CommodityDetailActivity.GOODS_ID,mAdapter.getData().get(position).getGoods_id()));
        });
        smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            getData(true);
        });
        getData(false);
    }

    /**
     * 获取数据
     */
    private void getData(boolean isLoadMore){
        this.isLoadMore = isLoadMore;
        mPresenter.getLikeGoods(PacketUtil.getRequestPacket(Utils.stringToMap("page",mPage+"")),isLoadMore);
    }

    /**
     * 取消收藏
     */
    private void cancleCollect(String goods_id){
        HashMap<String, String> map = new HashMap<>();
        map.put("goods_id",goods_id);
        //取消收藏
        map.put("act","cancel");
        mPresenter.putCollect(PacketUtil.getRequestPacket(map));

    }


    @Override
    public void isPutCollectSuc(String data) {
        //取消收藏成功
        mAdapter.remove(selectPosition);
        //没数据了显示无数据页面
        if (StringUtil.isListEmpty(mAdapter.getData())){
            tvToolbarTitle.setText("我的收藏");
            mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_data,"您还没有收藏商品",false,null));
            smartRefresh.setEnableLoadMore(false);
        }else{
            tvToolbarTitle.setText("我发布的("+mAdapter.getData().size()+")");
        }
    }

    //获取收藏列表
    @Override
    public void isSuccess(LikeGoodsBean data) {
        if (!StringUtil.isListEmpty(data.getList())){
            tvToolbarTitle.setText("我收藏的("+data.getNum()+")");
        }
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mAdapter.addNewData(data.getList()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_data, "您还没有收藏商品", true,
                        null));
                smartRefresh.setEnableLoadMore(false);
            } else {
                //有数据就开启加载更多
                smartRefresh.setEnableLoadMore(true);
            }
        }
        closeRefresh(smartRefresh);
    }


    class MineListAdapter extends BaseRecycleNewAdapter<LikeGoodsBean.ListBean>{

        public MineListAdapter(int layoutResId, @Nullable List<LikeGoodsBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LikeGoodsBean.ListBean item) {
            //是否下架
            ImageView item_is_down = helper.getView(R.id.item_img_is_down);
            //用户头像
            ImageView item_img_user_head = helper.getView(R.id.item_img_user_head);
            GlideUtils.loadCircleImage(mContext,item.getHead_pic(),item_img_user_head);
            //商品图
            ImageView item_img_commodity = helper.getView(R.id.item_img_commodity);
            GlideUtils.loadRoundCircleImage(mContext,item.getGoods_images(),item_img_commodity);
            //发布时间
            helper.setText(R.id.item_tv_time, DateUtil.getTimeFormatText(new Date(item.getOn_time()*1000))+"发布");
            //用户昵称
            helper.setText(R.id.item_tv_user_name, item.getNickname());
            //标题
            helper.setText(R.id.item_tv_title, item.getGoods_name());
            //浏览数 +  收藏数 + 留言数
            helper.setText(R.id.item_tv_number,
                    item.getClick_count() + "次浏览 "
                            + item.getCollect_sum() + "人想买 "
                            + item.getComment_num() + "人留言");
            //价格
            helper.setText(R.id.item_tv_new_money, item.getShop_price());
            //是否下架
            item_is_down.setVisibility(item.getIs_on_sale()==0? View.VISIBLE:View.GONE);
            //原价
            helper.setText(R.id.item_tv_old_money, "原价￥"+item.getMarket_price());
            //取消收藏
            helper.getView(R.id.item_tv_lable_one).setOnClickListener(v -> {
                //记录下标
                selectPosition = helper.getLayoutPosition();
                cancleCollect(String.valueOf(item.getGoods_id()));
            });
            //立即购买  todo 未完成 进入确认购买页
            helper.getView(R.id.item_tv_lable_two).setOnClickListener(v -> {

            });

        }
    }

    @Override
    protected MineLikeCommodityPresenter getPresenter() {
        return new MineLikeCommodityPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_like;
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
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isLoadMore){
            mPage--;
        }
        closeRefresh(smartRefresh);
        showToast(msg);
    }
}
