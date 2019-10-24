package com.jjz.energy.ui.mine.shop_order;

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
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 物流详情
 * @author: create by chenhao on 2019/10/22
 */
public class ExpressDetailsActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_commodity)
    ImageView imgCommodity;
    @BindView(R.id.rv_express)
    RecyclerView rvExpress;

    private ExpressAdapter mAdapter;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_express_details;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("物流详情");

        rvExpress.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        mAdapter = new ExpressAdapter(R.layout.item_express_details, list);
        rvExpress.setAdapter(mAdapter);

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


    /**
     * 物流信息
     */
    class ExpressAdapter extends BaseRecycleNewAdapter<String> {

        public ExpressAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            //圆球
            ImageView item_img_cricle = helper.getView(R.id.item_img_cricle);
            //竖线
            View item_view_line = helper.getView(R.id.item_view_line);
            //标题
            TextView item_tv_title = helper.getView(R.id.item_tv_title);
            //时间
            TextView item_tv_time = helper.getView(R.id.item_tv_time);
            //商品图片
            GlideUtils.loadRoundCircleImage(mContext, "https://timgsa.baidu" +
                            ".com/timg?image&quality=80&size=b9999_10000&sec=1571742620248&di" +
                            "=f6b62a9483c0af3a988127dee9018cbf&imgtype=0&src=http%3A%2F%2Fn" +
                            ".sinaimg" +
                            ".cn%2Ftranslate%2F533%2Fw800h533%2F20180810%2FzRtQ-hhnunsq7211829.jpg",
                    item_img_cricle);


            //第一个圆球和字是红色
            if (helper.getLayoutPosition() == 0) {
                item_tv_title.setTextColor(mContext.getResources().getColor(R.color.red_fe8977));
                item_img_cricle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_bg_cricle_orange));
            } else {
                item_tv_title.setTextColor(mContext.getResources().getColor(R.color.text_black88));
                item_img_cricle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_bg_cricle_gray));
            }
            //最后一条隐藏线
            item_view_line.setVisibility(helper.getLayoutPosition() == mData.size() - 1 ?
                    View.GONE : View.VISIBLE);

        }
    }

}
