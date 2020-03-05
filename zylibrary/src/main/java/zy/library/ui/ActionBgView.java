package zy.library.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import zy.library.R;


/**
 * Created by zy on 2018/5/3.
 * 背景色
 *
 */

public class ActionBgView extends View {

    public ActionBgView(Context context) {
        super(context);
    }

    public ActionBgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ActionBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取View的宽高
        int width = getWidth();
        int height = getHeight();

        int color1 = getResources().getColor(R.color.turn_color1);
        int color2 = getResources().getColor(R.color.turn_color2);
        int color3 = getResources().getColor(R.color.turn_color3);
        int color4 = getResources().getColor(R.color.turn_color4);

        Paint paint = new Paint();
        LinearGradient backGradient = new LinearGradient(0, 0, width, height, new int[]{color1, color2 ,color3,color4}, null, Shader.TileMode.CLAMP);
//        LinearGradient backGradient = new LinearGradient(0, 0, 0, height, new int[]{colorStart, color1 ,colorEnd}, null, Shader.TileMode.CLAMP);
        paint.setShader(backGradient);
        canvas.drawRect(0, 0, width, height, paint);
    }
}
