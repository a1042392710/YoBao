package com.jjz.energy.ui.mine;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.BaseWebActivity;
import com.jjz.energy.util.PopWindowUtil;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Features:
 * @author: create by chenhao on 2019/8/27
 */
@RuntimePermissions
public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_user_protocol)
    LinearLayout llUserProtocol;
    @BindView(R.id.ll_jjz_official_website)
    LinearLayout llJjzOfficialWebsite;
    @BindView(R.id.tv_consumer_hotline)
    TextView tvConsumerHotline;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_about_us;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("关于我们");
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }



    @OnClick({R.id.ll_toolbar_left, R.id.tv_version, R.id.ll_user_protocol,
            R.id.ll_jjz_official_website, R.id.tv_consumer_hotline})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
                //检查更新
            case R.id.tv_version:
                break;
                //用户协议
            case R.id.ll_user_protocol:
                startActivity(new Intent(mContext, BaseWebActivity.class).putExtra("title","用户协议").putExtra("url","www.baidu.com"));
                break;
                //金玖洲官网
            case R.id.ll_jjz_official_website:
                startActivity(new Intent(mContext, BaseWebActivity.class).putExtra("title","金玖洲新能源有限公司").putExtra("url","http://www.jjznewenergy.com"));
                break;
                //客服电话
            case R.id.tv_consumer_hotline:
                AboutUsActivityPermissionsDispatcher.callWithCheck(this);
                break;
        }
    }

    // =========================================== 权限申请


    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void call() {
        // 拨号：激活系统的拨号组件
        Intent intent = new Intent(); // 意图对象：动作 + 数据
        intent.setAction(Intent.ACTION_CALL); // 设置动作

        Uri data = Uri.parse("tel:" + "4001070400"); // 设置数据
        intent.setData(data);
        startActivity(intent); // 激活Activity组件
    }
    //给用户解释为什么要申请权限
    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showCallPhone(final PermissionRequest request) {
        //唤起打电话权限
        PopWindowUtil.getInstance().showPopupWindow(mContext, "没有电话权限可不能打电话哦", () -> {
            request.proceed();//继续执行请求
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AboutUsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
