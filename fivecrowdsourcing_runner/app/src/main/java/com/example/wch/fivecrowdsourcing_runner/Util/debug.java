package com.example.wch.fivecrowdsourcing_runner.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 调试用log
 */

public class debug {
    public static void d(String TAG, String msg) {
        if (Config.getDebug()) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (Config.getDebug()) {
            Log.e(TAG, msg);
        }
    }

    public static void toast(Context mContext, String msg) {
        if (Config.getDebug()) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
