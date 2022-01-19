package com.ys.musicplayer.di.modules;

import android.content.Context;

import androidx.room.Room;

import com.ys.musicplayer.ClientService;
import com.ys.musicplayer.INotification;
import com.ys.musicplayer.INotificationView;
import com.ys.musicplayer.MainContract;
import com.ys.musicplayer.MainPresenter;
import com.ys.musicplayer.Model;
import com.ys.musicplayer.NotificationView;
import com.ys.musicplayer.Settings;
import com.ys.musicplayer.adapters.PlayListAdapter;
import com.ys.musicplayer.dialogs.TrackDialogPresenter;
import com.ys.musicplayer.fragments.TrackFragment;
import com.ys.musicplayer.StringGetter;
import com.ys.musicplayer.YSNotification;
import com.ys.musicplayer.adapters.MediaAdapter;
import com.ys.musicplayer.adapters.TrackAdapter;
import com.ys.musicplayer.db.AppDatabase;
import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.PlaylistDAO;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.dialogs.PlayListDialog;
import com.ys.musicplayer.dialogs.TrackDialog;
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
import com.ys.musicplayer.player.PlayBackMode;
import com.ys.musicplayer.player.Player;
import com.ys.musicplayer.player.SystemPlayer;

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

    @Singleton
    @Provides
    MainContract.MainPresenter provideMainPresenter(MainContract.Model model){
        return new MainPresenter(model);
    };
    @Singleton
    @Provides
    MainContract.Model provideModel(Player player){
        return new Model(context, player);
    };
    @Singleton
    @Provides
    ClientService provideClientService(){return new StringGetter();};
    @Singleton
    @Provides
    INotification provideNotification(){return new YSNotification();};
    @Singleton
    @Provides
    INotificationView provideNotificationView(){return new NotificationView();};
////////////////////////////////////
    @Singleton
    @Provides
    PlayBackMode providePlayBackMode(){return new PlayBackMode();};
    @Singleton
    @Provides
    SystemPlayer provideSystemPlayer(){return new SystemPlayer(context);};
    @Singleton
    @Provides
    Player providePlayer(SystemPlayer player,PlayBackMode playBackMode){return new Player(player,playBackMode);};
//////////////////////////////////
    @Singleton
    @Provides
    TrackAdapter provideTrackAdapter(){return new TrackAdapter(context);};
    //////////////////////////////
    @Singleton
    @Provides
    TrackDialogPresenter provideTrackDialogPresenter(PlayListFactory.Factory playlistFactory, TrackManager trackManager){return new TrackDialogPresenter(playlistFactory, trackManager);};
    @Singleton
    @Provides
    MediaAdapter provideMediaAdapter(){return new MediaAdapter(context);};
    @Singleton
    @Provides
    TrackDialog provideTrackDialog(MediaAdapter trackAdapter, RootMediaItem rootMediaItem, TrackDialogPresenter trackDialogPresenter){return new TrackDialog(trackAdapter,rootMediaItem,trackDialogPresenter);};
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
  TrackFragmentPresenter providePlaylistFragmentPresenter(TrackAdapter trackAdapter, Settings settings, TrackManager trackManager){
      return new TrackFragmentPresenter(trackAdapter,settings, trackManager);
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
      return  Room.databaseBuilder(context, AppDatabase.class, "YSDatabase")
          //  .allowMainThreadQueries()
          .fallbackToDestructiveMigration()
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
