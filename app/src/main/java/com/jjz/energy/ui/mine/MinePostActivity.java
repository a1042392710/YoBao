package com.jjz.energy.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.ui.community.CommunityDetailActivity;
import com.jjz.energy.util.glide.GlideUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Features: 我的帖子
 * @author: create by chenhao on 2019/8/30
 */
public class MinePostActivity extends BaseActivity {
    @BindView(R.id.rv_mine_post)
    RecyclerView rvMinePost;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_mine_post;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("我的帖子 (50)");
        rvMinePost.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        MinePostAdapter minePostAdapter = new MinePostAdapter(R.layout.item_mine_post, list);
        rvMinePost.setAdapter(minePostAdapter);
        minePostAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext, CommunityDetailActivity.class));
        });
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class MinePostAdapter extends BaseRecycleNewAdapter<String> {

        public MinePostAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imgPhoto = helper.getView(R.id.item_img_photo);
            GlideUtils.loadRoundCircleImage(mContext, "https://timgsa.baidu" +
                    ".com/timg?image&quality=80&size=b9999_10000&sec=1567142575851&di" +
                    "=c9c78b9d69dcf2a535adb77640745abc&imgtype=0&src=http%3A%2F%2Fb.hiphotos" +
                    ".baidu.com%2Fimage%2Fpic%2Fitem%2F0eb30f2442a7d9337119f7dba74bd11372f001e0" +
                    ".jpg", imgPhoto);
        }
    }
}
