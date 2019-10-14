package com.jjz.energy.ui.mine;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.LikeGoodsBean;
import com.jjz.energy.presenter.home.MinePutPresenter;
import com.jjz.energy.ui.home.commodity.CommodityDetailActivity;
import com.jjz.energy.ui.home.commodity.PutCommodityActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.home.IMinePutView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @Features: 我发布的
 * @author: create by chenhao on 2019/7/24
 */
public class MinePutActivity extends BaseActivity <MinePutPresenter>implements IMinePutView {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_mine_put)
    RecyclerView rvMinePut;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    /**
     * 我的发布
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
        tvToolbarTitle.setText("我发布的");
        mAdapter = new MineListAdapter(R.layout.item_mine_put,new ArrayList<>());
        rvMinePut.setLayoutManager(new LinearLayoutManager(mContext));
        rvMinePut.setAdapter(mAdapter);
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
        mPresenter.getMinePutGoods(PacketUtil.getRequestPacket(Utils.stringToMap("page",mPage+"")),isLoadMore);
    }

    /**
     * 下架商品
     * @param goods_id
     */
    private void downCommodity(String goods_id){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("goods_id",goods_id);
        // onsale 上架  offsale 下架
        hashMap.put("act","offsale");
        mPresenter.downCommodity(PacketUtil.getRequestPacket(hashMap));
    }

    @Override
    public void isGetMineCommditySuccess(LikeGoodsBean data) {
        if (!StringUtil.isListEmpty(data.getList())){
            tvToolbarTitle.setText("我发布的("+data.getNum()+")");
        }
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mAdapter.addNewData(data.getList()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_data, "您还没有发布商品", true,
                        null));
                smartRefresh.setEnableLoadMore(false);
            } else {
                //有数据就开启加载更多
                smartRefresh.setEnableLoadMore(true);
            }
        }
        closeRefresh(smartRefresh);
    }

    @Override
    public void isDownGoodsSuccess(String data) {
        showToast("下架成功");
        //下架成功
        mAdapter.remove(selectPosition);
        //没数据了显示无数据页面
        if (StringUtil.isListEmpty(mAdapter.getData())){
            tvToolbarTitle.setText("我的发布");
            mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_data,"您还没有发布商品",false,null));
            smartRefresh.setEnableLoadMore(false);
        }else{
            tvToolbarTitle.setText("我发布的("+mAdapter.getData().size()+")");
        }
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isLoadMore){
            mPage--;
        }
        closeRefresh(smartRefresh);
        showToast(msg);
    }


    //列表适配器
    class MineListAdapter extends BaseRecycleNewAdapter<LikeGoodsBean.ListBean>{

        public MineListAdapter(int layoutResId, @Nullable List<LikeGoodsBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LikeGoodsBean.ListBean item) {
            //商品图片
            ImageView imgGoods = helper.getView(R.id.item_img_commodity);
            GlideUtils.loadRoundCircleImage(mContext, item.getGoods_images(), imgGoods);
            //上架天数
            helper.setText(R.id.item_tv_over_time, DateUtil.getTimeFormatText(new Date(item.getOn_time()*1000))+"发布");
            //商品标题
            helper.setText(R.id.item_tv_title, "商品标题");
            //浏览数 +  收藏数 + 留言数
            helper.setText(R.id.item_tv_number,
                    item.getClick_count() + "次浏览 "
                            + item.getCollect_sum() + "人想买 "
                            + item.getComment_num() + "人留言");
            // 价格
            helper.setText(R.id.item_tv_new_money, item.getShop_price());
            // 原价
            helper.setText(R.id.item_tv_old_money, "原价￥" + item.getMarket_price());
            // 下架商品
            helper.getView(R.id.item_tv_lable_one).setOnClickListener(v -> PopWindowUtil.getInstance().showPopupWindow(mContext, "您确定要下架该商品？", () -> {
                selectPosition = helper.getLayoutPosition();
                downCommodity(String.valueOf(item.getGoods_id()));
            }));
            //编辑
            helper.getView(R.id.item_tv_lable_two).setOnClickListener(v -> {
                //传递 goods_id
                startActivity(new Intent(mContext, PutCommodityActivity.class).putExtra("goods_id",item.getGoods_id()));
            });

        }
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
    protected MinePutPresenter getPresenter() {
        return new MinePutPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_put;
    }
}
