package com.ys.musicplayer.player;

import android.net.Uri;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.ys.musicplayer.models.TrackManager;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class IdleState implements State {
    private Player player;
    private TrackManager trackManager;
    private SystemPlayer systemPlayer;
    private PlayerStateFactory.Factory playerStateFactory;
    private Disposable disposable;
    private int selectedTrack;

    public IdleState(TrackManager trackManager,SystemPlayer systemPlayer,PlayerStateFactory.Factory playerStateFactory) {
        this.trackManager = trackManager;
        this.systemPlayer=systemPlayer;
        this.playerStateFactory = playerStateFactory;
        systemPlayer.init();
    }


    @Override
    public void setPlayer(Player player) {
        this.player = player;
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
       // trackManager.subscribeCurrentTrack()
              /*  .map(
                        currentTrack->{
                            if(selectedTrack<0&&selectedTrack!=currentTrack){
                                return systemPlayer.prepare(trackManager.getTrack(selectedTrack).getUri());
                            }else{
                                return systemPlayer.prepare(trackManager.getTrack(currentTrack).getUri());
                            }

                        }test
                )*/
                .subscribe(


                        d->{
                            player.changeState(playerStateFactory.getPlayingState());
                            disposable.dispose();
                        },//next
                        e->{},//error
                        ()->{

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
