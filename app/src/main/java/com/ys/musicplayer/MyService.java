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
import com.ys.musicplayer.utils.UIMessenger;

import javax.inject.Inject;

public class MyService extends Service {
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    public static final String IMPORTANCE="importance";
    public static final String ON_PLAY_CLICK="onPlayClick";
    public static final String EVENT="event";
    public static final String PLAYER_TRACK_INFO="com.ys.musicplayer.PlayerTrackInfo";
    public static final String PLAYER_STATE_INFO="com.ys.musicplayer.PlayerStateInfo";
    public static final String PLAYER_TIME_INFO="com.ys.musicplayer.PlayerTimeInfo";
    public static final String TITLE="title";
    public static final String STATE="state";
    public static final String PLAYING_STATE="playingState";
    public static final String PAUSED_STATE="pausedState";
    public static final String CURRENT_TIME="currentTime";
    public static final String DURATION="duration";
    ///////////////////////
    @Inject
    YESNotification yesNotification;


   /* @Inject
    Player player;*/

    @Inject
    UIMessenger messenger;
    /////////////////////////
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            messenger.handleMessage(msg);
        }
    }



    @Override
    public void onCreate() {
        super.onCreate();
        App.get(this).getInjector().inject(this);
        //////////////////////
     //   startForeground(777, yesNotification.getNotification(this,notificationView.getRemoteView(true)));
        /////////////
        HandlerThread thread = new HandlerThread("YESThread", 10);// Process.THREAD_PRIORITY_BACKGROUND : 10
        thread.start();
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);

    }
    private void startForeground(){

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = serviceHandler.obtainMessage();
        msg.obj=intent;
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
