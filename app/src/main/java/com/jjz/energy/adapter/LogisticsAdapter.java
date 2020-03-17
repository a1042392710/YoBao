package com.jjz.energy.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.home.LogisticsBean;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.system.SpUtil;

import java.util.Date;
import java.util.List;

/**
 * 物流列表
 */
public class LogisticsAdapter extends BaseRecycleNewAdapter<LogisticsBean> {

    public LogisticsAdapter(int layoutResId, @Nullable List<LogisticsBean> data) {
        super(layoutResId, data);
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

        if (!StringUtil.isEmpty(SpUtil.init(mContext).readString(Constant.LOCATION_LAT))){
            //获取存在本地的坐标信息
            double lat = Double.parseDouble(SpUtil.init(mContext).readString(Constant.LOCATION_LAT));
            double lng = Double.parseDouble(SpUtil.init(mContext).readString(Constant.LOCATION_LNG));
            //距离
            helper.setText(R.id.item_tv_distance, "距您" + Utils.getDistance(lng, lat,
                    item.getEnd_lng(), item.getEnd_lat()) + "公里");
        }
        //联系人
        helper.setText(R.id.item_tv_people, item.getShipper());
        //体积
        String str = !StringUtil.isEmpty(item.getGoods_size()) ? "|" + item.getGoods_size() : "";
        helper.setText(R.id.item_tv_weight, item.getWeight() + str);


    }
}