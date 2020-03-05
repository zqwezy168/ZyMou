package zy.library.ui.text;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import zy.library.R;


/**
 * Created by zy on 2016/10/14.
 */

public abstract class MyClickText extends ClickableSpan {

    private Context context;
    public MyClickText(Context context) {
        this.context = context;
    }

    /**
     * 客户电话变红并有超链接
     */
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        //设置文本的颜色
        ds.setColor(context.getResources().getColor(R.color.blue_light));
        //超链接是否有下划线
        ds.setUnderlineText(false);
    }

    public abstract void onClick(View widget);
}
