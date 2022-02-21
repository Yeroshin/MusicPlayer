package com.ys.musicplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import javax.inject.Inject;

public class YSNotification implements INotification{
    private Service context;
    private Notification notification;
    private NotificationManagerCompat notificationManager;
    private int start_type;
    public static final int start_type_alarm=0;
    public static final int start_type_notification=0;


    public YSNotification() {


    }
    @Override
    public Notification getNotification(Service context,RemoteViews remoteView){
        this.context=context;
        notificationManager = NotificationManagerCompat.from(context);
        String CHANNEL_ID;
        int importance;
        if(start_type==start_type_alarm){//|start_type==start_type_widget
            CHANNEL_ID = "12345677";
            importance= NotificationManager.IMPORTANCE_HIGH;
        }else{
            CHANNEL_ID = "12345678";
            importance=NotificationManager.IMPORTANCE_NONE;
        }


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = notificationManager.getNotificationChannel(CHANNEL_ID);
            // notificationManager.deleteNotificationChannel(CHANNEL_ID);
            if(channel==null){
                channel = new NotificationChannel(CHANNEL_ID, "YESPlayer", importance);
                channel.setSound(null, null);
                // channel.setDescription("now playing");
                // NotificationManager notificationManager = getSystemService(NotificationManager.class);
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                notificationManager.createNotificationChannel(channel);
            }


        }

        ////////////////////////////////////////////////

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                //.setContentTitle("myNotification")
                // .setContentText("hello")
                // .setVibrate(new long[0])
                .setSmallIcon(R.drawable.noti_icon)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                //.setOngoing(true)
                .setCustomContentView(remoteView)
                .setCustomBigContentView(remoteView)
                //.setContent(notificationLayout)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)////Check It!!!
                .setPriority(importance);

        notification=builder.build();
        return notification;
    }
    @Override
    public void updateNotification(RemoteViews remoteView){
        notificationManager.notify(777, getNotification(context,remoteView));
    }
}
