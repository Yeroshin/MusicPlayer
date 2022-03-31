package com.ys.musicplayer.di.modules;

import android.content.Context;

import com.ys.musicplayer.dialogs.MediaDialogPresenter;
import com.ys.musicplayer.adapters.PlayListAdapter;
import com.ys.musicplayer.fragments.TrackFragment;
import com.ys.musicplayer.adapters.MediaAdapter;
import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.media.AlbumsContainerMediaItem;
import com.ys.musicplayer.media.ArtistMediaItem;
import com.ys.musicplayer.media.ArtistsContainerMediaItem;
import com.ys.musicplayer.media.BackMediaItem;
import com.ys.musicplayer.media.IMediaItem;
import com.ys.musicplayer.media.MediaItemFactory;
import com.ys.musicplayer.media.MediaModel;

import com.ys.musicplayer.dialogs.PlayListDialogPresenter;
import com.ys.musicplayer.media.PlayListFactory;
import com.ys.musicplayer.media.RootMediaItem;
import com.ys.musicplayer.media.TrackMediaItem;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;
    public AppModule(Context context){
        this.context=context;
    }


    @Provides
    @Singleton
    Context provideContext(){
        return context;
    };
  ///////////////////////////////////
  
////////////////////////////////////
 /* @Singleton
  @Provides
  PlayerFragmentPresenterProxy provideServiceMessenger(){
    PlayerFragmentPresenterProxy playerFragmentPresenterProxy =new PlayerFragmentPresenterProxy(context);
    IntentFilter filter = new IntentFilter();
    filter.addAction(PlayerPresenterProxy.PLAYER_VIEW);
    context.registerReceiver(playerFragmentPresenterProxy, filter);
    return playerFragmentPresenterProxy;
  };*/

///////////////////////////////////
   /* @Singleton
    @Provides
    MainContract.Model provideModel(Player player){
        return new Model(context, player);
    };*/
////////////////////////////////////




}
