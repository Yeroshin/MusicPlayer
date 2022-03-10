package com.ys.musicplayer.player;

import android.net.Uri;

public interface State {



    void onPlay();
    void onNext();
    void onPrevious();
    void setProgress(int progress);
}
