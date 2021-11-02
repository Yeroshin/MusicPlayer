package com.ys.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ys.musicplayer.di.App;

import javax.inject.Inject;

public class MyService extends Service {
    class LocalBinder extends Binder {
        ClientService getService() {
            return MyService.this.clientService;
        }
    }
    private final IBinder localBinder=new LocalBinder();
    @Inject
    INotification ysNotification;
    @Inject
    INotificationView notificationView;
    @Inject
    ClientService clientService;

    @Override
    public void onCreate() {
        super.onCreate();
        App.get(this).getInjector().inject(this);
        notificationView.init(this);
        startForeground(777, ysNotification.getNotification(this,notificationView.getRemoteView(true)));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }
}
