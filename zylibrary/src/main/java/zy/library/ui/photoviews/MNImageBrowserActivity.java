package zy.library.ui.photoviews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import zy.library.GlideApp;
import zy.library.R;
import zy.library.ui.photoviews.transforms.DefaultTransformer;
import zy.library.ui.photoviews.transforms.DepthPageTransformer;
import zy.library.ui.photoviews.transforms.RotateDownTransformer;
import zy.library.ui.photoviews.transforms.RotateUpTransformer;
import zy.library.ui.photoviews.transforms.ZoomInTransformer;
import zy.library.ui.photoviews.transforms.ZoomOutSlideTransformer;
import zy.library.ui.photoviews.transforms.ZoomOutTransformer;
import zy.library.ui.sheetdialog.SheetDialog;
import zy.library.utils.Utils;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;


/**
 * 图片浏览的页面
 */
public class MNImageBrowserActivity extends Activity {

    public final static String IntentKey_ImageList = "IntentKey_ImageList";
    public final static String IntentKey_CurrentPosition = "IntentKey_CurrentPosition";
    public final static String IntentKey_ViewPagerTransformType = "IntentKey_ViewPagerTransformType";
    public final static int ViewPagerTransform_Default = 0;
    public final static int ViewPagerTransform_DepthPage = 1;
    public final static int ViewPagerTransform_RotateDown = 2;
    public final static int ViewPagerTransform_RotateUp = 3;
    public final static int ViewPagerTransform_ZoomIn = 4;
    public final static int ViewPagerTransform_ZoomOutSlide = 5;
    public final static int ViewPagerTransform_ZoomOut = 6;

    private Context context;

    private MNGestureView mnGestureView;
    private MNViewPager viewPagerBrowser;
    private TextView tvNumShow;
    private TextView tvSave;
    private RelativeLayout rl_black_bg;

    private ArrayList<String> imageUrlList = new ArrayList<>();
    private int currentPosition;
    private int currentViewPagerTransform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowFullScreen();
        setContentView(R.layout.activity_mnimage_browser);
        context = this;

        initIntent();

        initViews();

        initData();

        initViewPager();

    }

    private void setWindowFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 19) {
            // 虚拟导航栏透明
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void initIntent() {
        imageUrlList = getIntent().getStringArrayListExtra(IntentKey_ImageList);
        currentPosition = getIntent().getIntExtra(IntentKey_CurrentPosition, 1);
        currentViewPagerTransform = getIntent().getIntExtra(IntentKey_ViewPagerTransformType, ViewPagerTransform_Default);
    }
    private Bitmap bitmap;
    private void initViews() {
        viewPagerBrowser = (MNViewPager) findViewById(R.id.viewPagerBrowser);
        mnGestureView = (MNGestureView) findViewById(R.id.mnGestureView);
        tvNumShow = (TextView) findViewById(R.id.tvNumShow);
        tvSave = (TextView) findViewById(R.id.save_img);
        rl_black_bg = (RelativeLayout) findViewById(R.id.rl_black_bg);

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SheetDialog ssd = new SheetDialog(context).builder();
                ssd.addSheetItem("保存到本地相册", SheetDialog.SheetItemColor.Red, new SheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        if (bitmap == null){
                            Toast.makeText(context,"图片正在载入，请稍后保存", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            Utils.saveFile(context,bitmap);
                        }catch (Exception e){

                        }
                    }
                });
                ssd.show();
            }
        });
    }

    private void initData() {
        tvNumShow.setText(String.valueOf((currentPosition + 1) + "/" + imageUrlList.size()));
    }

    private void initViewPager() {
        viewPagerBrowser.setAdapter(new MyAdapter());
        viewPagerBrowser.setCurrentItem(currentPosition);
        setViewPagerTransforms();
        viewPagerBrowser.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvNumShow.setText(String.valueOf((position + 1) + "/" + imageUrlList.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mnGestureView.setOnSwipeListener(new MNGestureView.OnSwipeListener() {
            @Override
            public void downSwipe() {
                finishBrowser();
            }

            @Override
            public void onSwiping(float deltaY) {
                tvNumShow.setVisibility(View.GONE);

                float mAlpha = 1 - deltaY / 500;
                if (mAlpha < 0.3) {
                    mAlpha = 0.3f;
                }
                if (mAlpha > 1) {
                    mAlpha = 1;
                }
                rl_black_bg.setAlpha(mAlpha);
            }

            @Override
            public void overSwipe() {
                tvNumShow.setVisibility(View.VISIBLE);
                rl_black_bg.setAlpha(1);
            }
        });
    }

    private void setViewPagerTransforms() {
        if (currentViewPagerTransform == ViewPagerTransform_Default) {
            viewPagerBrowser.setPageTransformer(true, new DefaultTransformer());
        } else if (currentViewPagerTransform == ViewPagerTransform_DepthPage) {
            viewPagerBrowser.setPageTransformer(true, new DepthPageTransformer());
        } else if (currentViewPagerTransform == ViewPagerTransform_RotateDown) {
            viewPagerBrowser.setPageTransformer(true, new RotateDownTransformer());
        } else if (currentViewPagerTransform == ViewPagerTransform_RotateUp) {
            viewPagerBrowser.setPageTransformer(true, new RotateUpTransformer());
        } else if (currentViewPagerTransform == ViewPagerTransform_ZoomIn) {
            viewPagerBrowser.setPageTransformer(true, new ZoomInTransformer());
        } else if (currentViewPagerTransform == ViewPagerTransform_ZoomOutSlide) {
            viewPagerBrowser.setPageTransformer(true, new ZoomOutSlideTransformer());
        } else if (currentViewPagerTransform == ViewPagerTransform_ZoomOut) {
            viewPagerBrowser.setPageTransformer(true, new ZoomOutTransformer());
        } else {
            viewPagerBrowser.setPageTransformer(true, new ZoomOutSlideTransformer());
        }
    }

    private void finishBrowser() {
        tvNumShow.setVisibility(View.GONE);
        rl_black_bg.setAlpha(0);
        finish();
//        this.overridePendingTransition(0, R.anim.browser_exit_anim);
    }

    @Override
    public void onBackPressed() {
        finishBrowser();
    }


    private class MyAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyAdapter() {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return imageUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View inflate = layoutInflater.inflate(R.layout.mn_image_browser_item_show_image, container, false);
            final PhotoView imageView = (PhotoView) inflate.findViewById(R.id.imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            RelativeLayout rl_browser_root = (RelativeLayout) inflate.findViewById(R.id.rl_browser_root);
            final ProgressWheel progressWheel = (ProgressWheel) inflate.findViewById(R.id.progressWheel);
//            final RelativeLayout rl_image_placeholder_bg = (RelativeLayout) inflate.findViewById(R.id.rl_image_placeholder_bg);
//            final ImageView iv_fail = (ImageView) inflate.findViewById(R.id.iv_fail);

            SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(final @NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    try {
                        progressWheel.setVisibility(View.GONE);
                        bitmap = resource;
                        imageView.setImageBitmap(resource);
                    } catch (Exception ex) {
                        Log.e("flag--", "onResourceReady: " + ex.getMessage());
                    }
                }
            };

            String url = imageUrlList.get(position);
            GlideApp.with(context)
                    .asBitmap()
                    .load(url)
                    .placeholder(R.mipmap.img_def)
                    .dontAnimate()
                    .error(R.mipmap.img_def)
                    .into(target);


            rl_browser_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishBrowser();
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishBrowser();
                }
            });

            container.addView(inflate);
            return inflate;
        }
    }

}
