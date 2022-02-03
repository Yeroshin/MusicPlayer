package com.ys.musicplayer.fragments;

import com.ys.musicplayer.MainContract;
import com.ys.musicplayer.player.Player;
import com.ys.musicplayer.player.SystemPlayer;
import com.ys.musicplayer.utils.ServiceMessenger;

import java.util.ArrayList;

import javax.inject.Inject;

public class PlayerFragmentPresenter {

    ServiceMessenger serviceMessenger;
    public PlayerFragmentPresenter(ServiceMessenger serviceMessenger){
        this.serviceMessenger=serviceMessenger;
    }


    public void onClickPlay() {
        serviceMessenger.play();
    }
}
