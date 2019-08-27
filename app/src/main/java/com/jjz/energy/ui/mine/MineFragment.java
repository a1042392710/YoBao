package com.jjz.energy.ui.mine;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 个人中心
 * @author: create by chenhao on 2019/7/15
 */
public class MineFragment extends BaseFragment {
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

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initRv();
        GlideUtils.loadCircleImage(mContext,"http://b-ssl.duitang.com/uploads/item/201407/22/20140722182918_tV8aa.jpeg",imgHead);

    }

    /**
     * 初始化网格数据
     */
    private void initRv() {
        rvMine.setLayoutManager(new GridLayoutManager(mContext,4));
        List<MineBean> list = new ArrayList<>();
        list.add(new MineBean("我的钱包",1));
        list.add(new MineBean("我的会员",1));
        list.add(new MineBean("我的足迹",1));
        list.add(new MineBean("我的保险",1));
        list.add(new MineBean("我的教育",1));
        list.add(new MineBean("我的慈善",1));
        list.add(new MineBean("我的评价",1));
        list.add(new MineBean("我的客服",1));
        MineAdapter mineAdapter = new MineAdapter(R.layout.item_mine_type,list);
        rvMine.setAdapter(mineAdapter);

    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
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
                startActivity(new Intent(mContext,MineInfomationActivity.class));
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

    class MineBean {
        private String title;
        private int img;

        public MineBean(String title, int img) {
            this.title = title;
            this.img = img;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }
    }

    class MineAdapter extends BaseRecycleNewAdapter<MineBean>{

        public MineAdapter(int layoutResId, @Nullable List<MineBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MineBean item) {


        }
    }

}
