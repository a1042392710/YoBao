package com.jjz.energy.ui.notice;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.ocr.sdk.utils.LogUtil;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseFragment;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * @Features: 消息
 * @author: create by chenhao on 2019/7/23
 */
public class NoticeFragment extends BaseFragment {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.rv_new_notice)
    RecyclerView rvNewNotice;
    @BindView(R.id.rv_old_notice)
    RecyclerView rvOldNotice;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("消息");
        llToolbarLeft.setVisibility(View.INVISIBLE);
        //新旧消息列表
        initRv();
    }

    /**
     * 初始化
     */
    private void initRv() {
        //禁用滑动
        rvNewNotice.setLayoutManager(new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvOldNotice.setLayoutManager(new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        //  模拟数据
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        NoticeAdapter noticeAdapter = new NoticeAdapter(R.layout.item_notice,list);
        rvNewNotice.setAdapter(noticeAdapter);
        rvOldNotice.setAdapter(noticeAdapter);
        //消息点击进聊天
        noticeAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivity(new Intent(mContext,IMActivity.class));
        });

        //登录 方小杰
        JMessageClient.login("cds123", "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i==0){
                    LogUtil.e("'推送","登录成功");
                }

            }
        });
    }

    /**
     * 消息
     */
    class NoticeAdapter extends BaseRecycleNewAdapter<String>{

        public NoticeAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {


        }
    }



    @Override
    protected int layoutId() {
        return R.layout.fragment_notice;
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
