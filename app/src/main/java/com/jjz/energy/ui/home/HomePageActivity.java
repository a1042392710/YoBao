package com.jjz.energy.ui.home;

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
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.util.glide.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 个人主页
 * @author: create by chenhao on 2019/8/6
 */
public class HomePageActivity extends BaseActivity {


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

    private String  headImg = "http://n.sinaimg.cn/sinacn/20170814/7484-fyixias0541501.jpg";

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_home_page;
    }

    @Override
    protected void initView() {
        //初始化Tablayou和ViewPager
        initVpAndVp();
        //设置头像
        GlideUtils.loadCircleImage(mContext,headImg,imgHead);
        //设置背景为头像高斯模糊后的图片
        GlideUtils.loadBlurImage(mContext,headImg,llHeader);
    }

    /**
     * 初始化tblayout列表和vp
     */
    private void initVpAndVp() {
        //初始化ViewPager 并与TabLayout绑定
        noScrollVp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(noScrollVp);
    }


    @Override
    public void showLoading() {startProgressDialog();}

    @Override
    public void stopLoading() {stopProgressDialog();}



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
                tvLikeUser.setBackgroundResource(R.drawable.bg_unlike_user);
                tvLikeUser.setText("已关注");
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private String[] title = {"宝贝", "帖子", "评价"};

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
}
