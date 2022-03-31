package com.ys.musicplayer.di.modules;

import android.content.Context;

import com.ys.musicplayer.adapters.MediaAdapter;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.dialogs.IMediaDialog;
import com.ys.musicplayer.dialogs.MediaDialog;
import com.ys.musicplayer.dialogs.MediaDialogPresenter;
import com.ys.musicplayer.fragments.IMediaDialogPresenter;
import com.ys.musicplayer.media.AlbumsContainerMediaItem;
import com.ys.musicplayer.media.ArtistMediaItem;
import com.ys.musicplayer.media.ArtistsContainerMediaItem;
import com.ys.musicplayer.media.BackMediaItem;
import com.ys.musicplayer.media.IMediaItem;
import com.ys.musicplayer.media.MediaItemFactory;
import com.ys.musicplayer.media.MediaModel;
import com.ys.musicplayer.media.PlayListFactory;
import com.ys.musicplayer.media.RootMediaItem;
import com.ys.musicplayer.media.TrackFactory;
import com.ys.musicplayer.media.TrackMediaItem;
import com.ys.musicplayer.media.states.RootMedia;
import com.ys.musicplayer.models.TrackManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module(includes = {ModelModule.class})
public class MediaDialogModule {

    private final Context context;
    private IMediaDialog mediaDialog;

    public MediaDialogModule (Context context, IMediaDialog mediaDialog) {
        this.context = context;
        this.mediaDialog=mediaDialog;
    }


    ///////////////////////////
    @Singleton
    @Provides
    MediaModel provideMediaModel(TrackFactory.Factory trackFactory){return new MediaModel(context,trackFactory);};
    @Singleton
    @Provides
    IMediaDialogPresenter provideTrackDialogPresenter( TrackManager trackManager, MediaItemFactory.Factory mediaItemFactory){
        return new MediaDialogPresenter(mediaDialog,trackManager,mediaItemFactory);
    };

    @Singleton
    @Provides
    MediaAdapter provideMediaAdapter(){return new MediaAdapter();};
    @Singleton
    @Provides
    TrackFactory.Factory providesTrackFactory(){
        return new TrackFactory.Factory() {
            @Override
            public Track createTrack() {
                return new Track();
            }
        };
    }
    @Singleton
    @Provides
    MediaItemFactory.Factory provideMediaItemFactory(MediaModel mediaModel){
        return new MediaItemFactory.Factory(){
            @Override
            public RootMedia createRootMedia() {
                return new RootMedia(this);
            }

            @Override
            public BackMediaItem createBackMediaItem(IMediaItem backContent) {
                return new BackMediaItem(backContent);
            }

            @Override
            public AlbumsContainerMediaItem createAlbumsContainerMediaItem() {
                return new AlbumsContainerMediaItem();
            }


            @Override
            public ArtistsContainerMediaItem createArtistsContainerMediaItem(IMediaItem backMediaItemParent) {
                return new ArtistsContainerMediaItem(backMediaItemParent,this,mediaModel);
            }

            @Override
            public ArtistMediaItem createArtistMediaItem(IMediaItem backMediaItemParent) {
                return new ArtistMediaItem(backMediaItemParent,this,mediaModel);
            }

            @Override
            public TrackMediaItem createTrackMediaItem() {
                return new TrackMediaItem();
            }

        };
    }
}
