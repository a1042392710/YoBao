package com.jjz.energy.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;

import java.util.List;

/**
 * @Features: 订单状态
 * @author: create by chenhao on 2019/10/10
 */
public class OrderDetailsStatusAdapter extends BaseRecycleNewAdapter<String> {

    //订单状态（0待支付，1待发货,2待收货，3待评价，4交易关闭，5交易完成）

    /**
     * 订单状态
     */
    private int status;

    public OrderDetailsStatusAdapter(int layoutResId, @Nullable List<String> data ,int status) {
        super(layoutResId, data);
        this.status = status;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        //图
        ImageView imgCircle = helper.getView(R.id.item_img_ok );
        //文字
        TextView tvState = helper.getView(R.id.item_tv_state );
        //线
        View line = helper.getView(R.id.item_view_line );

        //根据订单状态显示球的位置 大与等于当前状态时
        if ( status >= helper.getLayoutPosition() ){
            //显示ok球 红线
            line.setBackgroundColor(mContext.getResources().getColor(R.color.red_fe8977));
            imgCircle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_ok));
        } else {
            //显示红球 灰线
            line.setBackgroundColor(mContext.getResources().getColor(R.color.gray_f6));
            imgCircle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_circle_soild_orange));
        }
        //最后一个球隐藏线
        line.setVisibility(helper.getLayoutPosition() == mData.size() - 1 ? View.GONE :
                View.VISIBLE);
        //状态
        helper.setText(R.id.item_tv_state, item);

    }
}
