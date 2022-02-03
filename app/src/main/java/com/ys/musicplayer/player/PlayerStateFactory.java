package com.ys.musicplayer.player;


public class PlayerStateFactory {
    public interface Factory{
        IdleState getIdleState();
        PlayingState getPlayingState();
        PausedState getPausedState();
    }

    public PlayerStateFactory(){

    }
}
