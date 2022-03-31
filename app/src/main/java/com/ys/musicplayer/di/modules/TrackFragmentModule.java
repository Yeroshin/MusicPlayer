package com.ys.musicplayer.di.modules;

import android.content.Context;


import com.ys.musicplayer.adapters.ItemTouchHelperCallback;
import com.ys.musicplayer.adapters.MediaAdapter;
import com.ys.musicplayer.adapters.PlayListAdapter;
import com.ys.musicplayer.adapters.TrackAdapter;
import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.dialogs.MediaDialog;
import com.ys.musicplayer.dialogs.MediaDialogPresenter;
import com.ys.musicplayer.dialogs.PlayListDialog;
import com.ys.musicplayer.dialogs.PlayListDialogPresenter;
import com.ys.musicplayer.fragments.ITrackFragment;
import com.ys.musicplayer.fragments.TrackFragment;
import com.ys.musicplayer.media.AlbumsContainerMediaItem;
import com.ys.musicplayer.media.ArtistMediaItem;
import com.ys.musicplayer.media.ArtistsContainerMediaItem;
import com.ys.musicplayer.media.BackMediaItem;
import com.ys.musicplayer.media.IMediaItem;
import com.ys.musicplayer.media.MediaItemFactory;
import com.ys.musicplayer.media.MediaModel;
import com.ys.musicplayer.media.PlayListFactory;
import com.ys.musicplayer.media.RootMediaItem;
import com.ys.musicplayer.media.TrackMediaItem;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.presenters.TrackFragmentPresenter;
import com.ys.musicplayer.utils.PlayBackMode;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ModelModule.class})
public class TrackFragmentModule {
    ITrackFragment trackFragment;

    public TrackFragmentModule(ITrackFragment trackFragment) {
        this.trackFragment = trackFragment;
    }


    @Singleton
    @Provides
    ItemTouchHelperCallback provideItemTouchHelperCallback(TrackFragmentPresenter trackFragmentPresenter){return new ItemTouchHelperCallback(trackFragmentPresenter);};

    @Singleton
    @Provides
    TrackAdapter provideTrackAdapter(){return new TrackAdapter();};

    @Singleton
    @Provides
    MediaDialog provideMediaDialog(){return new MediaDialog();};

  /*  @Singleton   TODO!!!!!!!!!!!!!!!
    @Provides
    PlayListDialog providePlayListDialog(PlayListAdapter playListAdapter, PlayListDialogPresenter playListDialogPresenter){return new PlayListDialog(playListAdapter,playListDialogPresenter);};*/

    @Singleton
    @Provides
    TrackFragmentPresenter provideTrackFragmentPresenter(PlayBackMode playBackMode, TrackManager trackManager){
        return new TrackFragmentPresenter(trackFragment,playBackMode, trackManager);
    }

    @Singleton
    @Provides
    PlayListAdapter providePlayListAdapter(Context context){return new PlayListAdapter(context);};

    @Singleton
    @Provides
    PlayListFactory.Factory providePlayListFactory(){
        return new PlayListFactory.Factory(){

            @Override
            public PlayList createPlayList() {
                return new PlayList();
            }
        };
    }

    @Singleton
    @Provides
    PlayListDialogPresenter providePlayListDialogPresenter(PlayListFactory.Factory playListFactory){
        return new PlayListDialogPresenter(playListFactory);
    };
    //////////////////////////
}
