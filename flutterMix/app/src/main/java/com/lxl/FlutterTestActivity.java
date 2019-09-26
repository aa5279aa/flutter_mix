package com.lxl;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxl.config.FlutterConfig;
import com.lxl.util.DeviceUtil;
import com.lxl.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.flutter.facade.Flutter;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterView;

public class FlutterTestActivity extends FragmentActivity implements View.OnClickListener {

    public static final String FlutterToAndroidChannel = "com.lxl.toandroid/plugin";
    public static final String AndroidToFlutterChannel = "com.lxl.toflutter/plugin";
    public volatile int  mCount;//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.flutter_test_main);


        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button_plus).setOnClickListener(this);

        try {
            Bundle metaData = getApplication().getPackageManager().getApplicationInfo(getApplication().getPackageName(), PackageManager.GET_META_DATA).metaData;
            String flutter = getApplication().getDir("flutter", 0).getPath();
            Log.i("lxltest", "flutter:" + flutter);
            File filesDir = getFilesDir();
            Log.i("lxltest", "filesDir:" + filesDir.getAbsolutePath());
            Log.i("lxltest", "isApkInDebug:" + DeviceUtil.isApkInDebug(this));

            String dataPath = getFilesDir().getParentFile().getAbsolutePath();
            File flutter_assets = new File(dataPath + File.separator + "app_flutter");
            if (flutter_assets.exists() && flutter_assets.isDirectory()) {
                for (File file : flutter_assets.listFiles()) {
                    Log.i("lxltest", "file:" + file.getName() + ",file time:" + DateUtil.calendar2Time(file.lastModified(), DateUtil.SIMPLEFORMATTYPESTRING2));
                }
            }
            File rootFile = flutter_assets.getParentFile().getParentFile();
            List<File> fileList = new ArrayList<>();
            FileUtil.getAllFileFromFolder(rootFile, fileList);
            for (File f : fileList) {
                Log.i("lxltest", "name:" + f.getName() + ",path:" + f.getAbsolutePath());
            }


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        addFlutterView("route3");
//        getAssets()
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button1) {
            addFlutterView("route1");
        } else if (id == R.id.button2) {

        } else if (id == R.id.button_plus) {
            ((TextView) findViewById(R.id.show_text)).setText("计数：" + String.valueOf(++mCount));
            //通知flutter
            notifyFlutter();
        } else if (id == R.id.button3) {
            useHotfix1();
        } else if (id == R.id.button4) {
            clearHotfix();
        }
    }

    private void useHotfix1() {
        //拷贝sd卡上的内容到data文件夹


        //debug的热更新会有问题，release包OK的。
        if (DeviceUtil.isApkInDebug(this)) {
            //debug包中包含三个文件，替换掉不行
            String dataPath = getFilesDir().getParentFile().getAbsolutePath();
            File flutter_assets = new File(dataPath + File.separator + "app_flutter/flutter_assets");
            File vm_snapshot_data = new File(FlutterConfig.DEBUG_PATH + "vm_snapshot_data");
            File isolate_snapshot_data = new File(FlutterConfig.DEBUG_PATH + "isolate_snapshot_data");


            Log.i("lxltest", "vm_snapshot_data:" + DateUtil.calendar2Time(vm_snapshot_data.lastModified(), DateUtil.SIMPLEFORMATTYPESTRING2));
            Log.i("lxltest", "isolate_snapshot_data:" + DateUtil.calendar2Time(isolate_snapshot_data.lastModified(), DateUtil.SIMPLEFORMATTYPESTRING2));


            File to_vm_snapshot_data = new File(flutter_assets.getAbsolutePath() + File.separator + "vm_snapshot_data");
            File to_isolate_snapshot_data = new File(flutter_assets.getAbsolutePath() + File.separator + "isolate_snapshot_data");

            try {
                FileUtil.copyFile(vm_snapshot_data, to_vm_snapshot_data);
                FileUtil.copyFile(isolate_snapshot_data, to_isolate_snapshot_data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //release包的话，需要替换整个flutter_assets文件夹
            String dataPath = getFilesDir().getParentFile().getAbsolutePath();
            File inFlutterAssets = new File(dataPath + File.separator + "app_flutter");

            String path = FlutterConfig.RELEASE_PATH + "flutter_assets";
            File hotfixFolder = new File(path);

            File backFile = new File(FlutterConfig.BACK_PATH + "flutter_assets");
            if (!backFile.exists()) {
                backFile.mkdirs();
            }

            try {
                //拷贝当前的数据到back文件中
                FileUtil.copyFolder(inFlutterAssets, backFile);

                //sd卡中的数据拷贝到data中
                FileUtil.copyFolder(hotfixFolder, inFlutterAssets);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        Toast.makeText(this, "热修成功，重启生效！", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        //录音获取麦克风声音
        String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;

//        Permissions.RECORD_AUDIO;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{writeExternalStorage}, 1);
        }

    }

    private void clearHotfix() {
        //release包的话，需要替换整个flutter_assets文件夹
        String dataPath = getFilesDir().getParentFile().getAbsolutePath();
        File inFlutterAssets = new File(dataPath + File.separator + "app_flutter");
        String backPath = FlutterConfig.BACK_PATH + "flutter_assets";
        try {
            //sd卡中备份数据拷贝到data中
            FileUtil.copyFolder(new File(backPath), inFlutterAssets);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Toast.makeText(this, "恢复成功，重启生效！", Toast.LENGTH_LONG).show();
    }

    FlutterView mFlutterView;
    EventChannel mEventChannel;
    EventChannel.EventSink mEventSink;

    private void addFlutterView(String title) {
        LinearLayout container = findViewById(R.id.flutter_container);
        container.removeAllViews();
        mFlutterView = Flutter.createView(this, getLifecycle(), title);
        container.addView(mFlutterView);

        MethodChannel methodChannel = new MethodChannel(mFlutterView, FlutterToAndroidChannel);
        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {

                if (methodCall.method.equalsIgnoreCase("withParams")) {
                    Integer count = methodCall.argument("count");
                    mCount = count;
                    //计数起加1
                    ((TextView) findViewById(R.id.show_text)).setText("计数：" + String.valueOf(mCount));
                    result.success("success");
                }
            }
        });
        mEventChannel = new EventChannel(mFlutterView, AndroidToFlutterChannel);
        mEventChannel.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object o, EventChannel.EventSink eventSink) {
                int count = mCount;
                mEventSink = eventSink;
                eventSink.success(count);
            }

            @Override
            public void onCancel(Object o) {

            }
        });
    }

    private void notifyFlutter() {
        if (mEventChannel == null) {
            return;
        }
        mEventSink.success(mCount);
    }

}
