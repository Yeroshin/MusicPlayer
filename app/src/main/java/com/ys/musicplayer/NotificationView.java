package com.ys.musicplayer;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.ys.musicplayer.di.App;

import javax.inject.Inject;

public class NotificationView extends BroadcastReceiver implements MainContract.MainView,INotificationView{
    public static final int action_rew=1;
    public static final int action_play=2;
    public static final int action_fwd=3;
    public static final String ACTION = "com.ys.musicplayer.YSMUSIC";
    private Context context;
    @Inject
    public MainContract.MainPresenter mainPresenter;
    @Inject
    public INotification ysNotification;


    public NotificationView(){

    }
    @Override
    public void init(Context context){
        App.get(context).getInjector().inject(this);
        this.context=context;
        this.mainPresenter.onAttachView(this);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        App.get(context).getInjector().inject(this);
        this.context=context;
        this.mainPresenter.onAttachView(this);
        this.context=context;
        switch (intent.getIntExtra("action",0)){
            case action_play:
                mainPresenter.onClickPlay();
                break;
            default:
                break;
        }
    }

    @Override
    public void setArtist(String text) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        remoteView.setTextViewText(R.id.textView,text);
        ysNotification.updateNotification(remoteView);
    }
    @Override
    public RemoteViews getRemoteView(boolean playBtnStatus){
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        //////view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            remoteView.setImageViewResource(R.id.rew_button, R.drawable.rewind);
            if(playBtnStatus){
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
            if(playBtnStatus){
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
        //////actions
        Intent rewActionIntent = new Intent(context, NotificationView.class);
        rewActionIntent.setAction(ACTION);
        rewActionIntent.putExtra("action",action_rew);
        PendingIntent rewPendingIntent = PendingIntent.getBroadcast(context, 1200, rewActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.rew_button,rewPendingIntent);

        Intent playActionIntent = new Intent(context, NotificationView.class);
        playActionIntent.putExtra("action",action_play);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(context, 1201, playActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.play_button,playPendingIntent);

        Intent fwdActionIntent = new Intent(context, NotificationView.class);
        fwdActionIntent.putExtra("action",action_fwd);
        PendingIntent fwdPendingIntent = PendingIntent.getBroadcast(context, 1202, fwdActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.fwd_button,fwdPendingIntent);
        ///////////////////////////
        return remoteView;
    }
}
