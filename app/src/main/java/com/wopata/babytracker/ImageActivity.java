package com.wopata.babytracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static org.opencv.core.CvType.CV_8UC4;


public class ImageActivity extends AppCompatActivity{

    @BindView(R.id.image)
    ImageView imageView;

    private MatAnalyzer analyzer;
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Timber.i("OpenCV loaded successfully");
                    mOriginal = new Mat(mBmp.getWidth(),mBmp.getHeight(),CV_8UC4);
                    analyzer = new MatAnalyzer(mBmp.getWidth(), mBmp.getHeight(),ImageActivity.this);
                    Utils.bitmapToMat(mBmp,mOriginal);
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    private Mat mOriginal;
    private Bitmap mBmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
        mBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.sample_foosball)).getBitmap();
    }


    public Mat analyzeMat(Mat mat) {
        return analyzer.analyzeMat(mat);
    }

    @OnClick(R.id.image)
    public void onClick(){
        Utils.bitmapToMat(mBmp,mOriginal);

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(mBmp.getWidth(), mBmp.getHeight(), conf); // this creates a MUTABLE bitmap

        Utils.matToBitmap(analyzeMat(mOriginal),bmp);
        imageView.setImageBitmap(bmp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item:
                startActivity(new Intent(this,MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
