package com.ys.musicplayer.fragments;

import com.ys.musicplayer.R;

public interface IPlayerFragment {
    void setPlaylist(String playlist);
    void setTrack_title(String track_title);
    void setTrackTitleBuffering();
    void setDuration_counter(String duration_counter);
    void setSeekBar(int progress,int max);
    void setDuration_info(String duration_info);
    void setPlayButton(boolean playing);
    void setVisualizer(int sessionId);
}
