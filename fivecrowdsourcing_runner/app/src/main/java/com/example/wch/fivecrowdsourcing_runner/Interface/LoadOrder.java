package com.example.wch.fivecrowdsourcing_runner.Interface;

import android.support.annotation.NonNull;

import com.example.wch.fivecrowdsourcing_runner.Gson.OrderInfo;

import java.util.ArrayList;

/**
 * 下载自己的订单
 */

public interface LoadOrder {
    public void onSuccess(@NonNull ArrayList<OrderInfo> orderInfoList);

    public void onFailure(String err);
}
