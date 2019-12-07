package com.jjz.energy.ui.mine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.jjz.energy.R;
import com.jjz.energy.adapter.MineAdapter;
import com.jjz.energy.base.BaseApplication;
import com.jjz.energy.base.BaseLazyFragment;
import com.jjz.energy.base.BaseWebActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.UserInfo;
import com.jjz.energy.entry.jiusu.MineBean;
import com.jjz.energy.presenter.mine.MinePresenter;
import com.jjz.energy.ui.home.jiusu.JiuSuVCodeDetailActivity;
import com.jjz.energy.ui.jiusu_shop.JiuSuShopHomePageActivity;
import com.jjz.energy.ui.jiusu_shop.ShopSureBuyActivity;
import com.jjz.energy.ui.mine.information.HomePageActivity;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.AesUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.networkUtil.UserLoginBiz;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.util.system.SpUtil;
import com.jjz.energy.view.mine.IMineView;
import com.jjz.energy.zxing.android.CaptureActivity;
import com.jjz.energy.zxing.common.ZxConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Features: 个人中心
 * @author: create by chenhao on 2019/7/15
 */
@RuntimePermissions
public class MineFragment  extends BaseLazyFragment<MinePresenter> implements IMineView {

    @BindView(R.id.view_title)
    View viewTitle;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.ll_mine_release)
    LinearLayout llMineRelease;
    @BindView(R.id.ll_mine_seller)
    LinearLayout llMineSeller;
    @BindView(R.id.ll_mine_buyer)
    LinearLayout llMineBuyer;
    @BindView(R.id.ll_mine_like)
    LinearLayout llMineLike;
    @BindView(R.id.rv_mine)
    RecyclerView rvMine;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_about_us)
    TextView tvAboutUs;
    @BindView(R.id.tv_fans_sum)
    TextView tvFansSum;
    @BindView(R.id.tv_like_sum)
    TextView tvLikeSum;
    @BindView(R.id.tv_shop_go)
    TextView tvShopGo;
    @BindView(R.id.tv_shop_vcode)
    TextView tvShopVcode;

    /**
     * 我的菜单列表数据
     */
    private List<MineBean> mList;

    /**
     * 我的页面数据
     */
    private UserInfo mUserInfo = new UserInfo();

    @Override
    protected void initView() {
        initRv();
    }

    /**
     * 获取用户数据成功
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void isGetInfoSuccess(UserInfo data) {
        mUserInfo = data;
        //将最新的数据存到本地
        UserLoginBiz.getInstance(mContext).saveUserInfo(mUserInfo);
        //推送公告
        String push_message = mUserInfo.getPush_message();
        //显示文本
        if (!StringUtil.isEmpty(push_message) && !push_message.equals(SpUtil.init(mContext).readString("push_message"))){
            PopWindowUtil.getInstance().showPopupWindow(mContext, push_message, () -> {});
            SpUtil.init(mContext).commit("push_message",push_message);
        }
        //头像
        GlideUtils.loadCircleImage(mContext, mUserInfo.getHead_pic(), imgHead);
        //昵称
        tvNickName.setText(mUserInfo.getNickname());
        //关注数量和粉丝数量
        tvFansSum.setText("粉丝："+data.getFans_num());
        tvLikeSum.setText("关注："+data.getFocus_num());
    }

    /**
     * 初始化我的菜单网格数据
     */
    private void initRv() {
        rvMine.setLayoutManager(new GridLayoutManager(mContext, 4));
        mList = new ArrayList<>();
        mList.add(new MineBean("我的帖子", R.mipmap.ic_mine_post));
//        mList.add(new MineBean("我的评价",R.mipmap.ic_mine_comment));
        mList.add(new MineBean("我的积分",R.mipmap.ic_mine_integral));
        mList.add(new MineBean("消费记录",R.mipmap.ic_mine_shopping_list));
        mList.add(new MineBean("收款记录",R.mipmap.ic_mine_get_money));
//        mList.add(new MineBean("我的公益", R.mipmap.ic_mine_charity));
//        mList.add(new MineBean("我的物流", R.mipmap.ic_mine_logistics));
//        mList.add(new MineBean("我的保险", R.mipmap.ic_mine_insurance));
//        mList.add(new MineBean("我的养老", R.mipmap.ic_mine_pension));
//        mList.add(new MineBean("我的教育",R.mipmap.ic_mine_education));
        MineAdapter mineAdapter = new MineAdapter(R.layout.item_mine_type,mList);
        rvMine.setAdapter(mineAdapter);
        mineAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (mineAdapter.getItem(position).getTitle()){
                case "我的帖子":
                    startActivity(new Intent(mContext,MinePostActivity.class));
                    break;
                case "我的评价":
                    break;
                case "我的积分":
                    startActivity(new Intent(mContext,MineIntegralActivity.class));
                    break;
                case "我的公益":
                    break;
                case "我的物流":
                    break;
                case "我的保险":
                    break;
                case "我的养老":
                    break;
                case "我的教育":
                    break;
                case "收款记录":
                    startActivity(new Intent(mContext, MineJiuSuCollectionListActivity.class));
                    break;
                case "消费记录":
                    startActivity(new Intent(mContext,MineJiuSuShoppingListActivity.class));
                    break;
            }
        });

    }

    @OnClick({R.id.img_setting, R.id.ll_mine_release, R.id.ll_mine_seller, R.id.ll_mine_buyer,R.id.tv_shop_go,R.id.tv_shop_vcode,
            R.id.ll_mine_like, R.id.tv_feedback, R.id.tv_about_us ,R.id.img_head  ,R.id.tv_like_sum ,R.id.tv_fans_sum  })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //设置
            case R.id.img_setting:
                startActivity(new Intent(mContext, MineSettingActivity.class));
                break;
            //头像
            case R.id.img_head:
                if (!StringUtil.isEmpty(mUserInfo.getShop_id())){
                    startActivity(new Intent(mContext, JiuSuShopHomePageActivity.class).putExtra(Constant.SHOP_ID,mUserInfo.getShop_id()));
                }else {
                    startActivity(new Intent(mContext, HomePageActivity.class).putExtra(Constant.USER_ID, mUserInfo.getUser_id()));
                }
                break;
                //我发布的
            case R.id.ll_mine_release:
                startActivity(new Intent(mContext,MinePutActivity.class));
                break;
                //我卖出的
            case R.id.ll_mine_seller:
                startActivity(new Intent(mContext,MineSellerActivity.class));
                break;
                //我买到的
            case R.id.ll_mine_buyer:
                startActivity(new Intent(mContext,MineBuyerActivity.class));
                break;
                //我收藏的
            case R.id.ll_mine_like:
                startActivity(new Intent(mContext,MineLikeCommodityActivity.class));
                break;
                //我关注的人
            case R.id.tv_like_sum:
                startActivity(new Intent(mContext,MineFansAndLikeActivity.class).putExtra("index",0));
                break;
                //我的粉丝
            case R.id.tv_fans_sum:
                startActivity(new Intent(mContext,MineFansAndLikeActivity.class).putExtra("index",1));
                break;
                //意见反馈
            case R.id.tv_feedback:
                startActivity(new Intent(mContext,FeedBackActivity.class));
                break;
                //关于我们
            case R.id.tv_about_us:
                startActivity(new Intent(mContext,AboutUsActivity.class));
                break;
                //扫一扫
            case R.id.tv_shop_vcode:
                MineFragmentPermissionsDispatcher.takeScanWithCheck(this);
                break;
                //商家入驻
            case R.id.tv_shop_go:
                //获取时间
                long time  = System.currentTimeMillis();
                String token = "" ;
                String decode_token = "";
                if (!StringUtils.isEmpty(SpUtil.init(BaseApplication.getAppContext()).readString(Constant.LOGIN_ID))) {
                    decode_token = SpUtil.init(BaseApplication.getAppContext()).readString(Constant.LOGIN_ID);
                }
                //用户 token
                if (!StringUtils.isEmpty(decode_token)) {//解密后的token 再次加密
                    token = AesUtils.encrypt(decode_token + time, AesUtils.KEY, AesUtils.IV);
                }
                //手机号
               String  phone =  UserLoginBiz.getInstance(mContext).readUserInfo().getMobile();
                //完整的带参数 进 商家入驻页面
                String url = Constant.SHOP_GO + "?time="+time+"&&token="+token +"&&username="+phone;
                //跳转
                startActivity(new Intent(mContext, BaseWebActivity.class).putExtra(BaseWebActivity.WEB_TITLE,"商家入驻").putExtra(BaseWebActivity.WEB_URL,url));
                break;
        }
    }
    @Override
    public void isFail(String msg ,boolean isNetAndServiceError) {}


    // --------------------------------------------- 扫码权限

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void takeScan() {
        startActivityForResult(new Intent(mContext, CaptureActivity.class),
                Constant.REQUEST_CODE_SCAN);
    }

    //用户选择的不再询问后回调  非必须的方法
    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void multiNeverAsk() {
        PopWindowUtil.getInstance().showPopupWindow(mContext, "没有权限您就无法使用扫一扫功能，请您前往设置页面打开拍照和读取sd" +
                "卡的权限！", "取消", "去设置", () -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri1 = Uri.parse("package:" + mContext.getPackageName());
            intent.setData(uri1);
            startActivityForResult(intent, 20);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MineFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode,
                grantResults);
    }


    //=========================================================================== 方法重写和生命周期


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_SCAN) {
            if (data != null) {
                Map itemMap;
                try {
                    itemMap = (Map) JSON.parse(data.getStringExtra(ZxConstant.CODED_CONTENT));
                    //二维码的内容
                    String qr_code = (String) itemMap.get("qr_code");
                    String scan_type = (String) itemMap.get("scan_type");
                    //扫码类型为订单
                    if (Constant.SCAN_TYPE_ORDER.equals(scan_type)) {
                        startActivity(new Intent(mContext, JiuSuVCodeDetailActivity.class).putExtra(
                                "qr_code", qr_code));
                        //扫码类型为 到店支付  shop_shopping
                    } else if (Constant.SCAN_TYPE_SHOP_SHOPPING.equals(scan_type)){
                        startActivity(new Intent(mContext, ShopSureBuyActivity.class).putExtra(Constant.SHOP_ID,qr_code));

                    }else{
                        showToast("请扫描正确的二维码");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("请扫描正确的二维码");
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //刷新数据
        if (mPresenter == null) {
            mPresenter = new MinePresenter(this);
        }
        if (UserLoginBiz.getInstance(mContext).detectUserLoginStatus()){
            //刷新当前页面数据
            mPresenter.getUserInfo(PacketUtil.getRequestPacket(null));
        }

    }

    @Override
    protected MinePresenter getPresenter() {
        return new MinePresenter(this);
    }


    @Override
    protected int layoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void showLoading() {}

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

}
