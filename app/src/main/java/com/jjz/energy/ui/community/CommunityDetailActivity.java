package com.jjz.energy.ui.community;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.adapter.PhotoAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.ui.home.HomePageActivity;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 帖子详情
 * @author: create by chenhao on 2019/8/26
 */
public class CommunityDetailActivity extends BaseActivity {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_user_head)
    ImageView imgUserHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_put_time)
    TextView tvPutTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.img_one_photo)
    ImageView imgOnePhoto;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.tv_put_comment)
    TextView tvPutComment;
    private List<String> list;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_community_detail;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("南昌彭于晏");
        initRv();
    }

    /**
     * 初始化列表
     */
    private void initRv() {
        list = new ArrayList<>();
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566812800708&di=f1b73d47010fc5d0b17d52b7843d1ee1&imgtype=0&src=http%3A%2F%2Fimg4.cache.netease.com%2Fphoto%2F0001%2F2010-04-17%2F64EFS71V05RQ0001.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566812800708&di=f1b73d47010fc5d0b17d52b7843d1ee1&imgtype=0&src=http%3A%2F%2Fimg4.cache.netease.com%2Fphoto%2F0001%2F2010-04-17%2F64EFS71V05RQ0001.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566812800708&di=f1b73d47010fc5d0b17d52b7843d1ee1&imgtype=0&src=http%3A%2F%2Fimg4.cache.netease.com%2Fphoto%2F0001%2F2010-04-17%2F64EFS71V05RQ0001.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566812800708&di=f1b73d47010fc5d0b17d52b7843d1ee1&imgtype=0&src=http%3A%2F%2Fimg4.cache.netease.com%2Fphoto%2F0001%2F2010-04-17%2F64EFS71V05RQ0001.jpg");

        rvPhoto.setLayoutManager(new GridLayoutManager(mContext,3));
        rvPhoto.setAdapter(new PhotoAdapter(R.layout.item_photo,list));
        //禁止滑动
        rvComment.setLayoutManager(new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvComment.setAdapter(new CommunityCommentAdapter(R.layout.item_community_comment,list));

    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @OnClick({R.id.ll_toolbar_left, R.id.img_user_head, R.id.tv_put_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //个人主页
            case R.id.img_user_head:
                startActivity(new Intent(mContext, HomePageActivity.class));
                break;
                //发布评论
            case R.id.tv_put_comment:
                break;
        }
    }

    class CommunityCommentAdapter  extends BaseRecycleNewAdapter<String> {
        public CommunityCommentAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView imgHead = helper.getView(R.id.item_img_head);
            GlideUtils.loadCircleImage(mContext,"http://b-ssl.duitang.com/uploads/item/201711/10/20171110225150_ym2jw.jpeg",imgHead);
        }
    }

}
