package com.jjz.energy.ui.home.jiusu;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.jiusu.ShareInfoBean;
import com.jjz.energy.presenter.jiusu.SharePresenter;
import com.jjz.energy.util.ShareUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IShareView;
import com.jjz.energy.zxing.encode.CodeCreator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features: 分享邀请
 * @author: create by chenhao on 2019/4/3
 */
public class JiuSuShareActivity extends BaseActivity<SharePresenter> implements IShareView {

    @BindView(R.id.view)
    View view;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_tj_code)
    TextView tvTjCode;
    @BindView(R.id.img_vcode)
    ImageView imgVcode;
    @BindView(R.id.ll_share_content)
    LinearLayout llShareContent;
    @BindView(R.id.img_text)
    ImageView imgText;
    @BindView(R.id.tv_text_one)
    TextView tvTextOne;


    private ShareInfoBean mShareInfo;

    /**
     * 分享工具类
     */
    private ShareUtil mShareUtil;

    @Override
    protected SharePresenter getPresenter() {
        return new SharePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_jiusu_share;
    }


    @Override
    protected void initView() {
        mPresenter.getShareInfo(PacketUtil.getRequestPacket(null));
        //初始化分享内容
        mShareInfo = new ShareInfoBean();
        mShareInfo.setRand_title("久速");
        mShareInfo.setRand_desc("我们专注新能源");
        mShareInfo.setShare_url(Constant.SANJIU_WEB);
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @OnClick({R.id.tv_share, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //分享
            case R.id.tv_share:
                //调用分享
                if (mShareUtil == null) {
                    //初始化分享
                    mShareUtil = new ShareUtil(this);
                }
                mShareUtil.showSharePop(mShareInfo);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void isSuccess(ShareInfoBean data) {
        mShareInfo = data;
        //生成二维码
        Bitmap bitmap = CodeCreator.createQRCode(data.getShare_url(), 400, 400, null);
        if (bitmap != null) {
            imgVcode.setImageBitmap(bitmap);
        }
        if (!StringUtil.isEmpty(mShareInfo.getTj_code())) {
            tvTjCode.setText(mShareInfo.getTj_code());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShareUtil != null) {
            mShareUtil.destory();
        }
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }

}
