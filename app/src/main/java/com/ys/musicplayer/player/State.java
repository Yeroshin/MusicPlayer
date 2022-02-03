package com.ys.musicplayer.player;

import android.net.Uri;

public interface State {
    public abstract void setPlayer(Player player);
    public abstract void onPlay();
    public abstract void onNext();
    public abstract void onPrevious();
}
