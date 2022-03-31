package com.ys.musicplayer.player;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.models.SystemPlayer;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.utils.PlayBackMode;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class PreparingState implements State{
    private Player player;
    private Disposable disposable;
    private int selectedTrack;
    private int currentTrack;


    public PreparingState(Player player) {
        this.player = player;
        ///////////////////////////////
        player.playerFragment.setPlayButton(true);
        player.playerFragment.setTrackTitleBuffering();
        ///////////////////////////////
        disposable=player.trackManager.subscribeSelectedTrack()
                .flatMap(
                        selectedTrack->{
                            this.selectedTrack=selectedTrack;
                            return player.trackManager.subscribeCurrentTrack();//here!!!!!
                        }
                )
                // trackManager.subscribeCurrentTrack()
                .flatMap(
                        currentTrack->{

                            Track track;
                            if(selectedTrack<0||selectedTrack==currentTrack){
                                track=player.trackManager.getTrack(currentTrack);
                                this.currentTrack=currentTrack;
                            }else{
                                track=player.trackManager.getTrack(selectedTrack);
                                this.currentTrack=selectedTrack;
                            }
                           /* hashMap=new HashMap();
                            hashMap.put(MyService.TITLE,track.getArtist()+" - "+track.getTitle());
                            hashMap.put(MyService.DURATION,track.getDuration());
                            player.sendMessage(MyService.PLAYER_TRACK_INFO,hashMap);*/
                            player.playerFragment.setTrack_title(track.getArtist()+" - "+track.getTitle());
                            return player.systemPlayer.prepare(track);
                        }
                )
                .subscribe(
                        nothing->{
                            player.playerFragment.setDuration_info(player.timeConvert(player.systemPlayer.getDuration()));
                            disposable.dispose();
                            player.trackManager.setCurrentTrack(currentTrack);
                            player.trackManager.setSelectedTrack(-1);
                            player.changeState(player.playerStateFactory.getPlayingState(player));
                        },//next
                        e->{},//error
                        ()->{}//complete
                );
    }

    @Override
    public void onPlay() {
        player.changeState(player.playerStateFactory.getPreparingState(player));
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

    }
}
