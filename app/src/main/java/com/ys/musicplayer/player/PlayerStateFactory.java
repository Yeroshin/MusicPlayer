package com.ys.musicplayer.player;


public class PlayerStateFactory {
    public interface Factory{
        IdleState getIdleState();
        PreparingState getPreparingState();
        PlayingState getPlayingState();
        PausedState getPausedState();
    }

    public PlayerStateFactory(){

    }
}
