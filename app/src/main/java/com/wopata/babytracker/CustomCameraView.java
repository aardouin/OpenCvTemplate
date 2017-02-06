package com.wopata.babytracker;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;

import org.opencv.android.JavaCameraView;

/**
 * Created by WOPATA on 02/02/17.
 */

public class CustomCameraView extends JavaCameraView {

    public CustomCameraView(Context context, int cameraId) {
        super(context, cameraId);
    }

    public CustomCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Camera getCamera(){
        mCamera.getParameters().setPreviewFpsRange(24000,120000);
        return mCamera;
    }
}
