package com.ys.musicplayer.player;

import android.net.Uri;

public class PlayingState implements State{
    private Player player;
    private SystemPlayer systemPlayer;
    private PlayerStateFactory.Factory playerStateFactory;

    public PlayingState(SystemPlayer systemPlayer, PlayerStateFactory.Factory playerStateFactory) {
        this.systemPlayer = systemPlayer;
        this.playerStateFactory = playerStateFactory;
        systemPlayer.play()
        .subscribe(
                ()->{

                }
        );
    }


    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void onPlay() {

        player.changeState(playerStateFactory.getPausedState());
    }

    @Override
    public void onNext() {

    }

    @Override
    public void onPrevious() {

    }
}
