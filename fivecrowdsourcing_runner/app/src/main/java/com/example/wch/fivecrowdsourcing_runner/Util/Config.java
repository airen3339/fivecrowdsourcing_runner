package com.example.wch.fivecrowdsourcing_runner.Util;

/**
 * Created by zc on 2017/7/28.
 */

public class Config {
    /*调试状态为true,发布时为false*/
    private static final boolean debug = true;
    private static final String url = "http://192.168.123.37/zyb";

    public static String getUrl() {
        return url;
    }

    public static boolean getDebug() {
        return debug;
    }
}
