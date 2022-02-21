package com.ys.musicplayer.player;

import android.net.Uri;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.models.TrackManager;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class PausedState implements State{
    private Player player;
    private SystemPlayer systemPlayer;
    private TrackManager trackManager;
    private PlayerStateFactory.Factory playerStateFactory;
    private Disposable disposable;
    private int selectedTrack;
    private HashMap hashMap;

    public PausedState(Player player, TrackManager trackManager, SystemPlayer systemPlayer,PlayerStateFactory.Factory playerStateFactory) {
        this.player=player;
        this.trackManager = trackManager;
        this.playerStateFactory = playerStateFactory;
        this.systemPlayer = systemPlayer;
        //////////////////////////////////////
        hashMap=new HashMap();
        hashMap.put(MyService.STATE,MyService.PAUSED_STATE);
        player.sendMessage(MyService.PLAYER_STATE_INFO,hashMap);
        systemPlayer.pause();
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
                                player.changeState(playerStateFactory.getPlayingState());
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
    }

    @Override
    public void onNext() {

    }

    @Override
    public void onPrevious() {

    }
}
