package com.ys.musicplayer.player;

import android.net.Uri;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.R;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.models.TrackManager;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class PreparingState implements State{
    private Player player;
    private TrackManager trackManager;
    private PlayerStateFactory.Factory playerStateFactory;
    private SystemPlayer systemPlayer;
    private Disposable disposable;
    private int selectedTrack;
    private int currentTrack;
    private HashMap hashMap;

    public PreparingState(Player player, TrackManager trackManager, SystemPlayer systemPlayer,PlayerStateFactory.Factory playerStateFactory) {
        this.player = player;
        this.trackManager = trackManager;
        this.systemPlayer=systemPlayer;
        this.playerStateFactory = playerStateFactory;
        hashMap=new HashMap();
        hashMap.put(MyService.STATE,MyService.PLAYING_STATE);
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

    }

    @Override
    public void onNext() {

    }

    @Override
    public void onPrevious() {

    }
}
