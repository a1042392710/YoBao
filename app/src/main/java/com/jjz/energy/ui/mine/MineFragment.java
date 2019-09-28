package com.jjz.energy.ui.mine;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.adapter.MineAdapter;
import com.jjz.energy.base.BaseLazyFragment;
import com.jjz.energy.entry.MineBean;
import com.jjz.energy.entry.MineInfoBean;
import com.jjz.energy.presenter.mine.MinePresenter;
import com.jjz.energy.ui.mine.information.MineInfomationActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.util.system.SpUtil;
import com.jjz.energy.view.mine.IMineView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 个人中心
 * @author: create by chenhao on 2019/7/15
 */
public class MineFragment  extends BaseLazyFragment<MinePresenter> implements IMineView {

    @BindView(R.id.view_title)
    View viewTitle;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.ll_mine_release)
    LinearLayout llMineRelease;
    @BindView(R.id.ll_mine_seller)
    LinearLayout llMineSeller;
    @BindView(R.id.ll_mine_buyer)
    LinearLayout llMineBuyer;
    @BindView(R.id.ll_mine_like)
    LinearLayout llMineLike;
    @BindView(R.id.rv_mine)
    RecyclerView rvMine;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_about_us)
    TextView tvAboutUs;
    @BindView(R.id.tv_fans_sum)
    TextView tvFansSum;
    @BindView(R.id.tv_like_sum)
    TextView tvLikeSum;
    /**
     * 我的菜单列表数据
     */
    private List<MineBean> mList;


    @Override
    protected void initView() {
        initRv();
    }

    /**
     * 获取用户数据成功
     */
    @Override
    public void isGetInfoSuccess(MineInfoBean loginBean) {

        //推送公告
        String push_message = loginBean.getPush_message();
        //显示文本
        if (!StringUtil.isEmpty(push_message) && !push_message.equals(SpUtil.init(mContext).readString("push_message"))){
            PopWindowUtil.getInstance().showPopupWindow(mContext, push_message, () -> {});
            SpUtil.init(mContext).commit("push_message",push_message);
        }

        //头像
        GlideUtils.loadCircleImage(mContext, loginBean.getHead_pic(), imgHead);
        //昵称
        tvNickName.setText(loginBean.getNickname());
        //关注数量和粉丝数量
        tvFansSum.setText("粉丝:100万");
        tvLikeSum.setText("关注:50");
    }



    /**
     * 初始化我的菜单网格数据
     */
    private void initRv() {
        rvMine.setLayoutManager(new GridLayoutManager(mContext, 4));
        mList = new ArrayList<>();
        mList.add(new MineBean("我的帖子", R.mipmap.ic_mine_post));
        mList.add(new MineBean("我的公益", R.mipmap.ic_mine_charity));
        mList.add(new MineBean("我的物流", R.mipmap.ic_mine_logistics));
        mList.add(new MineBean("我的保险", R.mipmap.ic_mine_insurance));
        mList.add(new MineBean("我的养老", R.mipmap.ic_mine_pension));
        mList.add(new MineBean("我的教育",R.mipmap.ic_mine_education));
        mList.add(new MineBean("我的评价",R.mipmap.ic_mine_comment));
        mList.add(new MineBean("我的积分",R.mipmap.ic_mine_integral));
        MineAdapter mineAdapter = new MineAdapter(R.layout.item_mine_type,mList);
        rvMine.setAdapter(mineAdapter);
        mineAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position){
                //我的帖子
                case 0:
                    startActivity(new Intent(mContext,MinePostActivity.class));
                    break;
                    //我的物流
                case 1:
                    break;
                    //我的保险
                case 2:
                    break;
                    //我的养老
                case 3:
                    break;
                    //我的公益
                case 4:
                    break;
                    //我的教育
                case 5:
                    break;
                    //我的评价
                case 6:
                    break;
                    //我的客服
                case 7:
                    break;
            }
        });

    }

    @OnClick({R.id.img_setting, R.id.ll_mine_release, R.id.ll_mine_seller, R.id.ll_mine_buyer,
            R.id.ll_mine_like, R.id.tv_feedback, R.id.tv_about_us ,R.id.img_head  ,R.id.tv_like_sum ,R.id.tv_fans_sum  })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //设置
            case R.id.img_setting:
                startActivity(new Intent(mContext,MineSettingActivity.class));
                break;
            //头像
            case R.id.img_head:
                startActivity(new Intent(mContext, MineInfomationActivity.class));
                break;
                //我发布的
            case R.id.ll_mine_release:
                startActivity(new Intent(mContext,MinePutActivity.class));
                break;
                //我卖出的
            case R.id.ll_mine_seller:
                startActivity(new Intent(mContext,MineSellerActivity.class));
                break;
                //我买到的
            case R.id.ll_mine_buyer:
                startActivity(new Intent(mContext,MineBuyerActivity.class));
                break;
                //我收藏的
            case R.id.ll_mine_like:
                startActivity(new Intent(mContext,MineLikeCommodityActivity.class));
                break;
                //我关注的人
            case R.id.tv_like_sum:
                startActivity(new Intent(mContext,MineFansAndLikeActivity.class).putExtra("index",0));
                break;
                //我的粉丝
            case R.id.tv_fans_sum:
                startActivity(new Intent(mContext,MineFansAndLikeActivity.class).putExtra("index",1));
                break;
                //意见反馈
            case R.id.tv_feedback:
                startActivity(new Intent(mContext,FeedBackActivity.class));
                break;
                //关于我们
            case R.id.tv_about_us:
                startActivity(new Intent(mContext,AboutUsActivity.class));
                break;
        }
    }
    //=========================================================================== 方法重写和生命周期

    @Override
    public void isFail(String msg ,boolean isNetAndServiceError) {
        showToast(msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        //刷新数据
        if (mPresenter == null) {
            mPresenter = new MinePresenter(this);
        }
        if (UserLoginBiz.getInstance(mContext).detectUserLoginStatus()){
            //刷新当前页面数据
            mPresenter.getUserInfo(PacketUtil.getRequestPacket(null));
        }

    }

    @Override
    protected MinePresenter getPresenter() {
        return new MinePresenter(this);
    }


    @Override
    protected int layoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void showLoading() {}

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

}
