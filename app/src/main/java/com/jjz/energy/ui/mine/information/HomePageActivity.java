package com.jjz.energy.ui.mine.information;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.UserPageInfo;
import com.jjz.energy.presenter.mine.HomePagePresenter;
import com.jjz.energy.ui.community.PostFragment;
import com.jjz.energy.ui.home.commodity.CommentFragment;
import com.jjz.energy.ui.home.commodity.CommodityFragment;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.AesUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.view.mine.IHomePageView;

import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 个人主页
 * @author: create by chenhao on 2019/8/6
 */
public class HomePageActivity extends BaseActivity<HomePagePresenter> implements IHomePageView {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    ImageView tvToolbarRight;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_time)
    TextView tvUserTime;
    @BindView(R.id.tv_user_like_and_fans)
    TextView tvUserLikeAndFans;
    @BindView(R.id.tv_user_desc)
    TextView tvUserDesc;
    @BindView(R.id.tv_like_user)
    TextView tvLikeUser;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.no_scroll_vp)
    ViewPager noScrollVp;

    /**
     * 用户Id
     */
    private int user_id;
    /**
     * 当前关注状态
     */
    private int isFocus;

    @Override
    protected HomePagePresenter getPresenter() {
        return new HomePagePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_home_page;
    }

    @Override
    protected void initView() {
        //用户id
        user_id = getIntent().getIntExtra("user_id",-1);
        /**
         * 如果用户是本人
         */
        if (UserLoginBiz.getInstance(mContext).readUserInfo().getUser_id()==user_id){
            tvToolbarTitle.setText("我的主页");
            tvLikeUser.setVisibility(View.GONE);
        }
        //初始化Tablayou和ViewPager
        initVpAndVp();
        //获取用户信息
        mPresenter.getUserPageInfo(PacketUtil.getRequestPacket(Utils.stringToMap("user_id", AesUtils.encrypt(String.valueOf(user_id), AesUtils.KEY, AesUtils.IV))));
    }

    /**
     * 初始化tblayout列表和vp
     */
    private void initVpAndVp() {
        //初始化ViewPager 并与TabLayout绑定
        noScrollVp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(noScrollVp);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void isGetInfoSuccess(UserPageInfo data) {
        isFocus = data.getIs_focus();
        //设置头像
        GlideUtils.loadCircleImage(mContext,data.getHead_pic(),imgHead);
        //设置背景为头像高斯模糊后的图片
        GlideUtils.loadBlurImage(mContext,data.getHead_pic(),llHeader);
        //用户昵称
        tvUserName.setText(data.getNickname());
        //粉丝数 关注数
        tvUserLikeAndFans.setText("关注 "+data.getFocus_num()+" 粉丝 "+data.getFans_num());
        //上次上线时间
        tvUserTime.setText(DateUtil.getTimeFormatText(new Date(data.getLast_time()*1000))+"来过");
        //用户简介
        tvUserDesc.setText(data.getDesc());
        //是否已经关注了
        if (data.getIs_focus()==1){
            tvLikeUser.setBackgroundResource(R.drawable.bg_unlike_user);
            tvLikeUser.setText("已关注");
        }
    }
    //关注或取消关注的操作成功
    @Override
    public void isFocusUserSuccess(String data) {
        //将关注状态设置为成功后的值 并设置背景和文字
        if (isFocus==1){
            isFocus=0;
            tvLikeUser.setBackgroundResource(R.drawable.bg_like_user);
            tvLikeUser.setText("关注");
        }else{
            isFocus=1;
            tvLikeUser.setBackgroundResource(R.drawable.bg_unlike_user);
            tvLikeUser.setText("已关注");
        }
        showToast("操作成功");
    }

    @OnClick({R.id.ll_toolbar_left, R.id.tv_toolbar_right, R.id.tv_like_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //分享
            case R.id.tv_toolbar_right:
                break;
            //关注
            case R.id.tv_like_user:
                HashMap<String,String> map = new HashMap<>();
                map.put("user_id",AesUtils.encrypt(String.valueOf(user_id), AesUtils.KEY, AesUtils.IV));
                map.put("act",isFocus==0?"focus":"cancel");
                mPresenter.setFocusUser(PacketUtil.getRequestPacket(map));
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private String[] title = {"商品", "帖子", "评价"};

        private Fragment[] mFragments = new Fragment[]{new CommodityFragment(), new PostFragment(),
                new CommentFragment()};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) { return mFragments[position];}

        @Override
        public int getCount() { return mFragments.length;}

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        showToast(msg);
    }

    @Override
    public void showLoading() {startProgressDialog();}

    @Override
    public void stopLoading() {stopProgressDialog();}


}
