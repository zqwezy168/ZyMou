package zy.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;

import zy.library.R;
import zy.library.utils.LocalConfig;
import zy.library.utils.SPUtil;
import zy.library.ui.ActionBgView;
import zy.library.utils.StatusBarUtil;


/**
 * Created by zy on 2018/3/12.
 */

public abstract class BaseZyActivity extends AppCompatActivity {

    private LoadingDailog dialog;

    private final SparseArray<View> mViews = new SparseArray<>();

    protected Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        activity = this;
//        setColor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        showKeyboard(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
                showKeyboard(false);
            }
        }
        return super.onTouchEvent(event);
    }

    //显示Dialog
    protected void showProgressBar(String content) {
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                .setMessage(content)
                .setCancelable(true)
                .setCancelOutside(true);
        dialog = loadBuilder.create();
        dialog.show();
    }

    //隐藏Dialog
    protected void progressBarDismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    //是否显示软键盘
    protected void showKeyboard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            if (getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(getCurrentFocus(), 0);
            }
        } else {
            if (getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    //显示Toast
    protected void showToastMsg(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /**
     * 描述：封装findViewById
     * 创建人：Justin
     * 创建时间：2016/7/1 17:48
     * 修改人：
     * 修改时间：2016/7/1 17:48
     * 修改备注：
     */
    private <T extends View> T findView(@IdRes int resId) {
        View view = mViews.get(resId);
        if (view == null) {
            view = findViewById(resId);
            mViews.put(resId, view);
        }
        return (T) view;
    }

    /**
     * 描述：优化的得到视图控件
     * 创建人：Justin
     * 创建时间：2016/7/19 15:36
     * 修改人：
     * 修改时间：2016/7/19 15:36
     * 修改备注：
     */
    protected <T extends View> T getViewById(@IdRes int resId) {
        return findView(resId);
    }


    /**
     * 状态栏相关
     */
    protected void setColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (color == 0) return;
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(this, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) this.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }


    protected void setColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            ActionBgView statusView = new ActionBgView(activity);
            int statusBarHeight = getStatusBarHeight();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    statusBarHeight);
            statusView.setLayoutParams(params);

            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) this.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    private View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int statusBarHeight = getStatusBarHeight();

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(getResources().getColor(color));
        return statusView;
    }

    public void setDefaultStatusBar() {
        setColor(R.color.deep_grey);
        StatusBarUtil.StatusBarLightMode(this);
    }

    public void setDefaultStatusBar_settings() {
        setColor(R.color.purely_white);
        StatusBarUtil.StatusBarLightMode(this);
    }

    public void setDefaultStatusBar_login() {
        setColor(R.color.colorPrimary);
        StatusBarUtil.StatusBarLightMode(this);
    }


    protected int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }



    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 4000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
