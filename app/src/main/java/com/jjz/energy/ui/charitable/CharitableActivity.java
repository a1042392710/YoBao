package com.jjz.energy.ui.charitable;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.CharitableAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 公益慈善主页
 * @author: create by chenhao on 2019/7/1
 */
public class CharitableActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.img_notice_head)
    ImageView imgNoticeHead;
    @BindView(R.id.tv_notice_content)
    TextView tvNoticeContent;
    @BindView(R.id.rv_charitable)
    RecyclerView rvCharitable;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_charitable;
    }

    @Override
    protected void initView() {
        //初始化列表
        initRv();
    }
    /**
     * 初始化商品分类列表
     */
    private void initRv() {
        GlideUtils.loadRoundCircleImage(mContext,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561983611774&di=d005454687e7f03d13e900f2043de152&imgtype=0&src=http%3A%2F%2Fimg0.pconline.com.cn%2Fpconline%2F1602%2F25%2F7567433_08_thumb.jpg",imgBanner);
        GlideUtils.loadCircleImage(mContext,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561983649260&di=5ee361749d8adb8a75b6abf34c24db07&imgtype=0&src=http%3A%2F%2Fp2.pstatp.com%2Flarge%2F1257%2F692371963",imgNoticeHead);
        rvCharitable.setLayoutManager(new GridLayoutManager(mContext, 2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
//        取消嵌套rv 的焦点获取，使其不自动滚动到底部
//        rvType.setFocusableInTouchMode(false);
//        rvType.requestFocus();
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        CharitableAdapter mCharitableAdapter=
                new CharitableAdapter(R.layout.item_charitable_grid, list);
        mCharitableAdapter.setOnItemClickListener((adapter, view, position) -> startActivity(new Intent(mContext,CharitableDetailActivity.class)));
        rvCharitable.setAdapter(mCharitableAdapter);
    }
    @Override
    public void showLoading() {
    startProgressDialog();
    }

    @Override
    public void stopLoading() {
    stopProgressDialog();
    }



    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
