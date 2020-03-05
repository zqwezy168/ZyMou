package zy.library.ui.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import zy.library.R;


/**
 * Created by zy on 2017/8/4.
 */

public class ColorTurnText extends TextView {

    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private Paint mPaint;
    private int mViewWidth = 0;

    public ColorTurnText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0)
        {
            // getWidth得到是某个view的实际尺寸.
            // getMeasuredWidth是得到某view想要在parent view里面占的大小.
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0)
            {
                mPaint = getPaint();
                // 线性渐变

                mLinearGradient=new LinearGradient(0,0,w,h, getResources().getColor(R.color.turn_color1), getResources().getColor(R.color.turn_color2), Shader.TileMode.MIRROR);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

    }
}
