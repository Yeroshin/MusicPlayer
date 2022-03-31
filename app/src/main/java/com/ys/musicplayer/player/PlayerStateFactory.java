package com.ys.musicplayer.player;


public class PlayerStateFactory {
    public interface Factory{
        IdleState getIdleState(Player player);
        PreparingState getPreparingState(Player player);
        PlayingState getPlayingState(Player player);
        PausedState getPausedState(Player player);
    }

    public PlayerStateFactory(){

    }
}
