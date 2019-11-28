package com.jjz.energy.ui.home.jiusu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.entry.jiusu.BillEntry;
import com.jjz.energy.presenter.jiusu.BillPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.view.jiusu.IBillView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chenhao 2018/12/25
 * @function 查看发票大图
 */
public class JiuSuLookBillActivity extends BaseActivity<BillPresenter> implements IBillView {
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.img_bill)
    ImageView imgBill;


    @Override
    protected void initView() {
        tvToolbarTitle.setText("查看发票");
        tvToolbarRight.setText("下载");
        mPresenter.getBill(PacketUtil.getRequestPacket(Utils.stringToMap("order_id",
                getIntent().getStringExtra("order_id"))));
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    @OnClick({R.id.ll_toolbar_left, R.id.img_bill, R.id.tv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            //下载电子发票到本地
            case R.id.tv_toolbar_right:
                if (StringUtil.isEmpty(imgUrl)) {
                    showToast("下载失败");
                } else {
                    download();
                }
                break;
            case R.id.img_bill:
                //查看大图
                break;
        }
    }

    /**
     * 图片地址
     */
    private String imgUrl ;

    @Override
    public void getBillSuccess(BillEntry data) {
        //图片地址
        imgUrl = data.getImg_url();
        //加载发票图片
        GlideUtils.loadImage(mContext, imgUrl, imgBill);
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }

    @Override
    protected BillPresenter getPresenter() {
        return new BillPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_look_bill;
    }

    //下载文件
    @SuppressLint("CheckResult")
    private  void download (){
        Observable.create((ObservableOnSubscribe<File>) e -> {
            //通过gilde下载得到file文件,这里需要注意android.permission.INTERNET权限
            e.onNext(Glide.with(mContext)
                    .load(imgUrl)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get());
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(file -> {
                    //获取到下载得到的图片，进行本地保存
                    File pictureFolder = Environment.getExternalStorageDirectory();
                    //第二个参数为你想要保存的目录名称
                    File appDir = new File(pictureFolder, "jjz_photo");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File destFile = new File(appDir, fileName);
                    //把gilde下载得到图片复制到定义好的目录中去
                    copy(file, destFile);

                    // 最后通知图库更新
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(new File(destFile.getPath()))));
                    showToast("下载成功，文件路径为：根目录下 jjz_photo 文件夹内");
                });
    }
    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    public void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
