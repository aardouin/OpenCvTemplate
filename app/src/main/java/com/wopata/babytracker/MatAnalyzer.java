package com.wopata.babytracker;


import android.content.Context;

import org.opencv.core.Mat;

import static org.opencv.core.CvType.CV_8UC4;

/**
 * Created by WOPATA on 03/02/17.
 */

class MatAnalyzer {

    private final Context context;
    private Mat mRgba;

    public MatAnalyzer(int width, int height, Context context) {
        mRgba = new Mat(height, width, CV_8UC4);
        this.context = context;
    }

    public Mat analyzeMat(Mat mat) {
        mRgba = mat;
        return mRgba;
    }

    public void release() {
        mRgba.release();
        mRgba = null;
    }
}
