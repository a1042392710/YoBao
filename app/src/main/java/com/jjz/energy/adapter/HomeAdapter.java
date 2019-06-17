package com.jjz.energy.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jjz.energy.R;
import com.jjz.energy.entry.HomeItemEntry;
import com.jjz.energy.util.glide.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

/**
 * @Features:
 * @author: create by chenhao on 2019/6/17
 */
public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeItemEntry, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeAdapter(List<HomeItemEntry> data) {
        super(data);
        addItemType(HomeItemEntry.BANNER, R.layout.item_home_banner);
        addItemType(HomeItemEntry.YOBAO, R.layout.item_home_yobao);
        addItemType(HomeItemEntry.TYPE, R.layout.item_home_yobao);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItemEntry item) {

        switch (item.getItemType()){

            case HomeItemEntry.BANNER:
                Banner banner = helper.getView(R.id.banner);
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                banner.setImages(item.getHomeBean().getBannerList());
                //设置banner动画
                banner.setBannerAnimation(Transformer.Default);
                //设置轮播时间
                banner.setDelayTime(3000);
                //设置指示器位置（当banner模式中有指示器时）
                banner.setIndicatorGravity(BannerConfig.CENTER);
                //轮播点击事件
                banner.setOnBannerListener(position -> {
                });
                banner.start();
                break;

            case HomeItemEntry.YOBAO:

                break;

            case HomeItemEntry.TYPE:
                break;

        }

    }
}
