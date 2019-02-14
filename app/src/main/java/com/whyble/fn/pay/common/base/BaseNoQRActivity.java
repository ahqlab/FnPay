package com.whyble.fn.pay.common.base;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.whyble.fn.pay.common.SharedPrefManager;

public abstract class BaseNoQRActivity<D extends Activity> extends AppCompatActivity {

    protected final String TAG = "HJLEE";

    public SharedPrefManager mSharedPrefManager;

    public static final int CAMERA_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPrefManager = SharedPrefManager.getInstance(getActivityClass());
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView qrcode = toolbar.findViewById(R.id.qrcode);
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
                if (permissionCamera == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivityClass(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                    Toast.makeText(getApplicationContext(), getString(R.string.required_permission_message), Toast.LENGTH_SHORT).show();
                } else {
                    openCamera();
                }
            }
        });*/
    }

    protected abstract BaseNoQRActivity<D> getActivityClass();

    public void setToolbarColor() {
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.hodoo_pink), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
            }
        });*/
    }



    public AlertDialog.Builder showBasicOneBtnPopup(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivityClass());
        if(title != null){
            builder.setTitle(title);
        }
        if(message != null){
            builder.setMessage(message);
        }
        return builder;
    }

    public void showToast(String message) {
        Toast.makeText(getActivityClass(), message, Toast.LENGTH_SHORT).show();
    }

    public void moveIntent(Context packageContext, Class<?> cls, int enterAnim, int exitAnim, boolean kill){
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
        overridePendingTransition(enterAnim , exitAnim);
        if(kill){
            finish();
        }
    }

    @Override
    protected void onStart() {
        Log.v(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.v(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.v(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.v(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
    }
}
