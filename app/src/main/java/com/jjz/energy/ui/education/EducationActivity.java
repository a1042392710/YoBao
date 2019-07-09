package com.jjz.energy.ui.education;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.adapter.CharitableAdapter;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 教育主页
 * @author: create by chenhao on 2019/7/1
 */
public class EducationActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.img_menu_one)
    ImageView imgMenuOne;
    @BindView(R.id.img_menu_two)
    ImageView imgMenuTwo;
    @BindView(R.id.img_menu_three)
    ImageView imgMenuThree;
    @BindView(R.id.rv_education)
    RecyclerView rvEducation;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_education;
    }

    @Override
    protected void initView() {
        //初始化列表
        initRv();
    }

    /**
     * 初始化商品分类列表
     */
    private void initRv() {
        GlideUtils.loadRoundCircleImage(mContext, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562647756133&di=0f48dc26b04bc3594ec3506917c9cc77&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F12%2F45%2F61%2F60bOOOPIC5e.jpg", imgBanner);

        rvEducation.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
//        取消嵌套rv 的焦点获取，使其不自动滚动到底部
//        rvType.setFocusableInTouchMode(false);
//        rvType.requestFocus();
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        EducationAdapter mCharitableAdapter =
                new EducationAdapter(R.layout.item_education, list);
        rvEducation.setAdapter(mCharitableAdapter);
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    /**
     * @Features: 教育
     * @author: create by chenhao on 2019/6/18
     */
    public class EducationAdapter extends BaseRecycleNewAdapter<String> {
        public EducationAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView img = helper.getView(R.id.item_img_photo);
            GlideUtils.loadRoundCircleImage(mContext,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562647756131&di=48d1636dc7eb1071ed882d301be1b160&imgtype=0&src=http%3A%2F%2Fs3.sinaimg.cn%2Fmw690%2F003Z9L7hzy6SKtkTkR492%26690",img);
        }
    }
}
