package com.example.wch.fivecrowdsourcing_runner.Util;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.wch.fivecrowdsourcing_runner.Gson.Me;
import com.example.wch.fivecrowdsourcing_runner.Gson.OrderInfo;
import com.example.wch.fivecrowdsourcing_runner.Interface.DealResult;
import com.example.wch.fivecrowdsourcing_runner.db.OrderImage;
import com.example.wch.fivecrowdsourcing_runner.Interface.AfterLogin;
import com.example.wch.fivecrowdsourcing_runner.Interface.DownImage;
import com.example.wch.fivecrowdsourcing_runner.Interface.GetImage;
import com.example.wch.fivecrowdsourcing_runner.Interface.LoadOrder;
import com.example.wch.fivecrowdsourcing_runner.Interface.LoginSender;
import com.example.wch.fivecrowdsourcing_runner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2017/8/2.
 */

public class DealData {
    /**
     * 处理获取到的图片的response信息
     *
     * @param url
     * @param getImage
     */
    public static void getImage(@NonNull final String url, final GetImage getImage) {
        List<OrderImage> list = OrderImage.where("url=?", url).find(OrderImage.class);
        if (list.size() == 0) {//数据库不存在信息
            DealResponse.getImage(url, new DownImage() {
                @Override
                public void onSuccess(byte[] imageBytes) {
                    OrderImage orderImage = new OrderImage(url, imageBytes);
                    orderImage.save();
                    getImage.onSuccess(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
                    debug.d("Https", "从网络端获得图片");
                }

                @Override
                public void onFailure() {
                    getImage.onFailure();
                }
            });
        } else {//数据库存在信息
            debug.d("Https", "从数据库获得图片信息");
            byte[] imageBytes = list.get(0).getImageBytes();
            getImage.onSuccess(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
        }
    }

    /**
     * @param account
     * @param password
     */
    public static void getMe(final String account, final String password, final AfterLogin afterLogin) {
        DealResponse.getMe(account, password, new LoginSender() {
            @Override
            public void onFailure(String msg) {
                afterLogin.onFailure(msg);
               // afterLogin.onSuccess();
            }

            @Override
            public void onSuccess(Me me) {
                me.setPassword(password);
                me.setAccount(account);
                CacheData.setMe(me);
                afterLogin.onSuccess();

                /*me.setPassword("123456");
                me.setAccount("123456");
                CacheData.setMe(me);*/
            }
        });
    }

    /**
     * 获得自己的订单信息并自动刷新
     */
    public static void getMyOrder() {
        DealResponse.getMyOrder(new LoadOrder() {
            @Override
            public void onSuccess(@NonNull ArrayList<OrderInfo> orderInfoList) {
                ArrayList<OrderInfo> list = CacheData.getNeworderList();
                synchronized (list) {
                    list.addAll(orderInfoList);
                }
                MyApplication.sendNewOrderBroadcast();
                MyApplication.showNotification(0, "外卖订单", "您有新的订单任务选项", true, ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                        + MyApplication.getmContext().getPackageName() + "/" + R.raw.error_net);
            }

            @Override
            public void onFailure(String err) {
                MyApplication.showErrorNotification(err);
            }
        });
    }

    public static void arriveOrder(final Activity activity, final OrderInfo orderInfo) {
        DealResponse.arriveOrder(orderInfo.getmOrderId(), new DealResult() {
            @Override
            public void onSuccess() {
                ArrayList<OrderInfo> orderInfoList = CacheData.getNeworderList();
                synchronized (orderInfoList) {
                    orderInfoList.remove(orderInfo);
                }
                orderInfo.setmStatus(-1);
                orderInfo.save();//接单成功,存储到数据库
                if (orderInfo.isSaved()) {
                    Log.d("test", "onSuccess: 存储成功");
                } else {
                    Log.d("test", "onSuccess: 失败");
                }
                MyApplication.sendNewOrderBroadcast();
            }

            @Override
            public void onFailure(@NonNull final String msg) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyApplication.toast(msg);
                    }
                });
            }
        });
    }
}
