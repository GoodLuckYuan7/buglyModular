package com.icegps.buglymodular;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.launcher.ARouter;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loadMyself;
    private Button loadA;
    private Button loadB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRequetPermission();
        initView();
    }

    private void initView(){
        loadMyself = (Button) findViewById(R.id.intent_Button);
        loadMyself.setOnClickListener(this);
        loadA = (Button) findViewById(R.id.intentA_Button);
        loadA.setOnClickListener(this);
        loadB = (Button) findViewById(R.id.intentB_Button);
        loadB.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.intent_Button:
//                startActivity(new Intent().setClass(MainActivity.this,MySelfActivity.class));
                ARouter.getInstance().build("/app/myself").navigation();
                break;
            case R.id.intentA_Button:
//                startActivity(new Intent().setClass(MainActivity.this,Module1Acitivity.class));
                ARouter.getInstance().build("/moduleA/mainActivity").navigation();
                break;
            case R.id.intentB_Button:
                ARouter.getInstance().build("/moduleB/mainActivity").navigation();
                break;
        }
    }

    private static final int NOT_NOTICE = 2;//如果勾选了不再询问
    private AlertDialog alertDialog;
    private AlertDialog mDialog;
    private String[] permissions = { // 权限数组
            Manifest.permission.INTERNET,              // 允许程序访问网络连接，可能产生GPRS流量
            Manifest.permission.ACCESS_WIFI_STATE,     // 允许程序获取当前WiFi接入的状态以及WLAN热点的信息
            Manifest.permission.READ_PHONE_STATE,      // 允许程序访问电话状态
            Manifest.permission.ACCESS_NETWORK_STATE,  // 允许程序获取网络信息状态，如当前的网络连接是否有效
            Manifest.permission.ACCESS_FINE_LOCATION,  // 允许程序通过GPS芯片接收卫星的定位信息
            Manifest.permission.ACCESS_COARSE_LOCATION,// 允许程序通过WiFi或移动基站的方式获取用户错略的经纬度信息
            Manifest.permission.READ_EXTERNAL_STORAGE, // 允许程序可以读取设备外部存储空间
            Manifest.permission.WRITE_EXTERNAL_STORAGE,// 允许程序写入外部存储,如SD卡上写文件
            Manifest.permission.WRITE_SETTINGS         // 允许程序读取或写入系统设置
    };

    // 动态申请权限
    private void myRequetPermission(){
        // 遍历权限数组，务必所有权限都申请通过
        for (int i = 0; i<permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {//选择了“始终允许”
                    continue;
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])){//用户选择了禁止不再询问

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("permission")
                                .setMessage("点击允许才可以使用我们的app哦")
                                .setPositiveButton("去允许", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (mDialog != null && mDialog.isShowing()) {
                                            mDialog.dismiss();
                                        }
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);//注意就是"package",不用改成自己的包名
                                        intent.setData(uri);
                                        startActivityForResult(intent, NOT_NOTICE);
                                    }
                                });
                        mDialog = builder.create();
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.show();



                    }else {//选择禁止
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("permission")
                                .setMessage("点击允许才可以使用我们的app哦")
                                .setPositiveButton("去允许", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (alertDialog != null && alertDialog.isShowing()) {
                                            alertDialog.dismiss();
                                        }
                                        ActivityCompat.requestPermissions(MainActivity.this,
                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                    }
                                });
                        alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    }

                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==NOT_NOTICE){
            myRequetPermission();//由于不知道是否选择了允许所以需要再次判断
        }
    }
}
