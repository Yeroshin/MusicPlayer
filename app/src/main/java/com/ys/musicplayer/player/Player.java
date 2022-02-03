package com.ys.musicplayer.player;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;


import com.ys.musicplayer.db.Track;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

public class Player {
    private State state;
    private PlayerStateFactory.Factory playerStateFactory;

    public Player(PlayerStateFactory.Factory playerStateFactory){
        this.playerStateFactory=playerStateFactory;
        changeState(playerStateFactory.getIdleState());
    }

    public void changeState(State state) {
        state.setPlayer(this);
        this.state = state;
    }

    public void onPlay(){

        state.onPlay();
    }
    public void onNext(){
        state.onNext();
    }
    public void onPrevious(){
        state.onPrevious();
    }

}
