package com.ys.musicplayer.media;

import com.ys.musicplayer.media.states.RootMedia;

import javax.inject.Singleton;

import dagger.Provides;

public class MediaItemFactory {
    public interface Factory{
        RootMedia createRootMedia();
        BackMediaItem createBackMediaItem(IMediaItem backContent);
        AlbumsContainerMediaItem createAlbumsContainerMediaItem();
        ArtistsContainerMediaItem createArtistsContainerMediaItem(IMediaItem backMediaItemParent);
        ArtistMediaItem createArtistMediaItem(IMediaItem backMediaItemParent);
        TrackMediaItem createTrackMediaItem();
    }

    public MediaItemFactory(){

    }
}

