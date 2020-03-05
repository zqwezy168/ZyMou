package ct.zy.zymou;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.nanchen.compresshelper.CompressHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import zy.library.base.BaseZyActivity;

public class MainActivity extends BaseZyActivity {


    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.edit1)
    EditText edit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initWeb();
        requestPermission();

//        File file = new File(img_uri.get(i).getUrl());//filePath 图片地址
//        File newFile = CompressHelper.getDefault(this).compressToFile(file);

//        edit1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                showToastMsg(editable+" ...");
//            }
//        });
    }

    //添加headerview
//    private void addHeaderView() {
//        View view = LayoutInflater.from(this).inflate(R.layout.include_inspection_top, lv, false);
//        vh = new ViewHolder(view);
//        lv.addHeaderView(view);

////    动态添加控件
//         for (int i = 0; i < baseInfoBeans.size() ; i++){
//            View view = LayoutInflater.from(this).inflate(R.layout.item_base_info_text,lrMain, false);
//            LrViewHolder vh = new LrViewHolder(view);
//            vh.mTv1.setText(baseInfoBeans.get(i).getKey());
//            lrMain.addView(view,i);
//        }
//    //获取控件内容
//            for (int i = 0; i < lrMain.getChildCount() ; i++){
//                EditText editText = lrMain.getChildAt(i).findViewById(R.id.edt);
//                baseInfoBeans.get(i).setValue(editText.getText().toString().trim());
//            }

//    }
//
//    static class ViewHolder {
//        @BindView(R.id.tv_inspection_num)
//        TextView tvInspectionNum;
//        @BindView(R.id.tv_inspection_man)
//        TextView tvInspectionMan;
//        @BindView(R.id.tv_inspection_area)
//        TextView tvInspectionArea;
//        @BindView(R.id.tv_inspection_time)
//        TextView tvInspectionTime;
//        @BindView(R.id.tv_address)
//        TextView tvAddress;
//
//        ViewHolder(View view) {
//            ButterKnife.bind(this, view);
//        }
//    }


    private void initWeb() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDefaultTextEncodingName("GBK");
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webview.addJavascriptInterface(new InJavaScriptLocalObj(), "AndroidWebView");
        webview.clearCache(true);
        webSettings.setAllowFileAccess(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        //设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);

        //启用地理定位
        webSettings.setGeolocationEnabled(true);


        webview.setWebViewClient(new webViewClient());
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);

            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, true);
                super.onGeolocationPermissionsShowPrompt(origin, callback);

            }
        });

//        webview.loadUrl("file:///android_asset/test.html");

        webview.loadUrl("https://testinfo.aifound.cn/newDetail.html?id=1550481262623");

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInfoToJs();
            }
        });

    }

    private class webViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            // html加载完成之后，添加监听点击js函数
            //调用js中的函数：showInfoFromJava(msg)

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    }

    public class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showInfoFromJs(String html) {

            showToastMsg(html);
        }
    }

    //在java中调用js代码
    public void sendInfoToJs() {
        //调用js中的函数：showInfoFromJava(msg)
//        webview.loadUrl("javascript:showInfoFromJava('" + edit1.getText().toString() + "')");
        webview.loadUrl("javascript:handleFontChange()");
    }


    private void requestPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.REQUEST_INSTALL_PACKAGES,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });
    }
}
