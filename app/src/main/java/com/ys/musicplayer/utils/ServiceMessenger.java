package com.ys.musicplayer.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ys.musicplayer.MyService;

public class ServiceMessenger {
    BroadcastReceiver broadcastReceiver;
    private Intent intent;
    private Context context;
    public static final String start="start";
    public static final String prepare="prepare";
    public static final String play="play";
    public static final String pause="pause";
    public ServiceMessenger(Context context) {
        this.context=context;
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
    }
    public void prepare(){
        intent = new Intent();
        intent.setAction(prepare);
        context.startService(intent);
    }
    public void play(){
        intent = new Intent(context, MyService.class);
        intent.setAction(play);
        context.startService(intent);
    }
}
