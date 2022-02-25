package com.lxl.config;

import android.os.Environment;

import java.io.File;

public class FlutterConfig {

    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.lxl" + File.separator;
    public static final String DEBUG_PATH = BASE_PATH + "debug/";
    public static final String RELEASE_PATH = BASE_PATH + "release/";
    public static final String BACK_PATH = BASE_PATH + "back/";

}
