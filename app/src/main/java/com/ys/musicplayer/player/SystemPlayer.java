package com.ys.musicplayer.player;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

import javax.inject.Inject;

public class SystemPlayer {
    interface SystemPlayerObserver{
        void onCompletion();
    }
    Context context;
    MediaPlayer mediaPlayer;
    SystemPlayer.SystemPlayerObserver observer;
    @Inject
    public SystemPlayer(Context context){
        this.context=context;
        this.mediaPlayer=new MediaPlayer();
    }
    public void setObserver(SystemPlayer.SystemPlayerObserver observer){
        this.observer=observer;
    }
    public void play(Uri uri){
        mediaPlayer.reset();
        mediaPlayer.setOnCompletionListener(null);
        try {
            mediaPlayer.setDataSource(context,uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer player) {
                player.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(observer!=null){
                    observer.onCompletion();
                }
                //  mediaPlayer.start();
            }
        });
        mediaPlayer.prepareAsync();
    }
    public void pause(){
        mediaPlayer.pause();
    }
    public void resume(){
        mediaPlayer.start();
    }

}
