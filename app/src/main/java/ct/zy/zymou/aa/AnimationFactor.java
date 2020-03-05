package ct.zy.zymou.aa;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;

import java.util.Random;
/**
 * Created by zy
 * on 2018/12/13.
 */
public class AnimationFactor {


    private static String TAG="AnimationFactor";
    public  static    Random mRandom=new Random();
    private  float progresscount3;

    public  static AnimationBean  getAnimation(Context context){
        final   AnimationBean  animationBean=new AnimationBean();

        ObjectAnimator animator = ObjectAnimator.ofFloat(context, "z", 0, 1 );
        animator.setDuration(mRandom.nextInt(6000)+1000);
        animator.setRepeatCount(0);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                animationBean.progress=(float)animation.getAnimatedValue();
                Log.d(TAG,animationBean.progress+"");
            }
        });
        animationBean.mObjectAnimator=animator;

        return  animationBean;
    }


}