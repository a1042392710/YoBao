package com.jjz.energy.ui.mine.shipping_address;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.base.Constant;
import com.jjz.energy.entry.mine.AddressBean;
import com.jjz.energy.presenter.mine.AddressManagerPresenter;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.Utils;
import com.jjz.energy.util.networkUtil.PacketUtil;
import com.jjz.energy.util.system.PopWindowUtil;
import com.jjz.energy.view.mine.IAddressManagerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenhao 2018/12/7
 * @function 收货地址管理
 * 使用情况： 1 查看地址  2 选择地址 （选择地址，需要传入 SELECT_ADDRESS 参数 为 true,在点击子项后，携带 bean 数据返回上一页，返回码为 RESULT_ADDRESS ）
 */
public class AddressManagerActivity extends BaseActivity<AddressManagerPresenter> implements IAddressManagerView {

    /**
     * 返回的地址 返回码
     */
    public static int RESULT_ADDRESS = 30;
    /**
     * 是否为返回地址的操作 Key
     */
    public static String SELECT_ADDRESS = "isSelect";
    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.rv_address_manager)
    RecyclerView rvAddressManager;
    /**
     * 是否是选择地址操作
     */
    private boolean isSelectAddress = false;
    /**
     * 地址管理适配器
     */
    private AddressManagerAdapter mAddressManagerAdapter;

    /**
     * 数据源
     */
    private List<AddressBean.ListBean> mList;

    /**
     * 对哪条地址操作的： position
     */
    private int selectPosition ;


    @Override
    protected AddressManagerPresenter getPresenter() {
        return new AddressManagerPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.act_address_manager;
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("地址管理");
        //操作 是否为返回地址
        isSelectAddress = getIntent().getBooleanExtra(SELECT_ADDRESS, false);
        //初始化数据
        mList = new ArrayList<>();
        rvAddressManager.setLayoutManager(new LinearLayoutManager(this));
        mAddressManagerAdapter = new AddressManagerAdapter(R.layout.item_address_manager, mList);
        rvAddressManager.setAdapter(mAddressManagerAdapter);
        //初始化监听
        initListener();
        //获取数据
        getData();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        //长按弹窗
        mAddressManagerAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            //弹窗提示
            PopWindowUtil.getInstance().showPopupWindow(mContext,
                   "您可以选择",
                    "设为默认", "删除地址", new PopWindowUtil.OnCountersignAndCancleListener() {
                        @Override
                        public void countersign() {
                            selectPosition = position;
                            //删除地址接口
                            deleteAddress();
                        }

                        @Override
                        public void cancle() {
                            selectPosition = position;
                            //默认地址接口
                            defaultAddress();
                        }
                    });
            return false;
        });

        /**
         * 点击事件  isSelect后 点击携带当前地址退出页面
         */
        mAddressManagerAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isSelectAddress) {
                //退出页面
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_KEY_OBJECT, mList.get(position));
                setResult(RESULT_ADDRESS, intent);
                finish();
            }
        });
    }

    @OnClick({R.id.ll_toolbar_left, R.id.img_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
            case R.id.img_add:
                //跳转添加收货地址页面
                startActivityForResult(new Intent(mContext, AddAddressActivity.class).putExtra("title", "添加地址"),10);
                break;
        }
    }

    class AddressManagerAdapter extends BaseRecycleNewAdapter<AddressBean.ListBean> {

        public AddressManagerAdapter(int layoutResId, @Nullable List<AddressBean.ListBean> data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder helper, AddressBean.ListBean item) {
            String addressStr = item.getArea()+item.getAddress();
            //地址
            helper.setText(R.id.item_tv_address, addressStr.replace(" ", ""));
            //电话
            helper.setText(R.id.item_tv_consignee_phone, item.getMobile());
            //是否默认
            if (item.getIs_default()==1){
                helper.setVisible(R.id.item_tv_is_default, true);
            }else{
                helper.setVisible(R.id.item_tv_is_default, false);
            }
            //收货人
            helper.setText(R.id.item_tv_consignee_name, item.getConsignee());
            helper.getView(R.id.item_img_modify_address).setOnClickListener(v -> {
                // 编辑地址
                startActivityForResult(new Intent(mContext,AddAddressActivity.class)
                        .putExtra(Constant.INTENT_KEY_OBJECT, mList.get(helper.getLayoutPosition()))
                        .putExtra("title","编辑地址" ),10);
            });
        }
    }

    @Override
    public void isSuccess(AddressBean data) {
        mList = data.getList();
        //无数据页面
        if (StringUtil.isListEmpty(mList)) {
            mAddressManagerAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data,"您还没有添加收货地址",false, null));
        } else {
            mAddressManagerAdapter.notifyChangeData(mList);
        }
    }
    /**
     * 删除成功
     */
    @Override
    public void isDeleteSuccess(AddressBean data) {
        mAddressManagerAdapter.remove(selectPosition);
        if (StringUtil.isListEmpty(mAddressManagerAdapter.getData())){
            mAddressManagerAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_list_data,"您还没有添加收货地址",false, null));
        }
    }

    /**
     * 设为默认成功
     */
    @Override
    public void isDefaultSuccess(AddressBean data) {
        List<AddressBean.ListBean> list = new ArrayList<>();
        for (int i = 0; i < mList.size() ; i++) {
            AddressBean.ListBean bean = mList.get(i);
            if (i==selectPosition){
                bean.setIs_default(1);
            }else{
                bean.setIs_default(0);
            }
            list.add(bean);
        }
        mAddressManagerAdapter.notifyChangeData(list);
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
    public void isFail(String msg, boolean isNetAndServiceError) {
        if (isNetAndServiceError) {
            //网络错误页面
            mAddressManagerAdapter.setEmptyView(getLoadSirView(R.mipmap.ic_none_timeout, "网络发生错误",true, v -> {
                getData();
            }));
        } else {
            showToast(msg);
        }
    }

    /**
     * 获取地址列表
     */
    private void getData(){
        mPresenter.getAddressList(PacketUtil.getRequestPacket(Utils.stringToMap("", "")));
    }


    /**
     * 删除地址
     */
    private void deleteAddress(){
        mPresenter.deleteAddress(PacketUtil.getRequestPacket(Utils.stringToMap("address_id", mList.get(selectPosition).getAddress_id()+"")));
    }

    /**
     * 设为默认地址
     */
    private void defaultAddress(){
        mPresenter.defaultAddress(PacketUtil.getRequestPacket(Utils.stringToMap("address_id", mList.get(selectPosition).getAddress_id()+"")));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==10&&resultCode==20){
                getData();
            }
    }
}
