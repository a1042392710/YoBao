package com.jjz.energy.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.entry.home.EntrustListBean;
import com.jjz.energy.ui.home.entrust.EntrustListActivity;
import com.jjz.energy.ui.notice.IMActivity;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.util.system.PopWindowUtil;

import java.util.Date;
import java.util.List;

/**
 * @Features: 委托大厅
 * @author: create by chenhao on 2019/11/25
 */
public class EntrustListAdpter extends BaseRecycleNewAdapter<EntrustListBean.ListBean> {

    private EntrustListActivity mActivity;

    public EntrustListAdpter(int layoutResId, @Nullable List<EntrustListBean.ListBean> data, EntrustListActivity activity) {
        super(layoutResId, data);
        this.mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, EntrustListBean.ListBean item) {
        helper.setText(R.id.item_tv_user_name,item.getNickname());
        helper.setText(R.id.item_tv_content,item.getDemand_content());
        helper.setText(R.id.item_tv_price, "¥"+item.getMoney());
        helper.setText(R.id.item_tv_location, item.getProvince()+" "+ item.getCity()+ " "+item.getDistrict());
        helper.setText(R.id.item_tv_time, DateUtil.getTimeFormatText(new Date(item.getAdd_time()*1000L))+"发布");
        //接受委托
        helper.getView(R.id.item_tv_accept_entrust).setOnClickListener(v -> {
            PopWindowUtil.getInstance().showPopupWindow(mContext, "您确定要接受这笔委托吗？", () -> {
                mActivity.accpetEntrust(item.getOrder_sn(),helper.getLayoutPosition());
            });
        });
        ImageView img = helper.getView(R.id.item_img_user_head);
        GlideUtils.loadRoundCircleImage(mContext, item.getHead_pic(), img);
        //进入聊天
        img.setOnClickListener(v -> {
            //不和自己聊天
            if (UserLoginBiz.getInstance(mContext).readUserInfo().getMobile().equals(item.getMobile())) {
                return;
            }
            mContext.startActivity(new Intent(mContext, IMActivity.class).putExtra("userName",
                    item.getMobile()));
        });


    }
}
