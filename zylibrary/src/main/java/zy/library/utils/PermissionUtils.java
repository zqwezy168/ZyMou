package zy.library.utils;

import android.hardware.Camera;

/**
 * Created by zy on 2018/3/27.
 */

public class PermissionUtils {

    public static boolean isCameraUseable() {

        boolean canUse =true;

        Camera mCamera =null;

        try{

            mCamera = Camera.open();

// setParameters 是针对魅族MX5。MX5通过Camera.open()拿到的Camera对象不为null

            Camera.Parameters mParameters = mCamera.getParameters();

            mCamera.setParameters(mParameters);

        }catch(Exception e) {

            canUse =false;

        }

        if(mCamera !=null) {

            mCamera.release();

        }
        return canUse;

    }
}
