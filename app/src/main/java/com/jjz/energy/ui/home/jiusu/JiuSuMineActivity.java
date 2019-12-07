package com.jjz.energy.ui.home.jiusu;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.enums.VipLevelEnum;
import com.jjz.energy.entry.jiusu.LoginBean;
import com.jjz.energy.presenter.jiusu.JiuSuMinePresenter;
import com.jjz.energy.util.glide.GlideUtils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.SpUtil;
import com.jjz.energy.view.mine.IPersonalInformationView;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * @Features: 个人中心
 * @author: create by chenhao on 2019/3/28
 */
public class JiuSuMineActivity extends BaseActivity<JiuSuMinePresenter> implements IPersonalInformationView {


    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.img_vip_level)
    ImageView imgVipLevel;
    @BindView(R.id.ll_nick_name)
    LinearLayout llNickName;
    @BindView(R.id.tv_vip_level)
    TextView tvVipLevel;
    @BindView(R.id.tv_label_no_vip)
    TextView tvLabelNoVip;
    @BindView(R.id.ll_label_no_vip)
    LinearLayout llLabelNoVip;
    @BindView(R.id.tv_label_one)
    TextView tvLabelOne;
    @BindView(R.id.ll_label_one)
    LinearLayout llLabelOne;
    @BindView(R.id.tv_label_two)
    TextView tvLabelTwo;
    @BindView(R.id.ll_label_two)
    LinearLayout llLabelTwo;
    @BindView(R.id.tv_label_three)
    TextView tvLabelThree;
    @BindView(R.id.ll_label_three)
    LinearLayout llLabelThree;
    @BindView(R.id.tv_label_four)
    TextView tvLabelFour;
    @BindView(R.id.ll_label_four)
    LinearLayout llLabelFour;
    @BindView(R.id.ll_mine_vip)
    LinearLayout llMineVip;
    @BindView(R.id.ll_mine_wallet)
    LinearLayout llMineWallet;
    @BindView(R.id.ll_mine_order)
    LinearLayout llMineOrder;
    @BindView(R.id.tv_seller_order)
    TextView tvSellerOrder;
    @BindView(R.id.ll_mine_seller_order)
    LinearLayout llMineSellerOrder;
    @BindView(R.id.ll_share)
    LinearLayout llShare;

    private LoginBean mLoginBean = new LoginBean();

    @Override
    protected void initView() {
        tvToolbarTitle.setText("会员中心");
    }

    @Override
    public void isSuccess(LoginBean loginBean) {
        mLoginBean = loginBean;
        SpUtil.init(mContext).commit(Constant.IS_SET_IDCARD,loginBean.getIs_set_idcard());
        //写入数据
        setUserInfo();
    }

    /**
     * 设置用户信息
     */
    private void setUserInfo() {
        if (mLoginBean != null) {
            //昵称
            tvNickname.setText(mLoginBean.getNickname());
            //会员等级
            if (mLoginBean.getLevel_id() == 1) {
                tvVipLevel.setText("点击升级成为VIP");
            } else {
                tvVipLevel.setText("尊敬的" + VipLevelEnum.getName(mLoginBean.getLevel_id()) + ",您好！");
            }
            if (mLoginBean.getIs_manager() == 1) {
                llMineSellerOrder.setVisibility(View.VISIBLE);
            }
            GlideUtils.loadImage(mContext, mLoginBean.getLevel_img(), imgVipLevel);
            //推广数量
            tvLabelNoVip.setText(String.valueOf(mLoginBean.getMember().getPush_level_1()));
            tvLabelOne.setText(String.valueOf(mLoginBean.getMember().getPush_level_2()));
            tvLabelTwo.setText(String.valueOf(mLoginBean.getMember().getPush_level_3()));
            tvLabelThree.setText(String.valueOf(mLoginBean.getMember().getPush_level_4()));
            tvLabelFour.setText(String.valueOf(mLoginBean.getMember().getPush_level_5()));
            //未处理的卖家订单
            if (mBadge != null) {
                mBadge.setBadgeNumber(mLoginBean.getUnconfirmed_num());
            } else {
                mBadge = new QBadgeView(mContext).bindTarget(tvSellerOrder).setBadgeGravity(Gravity.BOTTOM | Gravity.END).setBadgeNumber(mLoginBean.getUnconfirmed_num()).setGravityOffset(0, 0, true);
            }
        }
    }

    Badge mBadge;

    @OnClick({R.id.tv_vip_level, R.id.ll_mine_wallet, R.id.ll_share, R.id.ll_mine_seller_order,
            R.id.ll_label_no_vip, R.id.ll_mine_vip, R.id.ll_label_one, R.id.ll_label_two,
            R.id.ll_label_three, R.id.ll_label_four, R.id.ll_mine_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //会员等级 不是会员时要跳转升级会员页面
            case R.id.tv_vip_level:
                if (mLoginBean==null){
                    return;
                }
                if (!tvVipLevel.getText().toString().contains("尊敬")) {
                    startActivity(new Intent(mContext, JiuSuUpVipActivity.class).putExtra(Constant.INTENT_KEY_OBJECT,mLoginBean));
                }
                break;
            //我的佣金
            case R.id.ll_mine_wallet:
                if (mLoginBean== null){
                    return;
                }
                startActivity(new Intent(mContext, JiuSuMineWalletActivity.class).putExtra("level_id",
                        mLoginBean.getLevel_id()));
                break;
            //我的订单
            case R.id.ll_mine_order:
                startActivity(new Intent(mContext, JiuSuMineOrderActivity.class));
                break;
            //卖家订单管理
            case R.id.ll_mine_seller_order:
                startActivity(new Intent(mContext, JiuSuMineSellerOrderActivity.class));
                break;
            //推荐分享
            case R.id.ll_share:
                startActivity(new Intent(mContext, JiuSuShareActivity.class));
                break;
            //非会员
            case R.id.ll_label_no_vip:
                startActivity(new Intent(mContext, JiuSuMineAgencyActivity.class).putExtra(JiuSuMineAgencyActivity.AGENCY_TYPE, JiuSuMineAgencyActivity.TYPE_NO_VIP));
                break;
            //体验会员
            case R.id.ll_label_one:
                startActivity(new Intent(mContext, JiuSuMineAgencyActivity.class).putExtra(JiuSuMineAgencyActivity.AGENCY_TYPE, JiuSuMineAgencyActivity.TYPE_MEMBER));
                break;
            //经销商
            case R.id.ll_label_two:
                startActivity(new Intent(mContext, JiuSuMineAgencyActivity.class).putExtra(JiuSuMineAgencyActivity.AGENCY_TYPE, JiuSuMineAgencyActivity.TYPE_DEALER));
                break;
            //加盟商
            case R.id.ll_label_three:
                startActivity(new Intent(mContext, JiuSuMineAgencyActivity.class).putExtra(JiuSuMineAgencyActivity.AGENCY_TYPE, JiuSuMineAgencyActivity.TYPE_ALLIANCE_BUSINESS));
                break;
            //服务商
            case R.id.ll_label_four:
                startActivity(new Intent(mContext, JiuSuMineAgencyActivity.class).putExtra(JiuSuMineAgencyActivity.AGENCY_TYPE, JiuSuMineAgencyActivity.TYPE_SERVICE_PROVIDER));
                break;
            //我的会员
            case R.id.ll_mine_vip:
                if (mLoginBean==null){
                    return;
                }
                startActivity(new Intent(mContext, JiuSuMineVipActivity.class).putExtra(Constant.INTENT_KEY_OBJECT,mLoginBean));
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserInfo(PacketUtil.getRequestPacket(null));
    }

    @Override
    protected JiuSuMinePresenter getPresenter() {
        return new JiuSuMinePresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_jiusu_mine;
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void isFail(String msg, boolean i) {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}