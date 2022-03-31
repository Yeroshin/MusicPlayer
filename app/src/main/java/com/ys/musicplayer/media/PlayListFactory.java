package com.ys.musicplayer.media;

import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.Track;

public class PlayListFactory {
    public interface Factory{
        PlayList createPlayList();
    }

    public PlayListFactory(){

    }
}
