package ct.zy.zymou.aa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ct.zy.zymou.R;

/**
 * Created by zy
 * on 2018/12/13.
 */
public class BezierView extends View {


    private static final String TAG = "BezierView";
    private Context context;
    private Paint mPaint;
    private float detlaX=0;
    private float detlaY=500;

    private int mWidth;

    private PointF point1;

    private PointF mPoint;
    private Bitmap mBitmap;

    private Bitmap mBitmap2;
    private Bitmap mBitmap3;

    private Random mRandom;
    private int select;
    private List<Bitmap> mBitmaps=new ArrayList<>();
    private int select2=1;

    private List<PointImage> listpointImage=new ArrayList<>();
    private Bitmap mBitmap4;
    private Bitmap mBitmap5;
    private Bitmap mBitmap6;
    private Bitmap mBitmap7;
    private Bitmap mBitmap8;
    private Bitmap mBitmap9;
    private Bitmap mBitmap10;
    private Bitmap mBitmap11;

    public BezierView(Context context) {
        super(context);
        this.context=context;
    }

    public BezierView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        this.context=context;
        init();
        initBitmap();
        initrandom();
    }

    private void initrandom() {
        mRandom = new Random();
    }

    private void initBitmap() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap2 = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        mBitmap3 = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        mBitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap5 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap6 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap7 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap8 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap9 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap10 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap11 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmaps.add(mBitmap);
        mBitmaps.add(mBitmap2);
        mBitmaps.add(mBitmap3);
        mBitmaps.add(mBitmap4);
        mBitmaps.add(mBitmap5);
        mBitmaps.add(mBitmap6);
        mBitmaps.add(mBitmap7);
        mBitmaps.add(mBitmap8);
        mBitmaps.add(mBitmap9);
        mBitmaps.add(mBitmap10);
        mBitmaps.add(mBitmap11);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=this.getMeasuredWidth();
    }


    /**
     * 初始化画笔 路径
     */
    private void init() {
        //画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(10f);
        mPaint.setTextSize(20f);
        mPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0;i<listpointImage.size();i++){
            PointImage pointImage=    listpointImage.get(i);
            Log.d(TAG, pointImage.getAnimation().progress+"---"+i);
            float    xAix=getYvalue(pointImage.getAnimation().progress,pointImage.getPointF().point1,pointImage.getPointF().pointC,pointImage.getPointF().point2).x;
            float   yAix=getYvalue(pointImage.getAnimation().progress,pointImage.getPointF().point1,pointImage.getPointF().pointC,pointImage.getPointF().point2).y;
            canvas.drawBitmap(mBitmaps.get(pointImage.getSelctimage()),xAix,yAix,mPaint);
            if (pointImage.getAnimation().progress>=1){
                listpointImage.remove(pointImage);
                pointImage=null;
                continue;
            }else {
                invalidate();
            }
        }


    }





    public PointF  getYvalue(float t, PointF point1,PointF point2, PointF point3){

        mPoint.x =(1-t)*(1-t)*point1.x+2*t*(1-t)*point2.x+t*t*point3.x;

        mPoint.y = (1-t)*(1-t)*point1.y+2*t*(1-t)*point2.y+t*t*point3.y;
        // 套用上面的公式把点返回
        return mPoint;

    }

    public void addimage() {

        PointImage pointImage=new PointImage();
        AnimationBean animation=AnimationFactor.getAnimation(context);
        //必须要随机的一个动画
        pointImage.setAnimation(animation);

        //随机的一个路径
        PointPath pointPath =new PointPath();
        pointPath.creatPointPath(this.getMeasuredWidth(),this.getMeasuredHeight(),mBitmap.getHeight());
        pointImage.setPointF(pointPath);

        //随机的图片
        pointImage.setSelctimage(mRandom.nextInt(3));
        listpointImage.add(pointImage);

        pointImage.getAnimation().mObjectAnimator.start();
        invalidate();

    }
}