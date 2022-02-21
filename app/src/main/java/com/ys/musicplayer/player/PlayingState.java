package com.ys.musicplayer.player;

import android.net.Uri;
import android.util.Log;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.models.TrackManager;

import java.io.DataInput;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;

public class PlayingState implements State{
    private Player player;
    private SystemPlayer systemPlayer;
    private TrackManager trackManager;
    private PlayerStateFactory.Factory playerStateFactory;

    private Disposable disposable;
    private int selectedTrack;
    private HashMap hashMap;

    public PlayingState(Player player, TrackManager trackManager, SystemPlayer systemPlayer,PlayerStateFactory.Factory playerStateFactory) {
        this.player = player;
        this.trackManager = trackManager;
        this.systemPlayer = systemPlayer;
        this.playerStateFactory = playerStateFactory;
        hashMap=new HashMap();
        hashMap.put(MyService.STATE,MyService.PLAYING_STATE);
        player.sendMessage(MyService.PLAYER_STATE_INFO,hashMap);
        ///////////////////////
        systemPlayer.play()
                .subscribe(
                        v->{
                            onNext();
                           // Log.d("TAG","complete!!!");
                        }
                );

        systemPlayer.subscribeTime()
                .subscribe(
                        timeHashMap->{
                            hashMap=new HashMap();
                            hashMap.put(MyService.CURRENT_TIME,timeHashMap);
                            hashMap.put(MyService.DURATION,systemPlayer.getDuration());
                            player.sendMessage(MyService.PLAYER_TIME_INFO,hashMap);
                        }
                );
    }


    @Override
    public void onPlay() {
        disposable=trackManager.subscribeSelectedTrack()
                .flatMap(
                        selectedTrack->{
                            this.selectedTrack=selectedTrack;
                            return trackManager.subscribeCurrentTrack();
                        }
                )
                .subscribe(
                        currentTrack->{
                            disposable.dispose();
                            if(selectedTrack<0||selectedTrack==currentTrack){
                                player.changeState(playerStateFactory.getPausedState());
                            }else{
                                player.changeState(playerStateFactory.getPreparingState());
                            }
                        },
                        e->{},//error
                        ()->{//complete

                        },
                        d->{
                            disposable=d;
                        }
                );
       // player.changeState(playerStateFactory.getPausedState());
    }

    @Override
    public void onNext() {

    }

    @Override
    public void onPrevious() {

    }
}
