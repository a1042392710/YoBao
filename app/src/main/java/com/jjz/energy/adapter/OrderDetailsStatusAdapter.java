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

    public OrderDetailsStatusAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        //图
        ImageView imgCircle = helper.getView(R.id.item_img_ok );
        //文字
        TextView tvState = helper.getView(R.id.item_tv_state );
        //线
        View line = helper.getView(R.id.item_view_line );

        if (helper.getLayoutPosition()==mData.size()-2){
            line.setBackgroundColor(mContext.getResources().getColor(R.color.gray_f6));
        }else{
            line.setBackgroundColor(mContext.getResources().getColor(R.color.red_fe8977));
        }

        //最后一个球隐藏线
        if (helper.getLayoutPosition()==mData.size()-1){
            imgCircle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_circle_soild_orange));
           line.setVisibility(View.GONE);
        }else{
            imgCircle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_ok));
            line.setVisibility(View.VISIBLE);
        }

    }
}
