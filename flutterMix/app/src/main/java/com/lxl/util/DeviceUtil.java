package com.lxl.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class DeviceUtil {

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }


}
