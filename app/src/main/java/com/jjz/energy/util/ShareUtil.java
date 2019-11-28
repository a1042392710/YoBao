package com.jjz.energy.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jjz.energy.R;
import com.jjz.energy.adapter.CommonAdapter;
import com.jjz.energy.adapter.CommonViewHolder;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.jiusu.ShareBean;
import com.jjz.energy.entry.jiusu.ShareInfoBean;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author chenhao 2019/1/19
 * @function 分享 工具类
 */
public class ShareUtil {

    //分享的数据
    private int[] icons = new int[]{R.mipmap.ic_share_wechat, R.mipmap.ic_share_wechat_friend};
    private String[] names = new String[]{"微信", "微信朋友圈"};
    private List<ShareBean> eventList = new ArrayList<>();
    private PopupWindow popupWindowShare;//分享的window

    private int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
    private int mExtarFlag = 0x00;

    private IWXAPI api;
    private static final int THUMB_SIZE = 150;

    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;//聊天界面

    /**
     * Activity 对象
     */
   private BaseActivity mContext;

    /**
     * 分享的数据源
     */
   private ShareInfoBean mShareInfo;


    //初始化分享工具类
    public ShareUtil(BaseActivity context){
        this.mContext =context;
        api = WXAPIFactory.createWXAPI(context, Constant.WX_APP_ID, true);
    }



    /**
     * 初始化分享弹框
     */
    public void showSharePop(ShareInfoBean shareInfoBean) {
        if (shareInfoBean==null){
            ToastUtils.showShort("获取分享数据失败");
            return;
        }
        this.mShareInfo = shareInfoBean;
        String url = mShareInfo.getShare_url();
        @SuppressLint("InflateParams")
        View view = mContext.getLayoutInflater().inflate(R.layout.dialog_share,
                null, false);
        popupWindowShare = getPopupWindow(view);
        //分享列表
        GridView dialog_share_grid = view.findViewById(R.id.dialog_share_grid);
        FrameLayout dialog_share_fr = view.findViewById(R.id.dialog_share_fr);
        dialog_share_fr.setOnClickListener(new SafeClickListener() {
            @Override
            protected void onSingleClick(View v) {
                popupWindowShare.dismiss();
            }
        });
        eventList.clear();
        for (int i = 0; i < icons.length; i++) {
            ShareBean main = new ShareBean();
            main.setTitle(names[i]);
            main.setImg(icons[i]);
            eventList.add(main);
        }

        CommonAdapter<ShareBean> groupAdapter = new CommonAdapter<ShareBean>(mContext, eventList, R.layout.share_grid_item) {
            @SuppressLint("SetTextI18n")
            @Override
            public void convert(CommonViewHolder holder, ShareBean datas) {
                //图片
                ImageView share_grid_icon = holder.getView(R.id.share_grid_icon);
                //标题
                TextView share_grid_name = holder.getView(R.id.share_grid_name);
                share_grid_icon.setImageResource(datas.getImg());
                share_grid_name.setText(datas.getTitle() + "");
            }
        };
        dialog_share_grid.setAdapter(groupAdapter);
        dialog_share_grid.setOnItemClickListener((parent, view1, position, id) -> {
                mExtarFlag = 0x00;
                mTargetScene = SendMessageToWX.Req.WXSceneSession;
                switch (position) {
                    case 0://微信
                        mTargetScene = SendMessageToWX.Req.WXSceneSession;//聊天界面
                        initWeixinShare(url);
                        popupWindowShare.dismiss();
                        break;
                    case 1://微信朋友圈
                        mTargetScene = SendMessageToWX.Req.WXSceneTimeline;//朋友圈
                        initWeixinShare(url);
                        popupWindowShare.dismiss();
                        break;
            }
        });

        popupWindowShare.setOnDismissListener(() -> mContext.setDarkWindow(false));
        mContext.setDarkWindow(true);
        popupWindowShare.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    //初始化微信分享
    private void initWeixinShare(String url) {

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        if (mShareInfo==null){
            msg.title = "金玖洲新能源";
            msg.description = "持续环保 绿色长久，澎湃动力 能者为速";
        }else{
            msg.title = mShareInfo.getRand_title();
            msg.description = mShareInfo.getRand_desc() ;  }

        Bitmap bmp = getBitmap(mContext, R.mipmap.ic_logo);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = Utils.buildTransaction("webpage");
        req.message = msg;
        req.scene = mTargetScene;
        api.sendReq(req);
    }

    /**
     * createScaledBitmap 方法在 5.0以上系统报错，需要判断版本
     */
    private Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap;
        if (Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP){
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        }else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

    /**
     * 初始化popupwindow 因为配置都一样，所以写在一起
     *
     * @param view
     * @return
     */
    private PopupWindow getPopupWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(mContext);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.animation_bottom_dialog);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        return popupWindow;
    }

    /**
     * 注销
     */
    public  void destory(){
        if (popupWindowShare!=null&&popupWindowShare.isShowing()){
            popupWindowShare.dismiss();
            popupWindowShare=null;
        }
    }

}
