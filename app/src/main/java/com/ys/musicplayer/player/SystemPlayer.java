package com.ys.musicplayer.player;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class SystemPlayer {
    private MediaPlayer mediaPlayer;
    public SystemPlayer(){

    }
    public void init(){

        mediaPlayer=new MediaPlayer();
    }
    public Observable prepare(Uri uri){
        return Observable.create(
                s->{
                    mediaPlayer.reset();
                    mediaPlayer.setOnCompletionListener(null);
                    try {
                        mediaPlayer.setDataSource("https://maximum.hostingradio.ru/maximum128.mp3");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.setOnErrorListener(
                            (mediaPlayer,what,extra)->{
                                s.onError(new Exception("My Exception!"));
                                return true;
                            }
                    );
                    mediaPlayer.setOnPreparedListener(
                            mediaPlayer->{
                              s.onNext(null);
                            }
                    );
                    mediaPlayer.prepare();


                }
        );
       /* mediaPlayer.reset();
        mediaPlayer.setOnCompletionListener(null);

        /////////////////////recofe this!

        try {
            mediaPlayer.setDataSource("https://maximum.hostingradio.ru/maximum128.mp3");
            // mediaPlayer.setDataSource(context,uri);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ////////////////
        //  mediaPlayer.setDataSource("https://maximum.hostingradio.ru/maximum128.mp3");
        // mediaPlayer.setDataSource("http://nr3.newradio.it:8303/stream");
        ///////////////
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        }) ;

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer player) {

                player.start();

            }

        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });
        mediaPlayer.prepareAsync();*/
    }

    public Completable play(){
        return Completable.create(
                s->{
                    mediaPlayer.setOnCompletionListener(
                            mediaPlayer->{
                                s.onComplete();
                            }
                    );
                    mediaPlayer.start();

                }
        );
    }
    public void pause(){
        mediaPlayer.pause();
    }



}
