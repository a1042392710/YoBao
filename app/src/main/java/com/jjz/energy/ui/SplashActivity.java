package com.jjz.energy.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author chenhao 2018/9/26
 * @function 欢迎页 顺便获取权限
 */
@RuntimePermissions
public class SplashActivity extends BaseActivity {


    @BindView(R.id.img_splash)
    ImageView imgSplash;

    @Override
    protected int layoutId() {
        return R.layout.act_splash;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }


    @Override
    protected void initView() {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext).load(R.mipmap.ic_splash).apply(options).into(imgSplash);
    }

    //*****************************************************************  请求权限

    //获取多个权限  必须要的方法  (存储，电话，定位)
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION})
    public void getPermissions() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //跳转首页
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    //给用户解释要请求什么权限，为什么需要此权限 ( 多个 )  非必须的方法
    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION})
    void showRationale(final PermissionRequest request) {
        showToastDialog(request);
    }

    private void showToastDialog(final PermissionRequest request) {

        View view = View.inflate(mContext, R.layout.item_pop_defult_view, null);
        TextView pop_tv_message = view.findViewById(R.id.pop_tv_message);
        TextView pop_tv_ok = view.findViewById(R.id.pop_tv_ok);
        pop_tv_message.setText(R.string.permission_message);
        pop_tv_ok.setText(R.string.permission_button_application);

      AlertDialog dialog=  new AlertDialog.Builder(mContext).setView(view).setCancelable(false).create();
      dialog.show();
        pop_tv_ok.setOnClickListener(v -> {
            dialog.dismiss();
            request.proceed();//继续执行请求
        });
        //取消
        view.findViewById(R.id.pop_tv_cancle).setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

    }


    //用户拒绝后回调的方法  非必须的方法
    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION})
    public void multiDenied() {
        SplashActivityPermissionsDispatcher.getPermissionsWithCheck(this);
    }

    private PopupWindow popupWindow;

    //用户选择的不再询问后回调  非必须的方法
    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION})
    public void multiNeverAsk() {
        ToastUtils.showLong("电话权限、存储权限、定位权限为必选项，全部开通才可以正常使用APP,请到设置中开启\\n\\n设置路径——>久速商城——>权限");
    }

    // 必须要复写的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    //监听返回按钮
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取权限
        SplashActivityPermissionsDispatcher.getPermissionsWithCheck(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }


}
