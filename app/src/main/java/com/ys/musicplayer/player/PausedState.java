package com.ys.musicplayer.player;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.models.SystemPlayer;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.utils.PlayBackMode;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class PausedState implements State{
    private Player player;
    private Disposable disposable;
    private int selectedTrack;


    public PausedState(Player player) {
        this.player=player;
        //////////////////////////////////////
        player.playerFragment.setPlayButton(false);
        /////////////////////
       /* hashMap=new HashMap();
        hashMap.put(MyService.STATE,MyService.PAUSED_STATE);
        player.sendMessage(MyService.PLAYER_STATE_INFO,hashMap);*/
        player.systemPlayer.pause();
    }


    @Override
    public void onPlay() {
        disposable=player.trackManager.subscribeSelectedTrack()
                .flatMap(
                        selectedTrack->{
                            this.selectedTrack=selectedTrack;
                            return player.trackManager.subscribeCurrentTrack();
                        }
                )
                .subscribe(
                        currentTrack->{
                            disposable.dispose();
                            if(selectedTrack<0||selectedTrack==currentTrack){
                                player.changeState(player.playerStateFactory.getPlayingState(player));
                            }else{
                                player.changeState(player.playerStateFactory.getPreparingState(player));
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
        player.trackManager.setSelectedTrack(-1);
        player.trackManager.setCurrentTrack(player.playBackMode.getNext());
        player.changeState(player.playerStateFactory.getPreparingState(player));
    }

    @Override
    public void onPrevious() {
        player.trackManager.setSelectedTrack(-1);
        player.trackManager.setCurrentTrack(player.playBackMode.getPrevious());
        player.changeState(player.playerStateFactory.getPreparingState(player));
    }

    @Override
    public void setProgress(int progress) {
        player.systemPlayer.setProgress(progress);
    }
}
