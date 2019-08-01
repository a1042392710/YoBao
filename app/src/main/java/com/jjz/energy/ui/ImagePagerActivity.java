package com.jjz.energy.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jjz.energy.R;
import com.jjz.energy.base.BaseActivity;
import com.jjz.energy.base.BasePresenter;
import com.jjz.energy.widgets.FxScaleBUGViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @ author FX
 * @ date  2018/11/19  17:53
 * @ fuction 查看大图
 */
public class ImagePagerActivity extends BaseActivity {


    //图片
    @BindView(R.id.pager)
    FxScaleBUGViewPager viewPager;

    @BindView(R.id.guideGroup)
    LinearLayout guideGroup;
    //当前第几张
    @BindView(R.id.tv_selectwhere)
    TextView tv_selectwhere;
    //    //总共张数
    @BindView(R.id.tv_sum)
    TextView tv_sum;


    public static final String INTENT_IMGURLS = "imgurls";
    public static final String INTENT_POSITION = "position";
    public static final String INTENT_IMAGESIZE = "imagesize";
    public static final String INTENT_TITLE = "title";
    private List<View> guideViewList = new ArrayList<>();
    public ImageSize imageSize;
    private int startPos;
    private ArrayList<String> imgUrls;
    private String title;

    public static void startImagePagerActivity(Context context, List<String> imgUrls, int position,
                                               ImageSize imageSize) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<>(imgUrls));
        intent.putExtra(INTENT_POSITION, position);
        intent.putExtra(INTENT_IMAGESIZE, imageSize);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//        overridePendingTransition(R.anim.bga_sbl_activity_forward_enter, R.anim.bga_sbl_activity_forward_exit);
    }

    public static void startImagePagerActivity2(Context context, List<String> imgUrls, int position,
                                                ImageSize imageSize, String title) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<>(imgUrls));
        intent.putExtra(INTENT_POSITION, position);
        intent.putExtra(INTENT_IMAGESIZE, imageSize);
        intent.putExtra(INTENT_TITLE, title);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//        ((Activity) context).overridePendingTransition(R.anim.bga_sbl_activity_forward_enter, R.anim.bga_sbl_activity_forward_exit);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_imagepager;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void initView() {
        getIntentData();
//        setTitle(title);
        tv_selectwhere.setText(String.format("%d", startPos + 1));
        tv_sum.setText(String.format("%d", imgUrls.size()));
        ImageAdapter mAdapter = new ImageAdapter(ImagePagerActivity.this);
        mAdapter.setDatas(imgUrls);
        mAdapter.setImageSize(imageSize);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()

        {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tv_selectwhere.setText(String.valueOf(position + 1));
//                for (int i = 0; i < guideViewList.size(); i++) {
//                    guideViewList.get(i).setSelected(i == position ? true : false);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(startPos);
    }
    private void getIntentData() {
        startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
        imgUrls = getIntent().getStringArrayListExtra(INTENT_IMGURLS);
        imageSize = (ImageSize) getIntent().getSerializableExtra(INTENT_IMAGESIZE);
        title = getIntent().getStringExtra(INTENT_TITLE);
    }

    private void addGuideView(LinearLayout guideGroup, int startPos, ArrayList<String> imgUrls) {
        if (imgUrls != null && imgUrls.size() > 0) {
            guideViewList.clear();
            for (int i = 0; i < imgUrls.size(); i++) {
                View view = new View(this);
                view.setBackgroundResource(R.drawable.selector_guide_bg);
                view.setSelected(i == startPos ? true : false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        getResources().getDimensionPixelSize(R.dimen.gudieview_width),
                        getResources().getDimensionPixelSize(R.dimen.gudieview_heigh));
                layoutParams.setMargins(10, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    public static class ImageAdapter extends PagerAdapter {

        private List<String> datas = new ArrayList<String>();
        private LayoutInflater inflater;
        private Activity context;
        private ImageSize imageSize;
        private ImageView smallImageView = null;

        public void setDatas(List<String> datas) {
            if (datas != null)
                this.datas = datas;
        }

        public void setImageSize(ImageSize imageSize) {
            this.imageSize = imageSize;
        }

        public ImageAdapter(Activity context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (datas == null)
                return 0;
            return datas.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = inflater.inflate(R.layout.item_pager_image, container, false);
            if (view != null) {
                final PhotoView imageView = view.findViewById(R.id.image);
                if (imageSize != null) {
                    // 预览imageView
                    smallImageView = new ImageView(context);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.gravity = Gravity.CENTER;
//                    smallImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    smallImageView.setLayoutParams(layoutParams);
                    ((FrameLayout) view).addView(smallImageView);
                }
                imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float x, float y) {
                        context.finish();
                    }

                    @Override
                    public void onOutsidePhotoTap() {

                    }
                });

                /**
                 * 长按保存图片
                 */
//                imageView.setOnLongClickListener((View v) -> {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setItems(new String[]{"保存图片"},
//                            (dialog, which) -> {
//                                imageView.setDrawingCacheEnabled(true);
//                                Bitmap imageBitmap = imageView.getDrawingCache();
//                                if (imageBitmap != null) {
//                                    // 异步请求
//                                    SaveImageTask imageTask = new SaveImageTask(context, imageView);
//                                    imageTask.execute(imageBitmap);
//                                }
//                            });
//                    AlertDialog dialog = builder.create();
//                    dialog.setCanceledOnTouchOutside(true);
//                    if (!(context).isFinishing())
//                        dialog.show();
//                    return true;
//                });
                final String imgurl = datas.get(position);
                loadImage(imgurl, imageView);
                container.addView(view, 0);
            }
            return view;
        }

        /**
         * 加载图片
         *
         * @param url
         * @param imageView
         */
        private void loadImage(String url, PhotoView imageView) {

            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.ic_bg_img_not) //占位图
                    .error(R.mipmap.ic_bg_img_not)       //错误图
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(url).apply(options).into(imageView);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }

    public static class ImageSize implements Serializable {

        private int width;
        private int height;

        public ImageSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }

}
