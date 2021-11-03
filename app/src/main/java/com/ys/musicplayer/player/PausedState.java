package com.ys.musicplayer.player;

import android.net.Uri;

public class PausedState extends State{

    PausedState(Player player) {
        super(player);
    }

    @Override
    public void onPlay() {
        player.changeState(new PlayingState(player));
        player.startPlayback();
    }

    @Override
    public void onNext() {
        player.changeState(new PlayingState(player));
        player.startPlayback();
    }

    @Override
    public void onPrevious() {
        player.changeState(new PlayingState(player));
        player.startPlayback();
    }
}
