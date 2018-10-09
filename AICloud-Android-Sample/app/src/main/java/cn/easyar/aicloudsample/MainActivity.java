package cn.easyar.aicloudsample;

import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.os.Handler;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.easyar.dl.BodyPoseFactory;
import cn.easyar.dl.HandGestureFactory;
import cn.easyar.dl.RecognizeCallback;
import cn.easyar.dl.Recognizer;

public class MainActivity extends AppCompatActivity {

    private SurfaceView mLiveView;
    private SurfaceHolder mLiveViewHolder;
    private Camera mCamera;
    private TextView mTipTextView;
    private int catgeory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLiveView = findViewById(R.id.live_view);
        mLiveViewHolder = mLiveView.getHolder();
        mTipTextView = findViewById(R.id.tipTextView);
        catgeory = this.getIntent().getIntExtra(CategoryActivity.CATEGORY, 0);
        mLiveViewHolder.addCallback(mSurfaceCallback);

        //step1:init
        HandGestureFactory.init("", "");//add your key and secret here
        BodyPoseFactory.init("", "");

    }


    private SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            log("surfaceCreated");
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            log("surfaceChanged");
            initLiveView();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            log("surfaceDestroyed");

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
        mCamera = Camera.open();
		
		new Handler().post(new Runnable() {
		    @Override
		    public void run() {
		         mCamera.autoFocus(null);
		  	}
		 });
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");

        mCamera.stopPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera.release();
        mCamera = null;
        HandGestureFactory.release();
        BodyPoseFactory.release();
    }

    public void requestPictureSize(int width, int height) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureSize(width, height);
        List<Camera.Size> lists = parameters.getSupportedPreviewSizes();
        for (Camera.Size size : lists) {
            log("w=" + size.width + ",h=" + size.height);
            if (size.width * 100 / size.height == width * 100 / height) {
                log("Aspect Ratio match! Setting Preview size to : " + size.width + "x" + size.height);
                parameters.setPreviewSize(size.width, size.height);
                mCamera.setParameters(parameters);
                //return;
            }
        }
        log("Optimal preview size not found, falling back to default");
    }

    private static String covertMapToJSON(Map map){
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        JSONObject jsonObject=new JSONObject();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            entry.getKey();
            entry.getValue();
            try {
                jsonObject.put(entry.getKey(),entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString().replace("{","{\n").replace("}","\n}").replace(",",",\r\n");
    }

    public void initLiveView() {
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            log("orientation is not portrait, skip");
            return;
        }
        if (mCamera == null) {
            log("initPreview called when mCamera is not available. (before onResume)");
            return;
        }
        try {
            mCamera.setDisplayOrientation(90); //设定相机显示方向
            mCamera.setPreviewDisplay(mLiveViewHolder);
            mCamera.setPreviewCallback(previewCallback);
        } catch (Exception e) {
            log(e.getMessage());
        }
        mCamera.startPreview();
    }

    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] bytes, Camera camera) {
            // data length = preview width x preview height * 1.5
            //log("onPreviewFrame: " + System.currentTimeMillis() + " data length: " + bytes.length);
            Camera.Size size = camera.getParameters().getPreviewSize();
            switch (catgeory) {
                case Recognizer.RECOGNIZE_CATEGORY_BODY_POSE:
                    BodyPoseFactory.recognizeBuffer(bytes, size.width, size.height, new RecognizeCallback<Map>() {
                        @Override
                        public void onCompleted(Map result) {
                            mTipTextView.setText(covertMapToJSON(result));
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Log.e("onError", throwable.toString());
                            mTipTextView.setText(throwable.toString());

                        }
                    });
                    break;
                case Recognizer.RECOGNIZE_CATEGORY_HAND_GESTURE:
                    HandGestureFactory.recognizeBuffer(bytes, size.width, size.height, new RecognizeCallback<Map>() {
                        @Override
                        public void onCompleted(Map result) {
                            mTipTextView.setText(covertMapToJSON(result));

                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Log.e("onError", throwable.toString());
                            mTipTextView.setText(throwable.toString());

                        }
                    });
                    break;
            }

        }
    };

    private void log(String s) {
        Log.i("MainActivity", s);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCamera.autoFocus(null);
        return super.onTouchEvent(event);
    }
}
