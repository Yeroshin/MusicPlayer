package com.ys.musicplayer.player;

import android.net.Uri;

public class PlayingState extends State{
    PlayingState(Player player) {
        super(player);
    }

    @Override
    public void onPlay() {
        player.changeState(new PausedState(player));
        player.pausePlayback();
    }

    @Override
    public void onNext() {
        player.startPlayback();
    }

    @Override
    public void onPrevious() {
        player.startPlayback();
    }
}
