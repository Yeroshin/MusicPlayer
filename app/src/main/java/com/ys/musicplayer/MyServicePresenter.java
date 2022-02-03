package com.ys.musicplayer;

import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.player.Player;

public class MyServicePresenter {
    private TrackManager trackManager;
    private Player player;

    public MyServicePresenter(TrackManager trackManager, Player player) {
        this.trackManager = trackManager;
        this.player = player;
    }
    public void Play(){
       player.onPlay();
    }
}
