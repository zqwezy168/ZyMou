package zy.library.ui.sheetdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Spanned;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import zy.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2016/9/28.
 */

public class SheetDialog {
    private Context context;
    private Display display;
    private Dialog dialog;
    private boolean showTitle;
    private List<SheetItem> sheetItemList;

    public SheetDialog(Context context){
        this.context = context;
        WindowManager windowManage = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManage.getDefaultDisplay();
    }

    private TextView title,cancle;
    private LinearLayout line;
    private ScrollView scrollView;
    public SheetDialog builder(){
        View view = LayoutInflater.from(context).inflate(R.layout.sheetdialog, null);
        title = (TextView) view.findViewById(R.id.s_sheetdialog_title);
        title.setVisibility(View.GONE);
        view.setMinimumWidth(display.getWidth());
        cancle = (TextView) view.findViewById(R.id.s_sheetdialog_cancle);
        line = (LinearLayout) view.findViewById(R.id.s_sheetdialog_line);
        scrollView = (ScrollView) view.findViewById(R.id.s_sheetdialog_scrollview);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        dialog = new Dialog(context, R.style.S_SheetDialog);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);//控制Dialog限制位置-----默认居中
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 0;
        window.setAttributes(lp);
        return this;
    }

    public SheetDialog setTitle(String string){
        showTitle = true;
        title.setText(string);
        title.setVisibility(View.VISIBLE);
        return this;
    }

    public SheetDialog setTitle(Spanned string){
        showTitle = true;
        title.setText(string);
        title.setVisibility(View.VISIBLE);
        return this;
    }
    public SheetDialog setCancelable(boolean cancel){//返回键
        dialog.setCancelable(cancel);
        return this;
    }
    public SheetDialog setCanceledOnTouchOutside(boolean cancel){
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public SheetDialog addSheetItem(String itemName, SheetItemColor color, OnSheetItemClickListener listener){
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.add(new SheetItem(itemName, color, listener));
        return this;
    }

    private void setSheetItems(){
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }
        int size = sheetItemList.size();
        //控制列表最大高度：   限定条目高度  < 最大高度  < 限定条目+1高度
        //否则列表显示不全
        if (size >= 8) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) line.getLayoutParams();
            params.height = display.getHeight()/2;
            scrollView.setLayoutParams(params);
        }
        for (int i = 1; i <= size ; i++) {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i-1);
            String itemName = sheetItem.name;
            SheetItemColor itemColor = sheetItem.color;
            final OnSheetItemClickListener listener = sheetItem.itemClickListener;
            TextView textView = new TextView(context);
            textView.setText(itemName);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
            if (size == 1) {
                if (showTitle) {
                    textView.setBackgroundResource(R.drawable.s_sheetdialog_bottom);
                }else {
                    textView.setBackgroundResource(R.drawable.s_sheetdialog_single);
                }
            }else {
                if (showTitle) {
                    if (i >= 1 && i < size) {
                        textView.setBackgroundResource(R.drawable.s_sheetdialog_middle);
                    } else {
                        textView.setBackgroundResource(R.drawable.s_sheetdialog_bottom);
                    }
                }else {
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.s_sheetdialog_top);
                    } else if (i < size) {
                        textView.setBackgroundResource(R.drawable.s_sheetdialog_middle);
                    } else {
                        textView.setBackgroundResource(R.drawable.s_sheetdialog_bottom);
                    }
                }
            }
            if (itemColor == null) {
                textView.setTextColor(Color.parseColor(SheetItemColor.Blue.getName()));
            }else {
                textView.setTextColor(Color.parseColor(itemColor.getName()));
            }
            float scale = context.getResources().getDisplayMetrics().density;//获取手机屏幕密度
            int height = (int) (50 * scale + 0.5f);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    listener.onClick(index);
                    dialog.dismiss();
                }
            });
            line.addView(textView);
        }
    }

    public interface OnSheetItemClickListener{
        void onClick(int which);
    }
    public class SheetItem{
        String name;
        OnSheetItemClickListener itemClickListener;
        SheetItemColor color;
        public SheetItem(String name, SheetItemColor color, OnSheetItemClickListener itemClickListener){
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }
    public enum SheetItemColor{
        Green("#ACC43D"),Blue("#037BFF"),Black("#000000"),Red("#FF6A6A");
        private String name;
        private SheetItemColor(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
    public void show(){
        setSheetItems();
        dialog.show();
    }

}
