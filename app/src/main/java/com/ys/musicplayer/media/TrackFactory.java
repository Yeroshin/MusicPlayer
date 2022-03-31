package com.ys.musicplayer.media;

import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.Track;

public class TrackFactory {
    public interface Factory{
        Track createTrack();

    }

    public TrackFactory(){

    }
}
