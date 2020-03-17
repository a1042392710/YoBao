package com.jjz.energy.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.home.LogisticsBean;
import com.jjz.energy.ui.home.logistics.MineLogisticsActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;

import java.util.Date;
import java.util.List;

/**
 * 物流列表
 */
public class MineLogisticsAdapter extends BaseRecycleNewAdapter<LogisticsBean> {

    private MineLogisticsActivity mActivity;

    public MineLogisticsAdapter(int layoutResId, @Nullable List<LogisticsBean> data, MineLogisticsActivity activity) {
        super(layoutResId, data);
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, LogisticsBean item) {
        //装货时间
        helper.setText(R.id.item_tv_time_info, DateUtil.longToDate(item.getStart_time(), "MM月dd日 " +
                "HH时mm分"));
        //发布时间
        helper.setText(R.id.item_tv_time,
                DateUtil.getTimeFormatText(new Date(item.getAdd_time() * 1000)) + "发布更新");
        //起点
        helper.setText(R.id.item_tv_first, item.getStart_address());
        //终点
        helper.setText(R.id.item_tv_end, item.getEnd_address());
        //期望价格
        helper.setText(R.id.item_tv_money, "￥" + item.getPrice());
        //体积
        String str = !StringUtil.isEmpty(item.getGoods_size()) ? "|" + item.getGoods_size() : "";
        helper.setText(R.id.item_tv_weight, item.getWeight() + str);

        helper.getView(R.id.item_tv_cancle).setOnClickListener(v -> {
            mActivity.cancleLogistics(item.getId(),helper.getLayoutPosition());
        });
    }
}