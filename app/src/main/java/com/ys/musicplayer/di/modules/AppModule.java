package com.ys.musicplayer.di.modules;

import android.content.Context;

import com.ys.musicplayer.ClientService;
import com.ys.musicplayer.INotification;
import com.ys.musicplayer.INotificationView;
import com.ys.musicplayer.MainContract;
import com.ys.musicplayer.MainPresenter;
import com.ys.musicplayer.Model;
import com.ys.musicplayer.NotificationView;
import com.ys.musicplayer.StringGetter;
import com.ys.musicplayer.YSNotification;
import com.ys.musicplayer.adapters.TrackAdapter;
import com.ys.musicplayer.dialogs.TrackManager;
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

    @Singleton
    @Provides
    PlayBackMode providePlayBackMode(){return new PlayBackMode();};
    @Singleton
    @Provides
    SystemPlayer provideSystemPlayer(){return new SystemPlayer(context);};
    @Singleton
    @Provides
    Player providePlayer(SystemPlayer player,PlayBackMode playBackMode){return new Player(player,playBackMode);};

    @Provides
    TrackAdapter provideRecyclerListAdapter(){return new TrackAdapter(context);};
    @Singleton
    @Provides
    TrackManager provideTrackManager(){return new TrackManager();};

}
