package com.ys.musicplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
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

import com.ys.musicplayer.utils.ServiceMessenger;

public class YESNotification  extends BroadcastReceiver {
    private Context context;
    private RemoteViews remoteView;
    private Notification notification;
    private int importance;
    private boolean playingState;
    private String title;

    private NotificationManagerCompat notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context.getApplicationContext();
        if(intent.getStringExtra(MyService.IMPORTANCE)!=null){
            importance=Integer.parseInt(intent.getStringExtra(MyService.IMPORTANCE));
        }else{
            importance=1;
        }
       // NotificationManager.IMPORTANCE_HIGH
        setImportance(importance);

        if(intent.getAction().equals(MyService.PLAYER_TRACK_INFO)){
            title=intent.getStringExtra(MyService.TITLE);
        }
        if(intent.getAction().equals(MyService.PLAYER_STATE_INFO)){
            if(intent.getStringExtra(MyService.STATE).equals(MyService.PLAYING_STATE)){
                playingState=false;
            }else if(intent.getStringExtra(MyService.STATE).equals(MyService.BUFFERING_STATE)){
                title=context.getResources().getString(R.string.buffering);
                playingState=true;
            }
        }

        remoteView=getRemoteView();
        notification=getNotification(context);
        notificationManager.notify(777, notification);

    }
    public void setImportance(int importance){
        this.importance=importance;
    }
    public Notification getNotification(Context context){
        this.context=context;
        getRemoteView();

        notificationManager = NotificationManagerCompat.from(context);
        String CHANNEL_ID;
        if(importance>1){//|start_type==start_type_widget
            CHANNEL_ID = "12345677";
        }else{
            CHANNEL_ID = "12345678";
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
                .setOngoing(true)
                .setCustomContentView(remoteView)
                .setCustomBigContentView(remoteView)
                //.setContent(notificationLayout)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)////Check It!!!
                .setPriority(importance);


        return builder.build();
    }
    public RemoteViews getRemoteView(){
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.notification);
        //////view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            remoteView.setImageViewResource(R.id.rew_button, R.drawable.rewind);
            if(playingState){
                remoteView.setImageViewResource(R.id.play_button, R.drawable.play);
            }else{
                remoteView.setImageViewResource(R.id.play_button, R.drawable.pause);
            }
            remoteView.setImageViewResource(R.id.fwd_button, R.drawable.fast_forward);
        } else {
            Drawable drawable = VectorDrawableCompat.create(context.getResources(), R.drawable.rewind, null);
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            remoteView.setImageViewBitmap(R.id.rew_button, bitmap);

            Drawable drawablePlay;
            if(playingState){
                drawablePlay = VectorDrawableCompat.create(context.getResources(),R.drawable.play, null);
            }else{
                drawablePlay = VectorDrawableCompat.create(context.getResources(),R.drawable.pause, null);
            }
            Bitmap bitmapPlay = Bitmap.createBitmap(drawablePlay.getIntrinsicWidth(), drawablePlay.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvasPlay = new Canvas(bitmapPlay);
            drawablePlay.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawablePlay.draw(canvasPlay);
            remoteView.setImageViewBitmap(R.id.play_button, bitmapPlay);

            Drawable drawableFf = VectorDrawableCompat.create(context.getResources(), R.drawable.fast_forward, null);
            Bitmap bitmapFf = Bitmap.createBitmap(drawableFf.getIntrinsicWidth(), drawableFf.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvasFf = new Canvas(bitmapFf);
            drawableFf.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawableFf.draw(canvasFf);
            remoteView.setImageViewBitmap(R.id.fwd_button, bitmapFf);
        }
        //////////////
        remoteView.setTextViewText(R.id.widget_title, title);
        //////actions
        Intent rewActionIntent = new Intent(context, MyService.class);
        rewActionIntent.putExtra(MyService.EVENT, MyService.ON_PLAY_CLICK);
        PendingIntent rewPendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            rewPendingIntent = PendingIntent.getForegroundService(context, 1200, rewActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            rewPendingIntent = PendingIntent.getService(context, 1200, rewActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        remoteView.setOnClickPendingIntent(R.id.rew_button,rewPendingIntent);

        Intent playActionIntent = new Intent(context,  MyService.class);
        playActionIntent.putExtra(MyService.EVENT, MyService.ON_PLAY_CLICK);
        PendingIntent playPendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            playPendingIntent = PendingIntent.getForegroundService(context, 1200, playActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            playPendingIntent = PendingIntent.getService(context, 1200, playActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        remoteView.setOnClickPendingIntent(R.id.play_button,playPendingIntent);

        Intent fwdActionIntent = new Intent(context,  MyService.class);
        fwdActionIntent.putExtra(MyService.EVENT, MyService.ON_PLAY_CLICK);
        PendingIntent fwdPendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fwdPendingIntent = PendingIntent.getForegroundService(context, 1200, fwdActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            fwdPendingIntent = PendingIntent.getService(context, 1200, fwdActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        remoteView.setOnClickPendingIntent(R.id.fwd_button,fwdPendingIntent);
        ///////////////////////////
        return remoteView;
    }

}
