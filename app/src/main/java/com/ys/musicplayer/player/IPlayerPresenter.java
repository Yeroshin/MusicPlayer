package com.ys.musicplayer.player;

import com.ys.musicplayer.fragments.IPlayerFragment;
import com.ys.musicplayer.models.SystemPlayer;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.utils.PlayBackMode;

public interface IPlayerPresenter {

    void onPlay();
    void onNext();
    void onPrevious();
    void onProgressChanged(int progress);
}
