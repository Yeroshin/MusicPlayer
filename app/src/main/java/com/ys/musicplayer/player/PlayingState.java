package com.ys.musicplayer.player;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.models.SystemPlayer;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.utils.PlayBackMode;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class PlayingState implements State{
    private Player player;
    private Disposable disposable;
    private int selectedTrack;


    public PlayingState(Player player) {
        this.player = player;
        ////////////////////////////////////
       /* hashMap=new HashMap();
        hashMap.put(MyService.STATE,MyService.PLAYING_STATE);
        player.sendMessage(MyService.PLAYER_STATE_INFO,hashMap);*/
        ////
        player.playerFragment.setPlayButton(true);
        ///////////////////////
        player.systemPlayer.play()
                .subscribe(
                        v->{
                            onNext();
                           // Log.d("TAG","complete!!!");
                        }
                );

        player.systemPlayer.subscribeTime()
                .subscribe(
                        time->{
                            player.playerFragment.setDuration_counter(player.timeConvert(time));
                            player.playerFragment.setSeekBar(time,player.systemPlayer.getDuration());
                           /* hashMap=new HashMap();
                            hashMap.put(MyService.CURRENT_TIME,timeHashMap);
                            hashMap.put(MyService.DURATION,systemPlayer.getDuration());
                            player.sendMessage(MyService.PLAYER_TIME_INFO,hashMap);*/
                        }
                );
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
                                player.changeState(player.playerStateFactory.getPausedState(player));
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
       // player.changeState(playerStateFactory.getPausedState());
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
