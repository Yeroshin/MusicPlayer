package com.ys.musicplayer.media;

public class MediaItemFactory {
    public interface Factory{
        IMediaItem createBackMediaItem();
        ArtistMediaItem createArtistMediaItem();
        TrackMediaItem createTrackMediaItem();
    }

    public MediaItemFactory(){

    }
}
