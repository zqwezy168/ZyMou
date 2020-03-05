package ct.zy.zymou.aa;
import android.graphics.PointF;

import java.util.Random;
/**
 * Created by zy
 * on 2018/12/13.
 */
public class PointPath {
    public  PointF point1;
    public  PointF point2;
    public  PointF pointC;
    public   Random mRandom =new Random();
    /*
    *       point1 =new PointF(this.getMeasuredWidth()/2,this.getMeasuredHeight()-10);
            point2 =new PointF(this.getMeasuredWidth()/2,-80);
            pointC1 =new PointF(this.getMeasuredWidth()/2-200,this.getMeasuredHeight()/2);

    *
    *
    * */
    public  void creatPointPath(int viewwidth,int  viewHeight,int bitmapwidth) {

        point1 =new PointF(viewwidth/2,viewHeight-10);
        point2 =new PointF(mRandom.nextInt(viewwidth),-bitmapwidth);
        pointC =new PointF(viewwidth-200,viewHeight/2);


    }
}
