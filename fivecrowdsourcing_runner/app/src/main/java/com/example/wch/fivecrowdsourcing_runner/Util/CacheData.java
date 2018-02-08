package com.example.wch.fivecrowdsourcing_runner.Util;

import com.example.wch.fivecrowdsourcing_runner.Gson.Me;
import com.example.wch.fivecrowdsourcing_runner.Gson.OrderInfo;

import java.util.ArrayList;

/**
 * Created by zc on 2017/8/3.
 */

public class CacheData {
    private static ArrayList<OrderInfo> neworderList = new ArrayList<>();
    private static ArrayList<OrderInfo> oldorderList = new ArrayList<>();
    private static Me me;

    public static ArrayList<OrderInfo> getNeworderList() {
        return neworderList;
    }

    public static ArrayList<OrderInfo> getOldorderList() {
        return oldorderList;
    }

    public static Me getMe() {
        return me;
    }


    public static void setMe(Me me) {
        CacheData.me = me;
    }
}
