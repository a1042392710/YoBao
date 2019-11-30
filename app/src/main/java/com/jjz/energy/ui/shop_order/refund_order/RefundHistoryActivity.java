package com.jjz.energy.ui.shop_order.refund_order;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseApplication;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.mine.RefundHistroyBean;
import com.jjz.energy.presenter.order.RefundPresenter;
import com.jjz.energy.ui.ImagePagerActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.order.IRefundView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 协商历史
 * @author: create by chenhao on 2019/11/6
 */
public class RefundHistoryActivity extends BaseActivity<RefundPresenter> implements IRefundView {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;

    private HistoryAdapter mAdapter;
    /**
     * 售后id
     */
    private String return_id;

    @Override
    protected RefundPresenter getPresenter() {
        return new RefundPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_refund_history;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("协商历史");
        return_id = getIntent().getStringExtra(Constant.RETURN_ID);
        rvHistory.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter  = new HistoryAdapter(R.layout.item_refund_history,new ArrayList<>());
        rvHistory.setAdapter(mAdapter);
        mPresenter.getRefundHistoryList(PacketUtil.getRequestPacket(Utils.stringToMap(Constant.RETURN_ID,return_id)));
    }

    @Override
    public void isGetHistorySuccess(RefundHistroyBean data) {
        if (!mAdapter.notifyChangeData(data.getList())){
            mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data,"暂无协商历史",false,null));
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

    @OnClick(R.id.ll_toolbar_left)
    public void onViewClicked() {
            finish();
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }

    /**
     * 协商历史
     */
    class HistoryAdapter extends BaseRecycleNewAdapter<RefundHistroyBean.ListBean> {

        public HistoryAdapter(int layoutResId, @Nullable List<RefundHistroyBean.ListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, RefundHistroyBean.ListBean item) {
            ImageView img = helper.getView(R.id.item_img_user_head);
            GlideUtils.loadHead(mContext,item.getHead_pic(),img);
            helper.setText(R.id.item_tv_user_name,item.getNickname());
            helper.setText(R.id.item_tv_time, DateUtil.longToDate(item.getLog_time(),null));
            helper.setText(R.id.item_tv_content, item.getAction_note());
            if (!StringUtil.isEmpty(item.getImgs())){
                List<String> grid_list = Arrays.asList(item.getImgs().split(","));
                RecyclerView recyclerView = helper.getView(R.id.item_rv_grid_photo);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
                GridAdapter gridAdapter = new GridAdapter(R.layout.item_grid_history_photo,grid_list);
                recyclerView.setAdapter(gridAdapter);
                //查看大图
                gridAdapter.setOnItemClickListener((adapter, view, position) -> {
                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(),
                            view.getMeasuredHeight());
                    ImagePagerActivity.startImagePagerActivity(BaseApplication.AppContext, grid_list, position, imageSize);
                });
            }
        }
    }

    class GridAdapter extends BaseRecycleNewAdapter<String>{

        public GridAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView img = helper.getView(R.id.item_img_photo);
            GlideUtils.loadRoundCircleImage(mContext,item,img);
        }
    }
}
