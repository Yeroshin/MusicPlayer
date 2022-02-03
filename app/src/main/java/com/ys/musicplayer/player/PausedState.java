package com.ys.musicplayer.player;

import android.net.Uri;

import com.ys.musicplayer.models.TrackManager;

import io.reactivex.Observable;

public class PausedState implements State{
    private Player player;
    private TrackManager trackManager;
    private SystemPlayer systemPlayer;
    private PlayerStateFactory.Factory playerStateFactory;
    public PausedState(TrackManager trackManager, SystemPlayer systemPlayer, PlayerStateFactory.Factory playerStateFactory) {
        this.trackManager = trackManager;
        this.systemPlayer=systemPlayer;
        this.playerStateFactory = playerStateFactory;
        systemPlayer.pause();
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void onPlay() {
        trackManager.subscribeSelectedTrack()

                .subscribe(
                        id->{
                            if(id==-1){
                                player.changeState(playerStateFactory.getPlayingState());
                            }else{
                                player.changeState(playerStateFactory.getIdleState());
                                player.onPlay();
                            }
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
