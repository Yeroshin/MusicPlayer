package com.ys.musicplayer.player;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.models.SystemPlayer;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.utils.PlayBackMode;

import java.util.HashMap;

public class IdleState implements State {
    private Player player;
    private PlayerStateFactory.Factory playerStateFactory;
    private PlayBackMode playBackMode;
    private HashMap hashMap;

    public IdleState(Player player, TrackManager trackManager, SystemPlayer systemPlayer, PlayerStateFactory.Factory playerStateFactory, PlayBackMode playBackMode) {
        this.player = player;
        this.playerStateFactory = playerStateFactory;
        this.playBackMode=playBackMode;
        ///////////////////////
        systemPlayer.init();
        systemPlayer.subscribeAudioSessionId()
                .subscribe(
                        id->{
                            hashMap=new HashMap();
                            hashMap.put(MyService.AUDIO_SESSION_ID,id);
                            player.sendMessage(MyService.AUDIO_SESSION,hashMap);
                        }
                );
    }

    @Override
    public void onPlay() {
        player.changeState(playerStateFactory.getPreparingState());
    }

    @Override
    public void onNext() {
        player.changeState(playerStateFactory.getPreparingState());
    }

    @Override
    public void onPrevious() {
        player.changeState(playerStateFactory.getPreparingState());
    }

    @Override
    public void setProgress(int progress) {

    }
}
