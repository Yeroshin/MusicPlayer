package com.ys.musicplayer;

import android.app.Service;
import android.content.Intent;

import android.os.Handler;
import android.os.HandlerThread;

import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.Nullable;

import com.ys.musicplayer.di.App;
import com.ys.musicplayer.player.Player;
import com.ys.musicplayer.player.SystemPlayer;
import com.ys.musicplayer.utils.ServiceMessenger;

import javax.inject.Inject;

public class MyService extends Service {
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    ///////////////////////
    @Inject
    INotification ysNotification;
    @Inject
    INotificationView notificationView;

    @Inject
    Player player;
    /////////////////////////
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            switch ((String) msg.obj){
                case ServiceMessenger.play:
                    player.onPlay();
                    break;
            }
        }
    }



    @Override
    public void onCreate() {
        super.onCreate();
        App.get(this).getInjector().inject(this);
        notificationView.init(this);
        startForeground(777, ysNotification.getNotification(this,notificationView.getRemoteView(true)));
        /////////////
        /////////////
        HandlerThread thread = new HandlerThread("ServiceThread", 10);// Process.THREAD_PRIORITY_BACKGROUND : 10
        thread.start();
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = serviceHandler.obtainMessage();
        msg.obj=intent.getAction();
        serviceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
