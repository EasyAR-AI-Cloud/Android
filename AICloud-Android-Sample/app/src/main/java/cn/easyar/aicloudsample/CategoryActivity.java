package cn.easyar.aicloudsample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cn.easyar.dl.Recognizer;

public class CategoryActivity extends AppCompatActivity {

    public static final String CATEGORY = "cn.easyar.aicloudsample.recognize.category";
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        if (!hasCameraPermisson())
            requestPermission();
    }

    public void onBodyPoseBtnClicked(View view) {
        if (!hasCameraPermisson()) {
            showToast();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(CATEGORY, Recognizer.RECOGNIZE_CATEGORY_BODY_POSE);
        startActivity(intent);
    }

    public void onHandGestureBtnClicked(View view) {
        if (!hasCameraPermisson()) {
            showToast();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(CATEGORY, Recognizer.RECOGNIZE_CATEGORY_HAND_GESTURE);
        startActivity(intent);
    }

    private boolean hasCameraPermisson() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    private boolean requestPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                //mLiveViewHolder.addCallback(mSurfaceCallback);
                Log.i("CategoryActivity", "permission granted!");
            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                showToast();
                this.finish();
            }
        }
    }

    private void showToast() {
        Toast.makeText(this,
                "Camera permission is needed to run this application", Toast.LENGTH_LONG).show();
    }
}
