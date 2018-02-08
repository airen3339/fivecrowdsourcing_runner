package com.example.wch.fivecrowdsourcing_runner.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zc on 2017/7/26.
 */

public class HttpUtil {
    public static void sendRequest(String url, String jsonData, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        RequestBody requestBody = new FormBody.Builder().add("data", jsonData).build();
        builder.url(url).post(requestBody);
        Request request = builder.build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendRequest(String url, JSONObject jsonObject, Callback callback) {
        String jsonData = jsonObject.toString();
        sendRequest(url, jsonData, callback);
    }

    public static void sendRequest(String url, JSONArray jsonArray, Callback callback) {
        String jsonData = jsonArray.toString();
        sendRequest(url, jsonData, callback);
    }
}
