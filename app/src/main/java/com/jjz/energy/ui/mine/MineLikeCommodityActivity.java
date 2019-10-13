package com.jjz.energy.ui.mine;

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
import com.jjz.energy.presenter.mine.MineLikeCommodityPresenter;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.mine.IMineLikeCommodityView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
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
    private int  mPage=1;
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
        smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            getData(true);
        });
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
    }

    //获取收藏列表
    @Override
    public void isSuccess(LikeGoodsBean data) {
        tvToolbarTitle.setText("我收藏的("+data.getList().size()+")");
        if (isLoadMore) {
            //没有更多数据的时候，关闭加载更多
            if (!mAdapter.addNewData(data.getList()))
                smartRefresh.setEnableLoadMore(false);
        } else {
            // 新数据为空时 显示空数据页面
            if (!mAdapter.notifyChangeData(data.getList())){
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_data,"您还没有收藏商品",true,null));
            }else{
                //有数据就开启加载更多
                smartRefresh.setEnableLoadMore(true);
            }        }
        closeRefresh(smartRefresh);
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



    class MineListAdapter extends BaseRecycleNewAdapter<LikeGoodsBean.ListBean>{

        public MineListAdapter(int layoutResId, @Nullable List<LikeGoodsBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LikeGoodsBean.ListBean item) {
            //用户头像
            ImageView item_img_user_head = helper.getView(R.id.item_img_user_head);
            GlideUtils.loadCircleImage(mContext,"",item_img_user_head);
            //商品图
            ImageView item_img_commodity = helper.getView(R.id.item_img_commodity);
            GlideUtils.loadRoundCircleImage(mContext,"",item_img_commodity);
            //发布时间
            helper.setText(R.id.item_tv_time,"");
            //用户昵称
            helper.setText(R.id.item_tv_user_name,"");
            //标题
            helper.setText(R.id.item_tv_title,"");
            //浏览数 +  收藏数 + 留言数
            helper.setText(R.id.item_tv_number,"");

            //价格
            helper.setText(R.id.item_tv_new_money, "");
            //原价
            helper.setText(R.id.item_tv_old_money, "");
            //取消收藏
            helper.getView(R.id.item_tv_lable_one).setOnClickListener(v -> {
                //记录下标
                selectPosition = helper.getLayoutPosition();
                cancleCollect("goods_id");
            });
            //立即购买
            helper.getView(R.id.item_tv_lable_two).setOnClickListener(v -> {

            });

        }
    }
}
