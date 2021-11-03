package com.ys.musicplayer.player;

import android.net.Uri;

public abstract class State {
    Player player;
    State (Player player){
        this.player=player;
    }

    public abstract void onPlay();
    public abstract void onNext();
    public abstract void onPrevious();
}
