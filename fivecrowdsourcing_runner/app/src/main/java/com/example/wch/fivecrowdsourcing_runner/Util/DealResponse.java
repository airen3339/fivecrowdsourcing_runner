package com.example.wch.fivecrowdsourcing_runner.Util;

import android.support.annotation.NonNull;

import com.example.wch.fivecrowdsourcing_runner.Gson.Me;
import com.example.wch.fivecrowdsourcing_runner.Gson.OrderInfo;
import com.example.wch.fivecrowdsourcing_runner.Interface.DealResult;
import com.example.wch.fivecrowdsourcing_runner.Interface.DownImage;
import com.example.wch.fivecrowdsourcing_runner.Interface.LoadOrder;
import com.example.wch.fivecrowdsourcing_runner.Interface.LoginSender;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zc on 2017/8/2.
 */

public class DealResponse {

    /**
     * 从网络端获取图片信息
     *
     * @param url
     * @param downImage
     */

    public static void getImage(@NonNull String url, final DownImage downImage) {
        HttpUtil.sendRequest(url, "", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                downImage.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] data = response.body().bytes();
                downImage.onSuccess(data);
            }
        });
    }

    /**
     * 获得配送员个人信息
     *
     * @param account
     * @param password
     * @param loginSender
     */
    public static void getMe(String account, String password, final LoginSender loginSender) {
        Map map = new HashMap();
        map.put("account", account);
        map.put("password", password);
        JSONObject jsonObject = new JSONObject(map);
        HttpUtil.sendRequest(Config.getUrl() + "/login", jsonObject, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                if (loginSender != null) {
                    loginSender.onFailure(null);
                } else {
                    MyApplication.toast("登录异常,请重试");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                try {
                    JSONObject data = new JSONObject(jsonData);
                    String msg = data.optString("msg");
                    if (!"ok".equals(msg)) {//登录失败
                        if (loginSender != null) {
                            loginSender.onFailure(msg);
                        } else {
                            MyApplication.toast(msg);
                        }
                    } else {//登录成功
                        Gson gson = new Gson();
                        Me me = gson.fromJson(jsonData, Me.class);
                        if (me != null) {
                            loginSender.onSuccess(me);
                        } else {
                            if (loginSender != null) {
                                loginSender.onFailure(null);
                            } else {
                                MyApplication.toast(msg);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 返回自己的订单列表
     * </br></br>
     * 若成功 onSuccess(@NonNull Arraylist);
     * 若失败 onFailure(@NonNull String);
     *
     * @param loadOrder
     */
    public static void getMyOrder(@NonNull final LoadOrder loadOrder) {
        Map<String, Object> map = new HashMap();
        map.put("token", CacheData.getMe().getToken());
        final JSONObject jsonObject = new JSONObject(map);
        HttpUtil.sendRequest(Config.getUrl() + "/getMyOrder", jsonObject, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadOrder.onFailure(e.getMessage().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String strResponse = response.body().string();
                debug.d("test", "接收到" + strResponse);
                Gson gson = new Gson();
                ArrayList<OrderInfo> list = gson.fromJson(strResponse, new TypeToken<ArrayList<OrderInfo>>() {
                }.getType());
                if (list != null && list.size() > 0) {
                    loadOrder.onSuccess(list);
                }
            }
        });
    }

    public static void arriveOrder(String orderId, final DealResult dealResult) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", CacheData.getMe().getToken());
        map.put("orderId", orderId);
        JSONObject jsonObject = new JSONObject(map);
        HttpUtil.sendRequest(Config.getUrl() + "/arriveOrder", jsonObject, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dealResult.onFailure(e.getMessage().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String strData = response.body().string();
                try {
                    JSONObject jsonData = new JSONObject(strData);
                    if (jsonData != null) {
                        String msg = jsonData.optString("msg");
                        if (msg != null && !"ok".equals(msg)) {//发生错误
                            dealResult.onFailure(msg);
                        } else {//成功
                            dealResult.onSuccess();
                        }
                    } else {
                        dealResult.onFailure("返回数据为空");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dealResult.onFailure(e.getMessage().toString());
                }
            }
        });


    }
}
