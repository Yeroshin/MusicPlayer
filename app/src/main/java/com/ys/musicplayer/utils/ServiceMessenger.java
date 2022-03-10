package com.ys.musicplayer.utils;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.ys.musicplayer.MyService;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class ServiceMessenger extends BroadcastReceiver{
    private Intent intent;
    private Context context;
    private BehaviorSubject subjectPlayerTrackInfo;
    private BehaviorSubject subjectPlayerStateInfo;
    private BehaviorSubject subjectPlayerTimeInfo;
    private BehaviorSubject subjectAudioSessionId;
    private HashMap hashMap;


    public ServiceMessenger(Context context) {
        this.context=context;
        subjectPlayerTrackInfo = BehaviorSubject.create();
        subjectPlayerStateInfo = BehaviorSubject.create();
        subjectPlayerTimeInfo = BehaviorSubject.create();
        subjectAudioSessionId = BehaviorSubject.create();
    }
/*
    public void play(){
        intent = new Intent(context, MyService.class);
        intent.putExtra(MyService.EVENT,MyService.ON_PLAY_CLICK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        }else{
            context.startService(intent);
        }
    }*/
    public void sendMessage(Map<String,String> extraHashMap){
        intent = new Intent(context, MyService.class);
        for(Map.Entry<String,String> entry:extraHashMap.entrySet()){
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        }else{
            context.startService(intent);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        hashMap=new HashMap();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
               hashMap.put(key,bundle.get(key));
            }
        }
        switch (intent.getAction()){
            case MyService.PLAYER_TRACK_INFO:
                subjectPlayerTrackInfo.onNext(hashMap);
                break;
            case MyService.PLAYER_STATE_INFO:
                subjectPlayerStateInfo.onNext(hashMap);
                break;
            case MyService.PLAYER_TIME_INFO:
                subjectPlayerTimeInfo.onNext(hashMap);
                break;
            case MyService.AUDIO_SESSION:
                subjectAudioSessionId.onNext(hashMap);
                break;
            default:
                break;
        }

    }
    public Observable<HashMap<String,String>> subscribePlayerTrackInfo(){
        return subjectPlayerTrackInfo;
    }
    public Observable<HashMap<String,String>> subscribePlayerTimeInfo(){
        return subjectPlayerTimeInfo;
    }
    public Observable<HashMap<String,String>> subscribePlayerStateInfo(){
        return subjectPlayerStateInfo;
    }
    public Observable<HashMap<String,String>> subscribeAudioSessionId(){
        return subjectAudioSessionId;
    }
}
