package com.example.wch.fivecrowdsourcing_runner.Util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


import com.example.wch.fivecrowdsourcing_runner.Activity.MainActivity;
import com.example.wch.fivecrowdsourcing_runner.R;

import org.litepal.LitePalApplication;

/**
 * Created by zc on 2017/8/2.
 */

public class MyApplication extends LitePalApplication {
    static Context mContext;
    static Notification.Builder builder;
    static NotificationManager manager;
    private static Toast toast;
    /**
     * 新订单信息的广播action
     */
    public final static String BROADCAST_NEW_UNACCEPTED = "NEW_UNACCEPTED_DATA";

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getmContext() {
        return mContext;
    }

    /**
     * 发送通知
     */
    public static void showNotification(@NonNull int id, String title, String msg, boolean insistent, @NonNull String uri) {
        if (builder == null) {
            builder = new Notification.Builder(mContext);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
            Intent intent = new Intent(mContext, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, intent, 0);
            builder.setContentIntent(pendingIntent);
        }
        if (manager == null) {
            manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        }
        builder.setSound(Uri.parse(uri));
        builder.setContentText(msg);
        builder.setContentTitle(title);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        if (insistent) {//如果是重要消息,则一直提示
            notification.flags |= Notification.FLAG_INSISTENT;
        }
        manager.notify(id, notification);
    }

    public static void showErrorNotification(String msg) {
        showNotification(2, "警告", msg, true, ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + mContext.getPackageName() + "/" + R.raw.error_net);
    }

    public static void toast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 发送广播,有新的未接订单
     */
    public static void sendNewOrderBroadcast() {
        Intent intent = new Intent(BROADCAST_NEW_UNACCEPTED);
        mContext.sendBroadcast(intent);
    }
}
