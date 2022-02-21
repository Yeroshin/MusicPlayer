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

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.R;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.models.TrackManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.security.auth.callback.Callback;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class SystemPlayer {
    private Context context;
    private MediaPlayer mediaPlayer;
    public SystemPlayer(Context context){
        this.context=context;
    }
    public void init(){
        mediaPlayer=new MediaPlayer();
    }
    public Observable prepare(Track track){
        return Observable.create(//TODO
                emmiter->{
                    mediaPlayer.reset();
                    mediaPlayer.setOnCompletionListener(null);
                    try {
                        mediaPlayer.setDataSource(context, Uri.parse(track.getUri()));
                        // mediaPlayer.setDataSource("https://maximum.hostingradio.ru/maximum128.mp3");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.setOnErrorListener(
                            (mediaPlayer,what,extra)->{
                                emmiter.onError(new Exception("My Exception!"));
                                return true;
                            }
                    );
                    mediaPlayer.setOnPreparedListener(
                            mediaPlayer->{

                                emmiter.onNext(-1);
                            }
                    );
                    mediaPlayer.prepareAsync();
                }

        );

    }

    public Observable play(){
        return Observable.create(
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
    public Observable<String> subscribeTime() {
        return Observable.interval(1, TimeUnit.SECONDS)
                .flatMap(
                        time -> {
                            return Observable.create(
                                    emitter -> {
                                        emitter.onNext(String.valueOf(mediaPlayer.getCurrentPosition()));
                                    }
                            );
                        }
                );
    }
    public String getDuration(){
        return String.valueOf(mediaPlayer.getDuration());
    }
    ///////////////////
}
