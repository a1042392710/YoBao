package com.jjz.energy.ui.home.commodity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jjz.energy.R;
import com.jjz.energy.adapter.HomePageCommentAdapter;
import com.jjz.energy.base.BaseLazyFragment;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.commodity.HomePageCommentBean;
import com.jjz.energy.presenter.mine.HomePagePresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.AesUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.mine.IHomePageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * @Features: 评价列表
 * @author: create by chenhao on 2019/8/6
 */
public class HomePageCommentFragment extends BaseLazyFragment<HomePagePresenter> implements IHomePageView {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.rb_comment_all)
    RadioButton rbCommentAll;
    @BindView(R.id.rb_comment_good)
    RadioButton rbCommentGood;
    @BindView(R.id.rb_comment_have_photo)
    RadioButton rbCommentHavePhoto;
    @BindView(R.id.rg_comment)
    RadioGroup rgComment;


    private HomePageCommentAdapter mAdapter;

    /**
     * 页码
     */
    private int mPage = 1;

    /*
     * 是否加载更多
     */
    private boolean isLoadMore;

    /**
     * 用户id
     */
    private int user_id;
    /**
     * 筛选类型 0 全部  1 很棒    2 有图
     */
    private int filter_type = 0;

    @Override
    protected void initView() {
        //用户id
        Bundle bundle = getArguments();
        user_id = bundle.getInt("user_id");
        initRvAndRefresh();
        getData(false);
    }

    /**
     * 初始化监听
     */
    private void initRvAndRefresh() {
        //禁止刷新
        smartRefresh.setEnableRefresh(false);
        //初始化 rv
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new HomePageCommentAdapter(R.layout.item_homepage_comment,
                new ArrayList<>());
        rvList.setAdapter(mAdapter);
        //上拉加载
        smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            mPage++;
            //获取指定类别的商品
            getData(true);
        });
        //选中的时候
        rgComment.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId==R.id.rb_comment_all){
                filter_type=0;
            }else if (checkedId==R.id.rb_comment_good){
                filter_type=1;
            }else if (checkedId==R.id.rb_comment_have_photo){
                filter_type=2;
            }
            mPage=1;
            getData(false);
        });
    }

    /**
     * 获取评价列表
     *
     * @param isLoadMore 是否加载更多
     */
    private void getData(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        HashMap<String, String> map = new HashMap<>();
        map.put("page", mPage + "");
        map.put(Constant.USER_ID, AesUtils.encrypt(String.valueOf(user_id), AesUtils.KEY, AesUtils.IV));
        if (filter_type==1){
            //查询很棒的评价
            map.put("start","3");
        }else if (filter_type==2){
            //查询有图的评价
            map.put("is_set_img","1");
        }
        mPresenter.getUserComments(PacketUtil.getRequestPacket(map), isLoadMore);
    }

    @Override
    public void isGetUserComments(HomePageCommentBean data) {
        //展示数量
        rbCommentAll.setText("全部 "+ data.getTotal_num());
        rbCommentGood.setText("很棒 "+ data.getGood_num());
        rbCommentHavePhoto.setText("有图 "+ data.getHave_img_num());
        if (!StringUtil.isListEmpty(data.getList()) && data.getList().size() > 8) {
            smartRefresh.setEnableLoadMore(true);     //有够长的数据就开启加载更多
        } else {
            smartRefresh.setEnableLoadMore(false);     //否则关闭
        }
        //加载更多
        if (isLoadMore) {
            mAdapter.addNewData(data.getList());
        } else {
            //刷新数据，无数据显示空页面
            if (!mAdapter.notifyChangeData(data.getList())) {
                mAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data, "还没有对你的评论", false,null));
            }
        }

        closeRefresh(smartRefresh);
//        stopLoading();
    }

    @Override
    public void isFail(String msg, boolean isNetAndServiceError) {
        //请求失败时页码归位
        if (isLoadMore) {
            mPage--;
        }
        closeRefresh(smartRefresh);
        showToast(msg);
    }

    @Override
    protected HomePagePresenter getPresenter() {
        return new HomePagePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_homepage_comment_list;
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

}
