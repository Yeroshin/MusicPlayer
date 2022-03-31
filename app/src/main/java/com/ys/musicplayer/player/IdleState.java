package com.ys.musicplayer.player;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.models.SystemPlayer;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.utils.PlayBackMode;

import java.util.HashMap;

public class IdleState implements State {
    private Player player;


    public IdleState(Player player) {
        this.player = player;


        ///////////////////////
        player.systemPlayer.init();
        player.systemPlayer.subscribeAudioSessionId()
                .subscribe(
                        id->{
                            player.playerFragment.setVisualizer(id);
                            /////////////
                          /*  hashMap=new HashMap();
                            hashMap.put(MyService.AUDIO_SESSION_ID,String.valueOf(id));
                            player.sendMessage(MyService.AUDIO_SESSION,hashMap);*/
                        }
                );
    }

    @Override
    public void onPlay() {
        player.changeState(player.playerStateFactory.getPreparingState(player));
    }

    @Override
    public void onNext() {
        player.changeState(player.playerStateFactory.getPreparingState(player));
    }

    @Override
    public void onPrevious() {
        player.changeState(player.playerStateFactory.getPreparingState(player));
    }

    @Override
    public void setProgress(int progress) {

    }
}
