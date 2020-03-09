package com.jjz.energy.ui.home.logistics;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.base.Constant;

import java.io.Serializable;

import butterknife.BindView;
import cn.jmessage.support.google.gson.Gson;

/**
 * @Features: 地图选点功能
 * @author: create by chenhao on 2020/3/9
 */
public class MapSelectActivity extends BaseActivity {

    @BindView(R.id.webView)
    BridgeWebView mWebView;
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;

    private String webUrl = "http://172.16.32.7/web/index/map";
    /**
     * 起点还是终点
     */
    private String type  ;
    @Override
    public void initView() {
        tvToolbarTitle.setText("选择位置");
        llToolbarLeft.setOnClickListener(v -> finish());
        type = getIntent().getStringExtra("type");
        initWebView();
    }


    private void initWebView() {
        mWebView.setWebChromeClient(new WebChromeClient());
        //加载服务器网页或者本地网页
        mWebView.loadUrl(webUrl);

        // 重写handler方法接收js的消息
        mWebView.setDefaultHandler(new DefaultHandler(){
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("address",data);
                LatLngBean bean =new Gson().fromJson(data,LatLngBean.class);
                if ("start".equals(type)){
                    setResult(0, new Intent().putExtra(Constant.INTENT_KEY_OBJECT,bean));
                }else{
                    setResult(1, new Intent().putExtra(Constant.INTENT_KEY_OBJECT,bean));
                }
                finish();
                //在此接收js返回的数据
                function.onCallBack(data);
            }
        });

    }

   public class LatLngBean implements Serializable {
        private double lat;
       private double lng;
       private String poiname;
       private String cityname;
       private String poiaddress;

       public String getPoiname() {
           return poiname == null ? "" : poiname;
       }

       public void setPoiname(String poiname) {
           this.poiname = poiname;
       }

       public String getCityname() {
           return cityname == null ? "" : cityname;
       }

       public void setCityname(String cityname) {
           this.cityname = cityname;
       }

       public String getPoiaddress() {
           return poiaddress == null ? "" : poiaddress;
       }

       public void setPoiaddress(String poiaddress) {
           this.poiaddress = poiaddress;
       }

       public double getLat() {
           return lat;
       }

       public void setLat(double lat) {
           this.lat = lat;
       }

       public double getLng() {
           return lng;
       }

       public void setLng(double lng) {
           this.lng = lng;
       }
   }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mWebView.canGoBack()){
                mWebView.goBack();
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_map_select;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }



}
