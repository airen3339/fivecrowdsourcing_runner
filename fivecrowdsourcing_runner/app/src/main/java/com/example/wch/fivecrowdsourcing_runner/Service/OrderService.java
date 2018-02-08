package com.example.wch.fivecrowdsourcing_runner.Service;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.wch.fivecrowdsourcing_runner.Activity.MainActivity;
import com.example.wch.fivecrowdsourcing_runner.Gson.OrderInfo;
import com.example.wch.fivecrowdsourcing_runner.R;
import com.example.wch.fivecrowdsourcing_runner.Util.DealData;

import java.util.ArrayList;


/**
 * Created by zc on 2017/7/29.
 */

public class OrderService extends IntentService {
    /**
     * 构造函数
     */
    ArrayList<OrderInfo> unacceptedOrderInfo;
    ArrayList<OrderInfo> acceptedOrderInfo;
    ArrayList<OrderInfo> allotOrderInfo;
    OrderService thisService;


    public OrderService() {
        super("OrderService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new Notification.Builder(this).setContentTitle("外卖后台服务")
                .setContentText("正在检测新的订单消息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(0, notification);
        thisService = this;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (true) {
            try {
                DealData.getMyOrder();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void beginService(Context context) {
        Intent intent = new Intent(context, OrderService.class);
        context.startService(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
