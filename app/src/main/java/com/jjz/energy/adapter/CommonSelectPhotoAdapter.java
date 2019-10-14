package com.jjz.energy.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BaseApplication;
import com.jjz.energy.base.BaseRecycleNewAdapter;
import com.jjz.energy.ui.ImagePagerActivity;
import com.jjz.energy.util.glide.GlideUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhao 2018/12/3
 * @function 选择图片的通用适配器
 */
public class CommonSelectPhotoAdapter extends BaseRecycleNewAdapter<Uri> {
    /**
     * 默认最多可选择的图片数量  5张
     */
    private int defultPhotoNums = 5;

    public CommonSelectPhotoAdapter(int layoutResId, @Nullable List<Uri> data) {
        super(layoutResId, data);
    }

    /**
     * @param defultPhotoNums 最多可选照片数量
     */
    public CommonSelectPhotoAdapter(int layoutResId, @Nullable List<Uri> data, int defultPhotoNums) {
        super(layoutResId, data);
        this.defultPhotoNums = defultPhotoNums;
    }

    @Override
    protected void convert(BaseViewHolder holder, Uri item) {
        ImageView imgPhoto = holder.getView(R.id.img_photo);
        ImageView imgDelect = holder.getView(R.id.img_delect);
        //拍照按钮
        if (holder.getLayoutPosition() == getItemCount() - 1) {
            GlideUtils.loadImage(mContext, "", imgPhoto, R.color.color_primary_f5,R.mipmap.ic_add_photo);
            imgDelect.setVisibility(View.GONE);
            imgPhoto.setOnClickListener(v -> {
                //拍照
                if (getItemCount() == defultPhotoNums+1) {
                    ((BaseActivity) mContext).showToast("您已选择" + defultPhotoNums + "张图片");
                    return;
                } else {
                    //点击选择图片或者拍照，需要申请动态权限，需要通过EventBus发送到Activity 页面进行
                    EventBus.getDefault().post("selectPhoto");
                }
            });
        } else {
            GlideUtils.loadImage(mContext, item.toString(), imgPhoto);
            imgDelect.setVisibility(View.VISIBLE);
            //查看大图
            imgPhoto.setOnClickListener(v -> {
                List<String> intentList = new ArrayList<>();
                for (int i = 0; i < mData.size(); i++) {
                    intentList.add(mData.get(i).toString());
                }
                intentList.remove(mData.size() - 1);
                ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(v.getMeasuredWidth(),
                        v.getMeasuredHeight());
                ImagePagerActivity.startImagePagerActivity(BaseApplication.AppContext, intentList, holder.getPosition(), imageSize);

            });
            //删除按钮
            imgDelect.setOnClickListener(v -> {
                mData.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getLayoutPosition());
                // 如果移除的是最后一个，忽略
                notifyItemRangeChanged(holder.getLayoutPosition(), getItemCount() - holder.getLayoutPosition());
            });
        }

    }
}
