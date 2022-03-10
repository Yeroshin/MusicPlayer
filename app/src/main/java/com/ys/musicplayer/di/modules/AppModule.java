package com.ys.musicplayer.di.modules;

import android.content.Context;
import android.content.IntentFilter;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.R;
import com.ys.musicplayer.dialogs.MediaDialog;
import com.ys.musicplayer.dialogs.MediaDialogPresenter;
import com.ys.musicplayer.models.Settings;
import com.ys.musicplayer.adapters.PlayListAdapter;
import com.ys.musicplayer.fragments.TrackFragment;
import com.ys.musicplayer.adapters.MediaAdapter;
import com.ys.musicplayer.adapters.TrackAdapter;
import com.ys.musicplayer.db.AppDatabase;
import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.PlaylistDAO;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.dialogs.PlayListDialog;
import com.ys.musicplayer.fragments.TrackFragmentPresenter;
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
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.utils.PlayBackMode;
import com.ys.musicplayer.utils.ServiceMessenger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;
    public AppModule(Context context){
        this.context=context;
    }
  /*  @Provides
    @Singleton
    Context provideContext(){
        return context;
    };*/
  ///////////////////////////////////
  
////////////////////////////////////
  @Singleton
  @Provides
  ServiceMessenger provideServiceMessenger(){
    ServiceMessenger serviceMessenger=new ServiceMessenger(context);
    IntentFilter filter = new IntentFilter();
    filter.addAction(MyService.PLAYER_TRACK_INFO);
    filter.addAction(MyService.PLAYER_STATE_INFO);
    filter.addAction(MyService.PLAYER_TIME_INFO);
    filter.addAction(MyService.AUDIO_SESSION);
    context.registerReceiver(serviceMessenger, filter);
    return serviceMessenger;
  };

///////////////////////////////////
   /* @Singleton
    @Provides
    MainContract.Model provideModel(Player player){
        return new Model(context, player);
    };*/
////////////////////////////////////

    @Singleton
    @Provides
    TrackAdapter provideTrackAdapter(){return new TrackAdapter(context);};
    //////////////////////////////
    @Singleton
    @Provides
    MediaDialogPresenter provideTrackDialogPresenter(MediaAdapter trackAdapter, RootMediaItem rootMediaItem, TrackManager trackManager){return new MediaDialogPresenter(trackAdapter,rootMediaItem,trackManager);};
    @Singleton
    @Provides
    MediaAdapter provideMediaAdapter(){return new MediaAdapter(context);};
    @Singleton
    @Provides
    MediaDialog provideTrackDialog(MediaAdapter mediaAdapter,MediaDialogPresenter mediaDialogPresenter){return new MediaDialog(mediaAdapter,mediaDialogPresenter);};
    @Singleton
    @Provides
    PlayListAdapter providePlayListAdapter(){return new PlayListAdapter(context);};
    @Singleton
    @Provides
    PlayListDialog providePlayListDialog(PlayListAdapter playListAdapter, PlayListDialogPresenter playListDialogPresenter){return new PlayListDialog(playListAdapter,playListDialogPresenter);};
  /////////////////////////////////
  @Singleton
  @Provides
  TrackManager providePlaylistManager(Settings settings, PlaylistDAO playlistDAO){
      return new TrackManager(settings,playlistDAO);
  }
  @Singleton
  @Provides
  PlayBackMode providePlayBack(TrackManager trackManager){
    return new PlayBackMode(trackManager);
  }
  @Singleton
  @Provides
  TrackFragmentPresenter providePlaylistFragmentPresenter(TrackAdapter trackAdapter, TrackManager trackManager, PlayBackMode playBackMode){
      return new TrackFragmentPresenter(trackAdapter,trackManager,playBackMode);
  }
  @Singleton
  @Provides
  TrackFragment providePlaylistFragment(){
        return new TrackFragment();
  }
  ///////////////////////////////
  @Singleton
  @Provides
  PlayListFactory.Factory providePlayListFactory(){
      return new PlayListFactory.Factory(){

          @Override
          public Track createTrack() {
              return new Track();
          }

          @Override
          public PlayList createPlayList() {
              return new PlayList();
          }
      };
  }
    //////////////////////////////
  @Singleton
  @Provides
  RootMediaItem provideRootMediaItem(BackMediaItem backMediaItem,
                                     ArtistsContainerMediaItem artistsContainerMediaItem,
                                     AlbumsContainerMediaItem albumsContainerMediaItem){
    return new  RootMediaItem(backMediaItem,artistsContainerMediaItem,albumsContainerMediaItem);};
  @Singleton
  @Provides
  ArtistsContainerMediaItem provideArtistsContainerMediaItem(MediaItemFactory.Factory mediaItemFactory,MediaModel mediaModel){
    return new ArtistsContainerMediaItem(mediaItemFactory,mediaModel);
  };
  @Singleton
  @Provides
  ArtistMediaItem provideArtistMediaItem(MediaItemFactory.Factory mediaItemFactory,MediaModel mediaModel){
    return new ArtistMediaItem(mediaItemFactory,mediaModel);};
  @Singleton
  @Provides
  TrackMediaItem provideTrackMediaItem(){return new TrackMediaItem();};
  @Singleton
  @Provides
  AlbumsContainerMediaItem provideAlbumsContainerMediaItem(){return new  AlbumsContainerMediaItem();};
  @Singleton
  @Provides
  BackMediaItem provideBackMediaItem(){return new BackMediaItem();};
  @Singleton
  @Provides
  MediaModel provideMediaModel(PlayListFactory.Factory playListFactory){return new MediaModel(context,playListFactory);};

  @Singleton
  @Provides
  MediaItemFactory.Factory provideMediaItemFactory(MediaModel mediaModel){
    return new MediaItemFactory.Factory(){
      @Override
      public IMediaItem createBackMediaItem() {
        return new BackMediaItem();
      }

      @Override
      public ArtistMediaItem createArtistMediaItem() {
        return new ArtistMediaItem(this,mediaModel);
      }

      @Override
      public TrackMediaItem createTrackMediaItem() {
        return new TrackMediaItem();
      }

    };
  }
  /////////////////////////////
  @Singleton
  @Provides
  AppDatabase  provideDatabase (){
      String defPlaylistName=context.getResources().getString(R.string.def);
      return  Room.databaseBuilder(context, AppDatabase.class, "YSDatabase")
          //  .allowMainThreadQueries()
              .fallbackToDestructiveMigration()
              .addCallback(new RoomDatabase.Callback() {
                @Override
                public void onCreate(SupportSQLiteDatabase db) {
                  super.onCreate(db);
                  db.execSQL("INSERT INTO playlist(name) VALUES('"+defPlaylistName+"');");
                }
              })
              .build();
  };
  @Singleton
  @Provides
  PlaylistDAO providePlaylistDAO (AppDatabase appDatabase){
      return  appDatabase.playlistDao();
  };
  @Singleton
  @Provides
  PlayListDialogPresenter providePlayListDialogPresenter(PlaylistDAO playlistDAO,PlayListFactory.Factory playListFactory,Settings settings){
      return new PlayListDialogPresenter(playlistDAO,playListFactory,settings);
  };
  //////////////////////////
  @Singleton
  @Provides
  Settings provideSettings(){
      return new Settings(context);
  };

}
