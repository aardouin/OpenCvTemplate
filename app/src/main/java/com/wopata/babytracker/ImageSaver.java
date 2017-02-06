package com.wopata.babytracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by WOPATA on 03/02/17.
 */

public class ImageSaver {

    private HandlerThread handlerThread;
    private Handler handler;

    public ImageSaver() {
        super();
        handlerThread = new HandlerThread("test");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    public void saveImage(final long startAt, final Context context, final Mat mat){
        handler.post(new Runnable() {
            @Override
            public void run() {
                String filename = String.format("missed%d.png",startAt);
                File file = new File(context.getExternalFilesDir(null), filename);

                Bitmap bmp = Bitmap.createBitmap(mat.width(),mat.height(),Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(mat,bmp);
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            out.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

}
