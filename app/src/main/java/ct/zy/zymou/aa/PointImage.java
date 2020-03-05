package ct.zy.zymou.aa;

/**
 * Created by zy
 * on 2018/12/13.
 */
public class PointImage {

    public AnimationBean mAnimation;
    public PointPath mPointF;
    public  int selctimage;

    public int getSelctimage() {
        return selctimage;
    }

    public void setSelctimage(int selctimage) {
        this.selctimage = selctimage;
    }

    public AnimationBean getAnimation() {
        return mAnimation;
    }

    public void setAnimation(AnimationBean animation) {
        mAnimation = animation;
    }

    public PointPath getPointF() {
        return mPointF;
    }

    public void setPointF(PointPath pointF) {
        mPointF = pointF;
    }

}