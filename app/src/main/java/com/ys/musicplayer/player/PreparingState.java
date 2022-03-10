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
    private TrackManager trackManager;
    private PlayerStateFactory.Factory playerStateFactory;
    private SystemPlayer systemPlayer;
    private PlayBackMode playBackMode;
    private Disposable disposable;
    private int selectedTrack;
    private int currentTrack;
    private HashMap hashMap;

    public PreparingState(Player player, TrackManager trackManager, SystemPlayer systemPlayer, PlayerStateFactory.Factory playerStateFactory, PlayBackMode playBackMode) {
        this.player = player;
        this.trackManager = trackManager;
        this.systemPlayer=systemPlayer;
        this.playerStateFactory = playerStateFactory;
        this.playBackMode=playBackMode;
        ///////////////////////////////
        hashMap=new HashMap();
        hashMap.put(MyService.STATE,MyService.BUFFERING_STATE);
        player.sendMessage(MyService.PLAYER_STATE_INFO,hashMap);
        ///////////////////////////////
        disposable=trackManager.subscribeSelectedTrack()
                .flatMap(
                        selectedTrack->{
                            this.selectedTrack=selectedTrack;
                            return trackManager.subscribeCurrentTrack();//here!!!!!
                        }
                )
                // trackManager.subscribeCurrentTrack()
                .flatMap(
                        currentTrack->{

                            Track track;
                            if(selectedTrack<0||selectedTrack==currentTrack){
                                track=trackManager.getTrack(currentTrack);
                                this.currentTrack=currentTrack;
                            }else{
                                track=trackManager.getTrack(selectedTrack);
                                this.currentTrack=selectedTrack;
                            }
                            hashMap=new HashMap();
                            hashMap.put(MyService.TITLE,track.getArtist()+" - "+track.getTitle());
                            hashMap.put(MyService.DURATION,track.getDuration());
                            player.sendMessage(MyService.PLAYER_TRACK_INFO,hashMap);
                            return systemPlayer.prepare(track);
                        }
                )
                .subscribe(
                        nothing->{
                            disposable.dispose();
                            trackManager.setCurrentTrack(currentTrack);
                            trackManager.setSelectedTrack(-1);
                            player.changeState(playerStateFactory.getPlayingState());
                        },//next
                        e->{},//error
                        ()->{}//complete
                );
    }

    @Override
    public void onPlay() {
        player.changeState(playerStateFactory.getPreparingState());
    }

    @Override
    public void onNext() {
        trackManager.setSelectedTrack(-1);
        trackManager.setCurrentTrack(playBackMode.getNext());
        player.changeState(playerStateFactory.getPreparingState());
    }

    @Override
    public void onPrevious() {
        trackManager.setSelectedTrack(-1);
        trackManager.setCurrentTrack(playBackMode.getPrevious());
        player.changeState(playerStateFactory.getPreparingState());
    }

    @Override
    public void setProgress(int progress) {

    }
}
