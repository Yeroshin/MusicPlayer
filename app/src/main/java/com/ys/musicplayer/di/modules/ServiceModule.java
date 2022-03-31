package com.ys.musicplayer.di.modules;

import android.content.Context;
import android.content.IntentFilter;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.YESNotification;
import com.ys.musicplayer.fragments.EqualizerFragmentProxy;
import com.ys.musicplayer.fragments.PlayerFragmentProxy;
import com.ys.musicplayer.fragments.Proxy;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.player.IPlayerPresenter;
import com.ys.musicplayer.player.IdleState;
import com.ys.musicplayer.player.PausedState;
import com.ys.musicplayer.player.Player;
import com.ys.musicplayer.utils.PlayBackMode;

import com.ys.musicplayer.player.PlayerStateFactory;
import com.ys.musicplayer.player.PlayingState;
import com.ys.musicplayer.player.PreparingState;
import com.ys.musicplayer.models.SystemPlayer;
import com.ys.musicplayer.utils.ServiceController;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {
    private Context context;
    public ServiceModule(Context context){
        this.context=context;
    }
    ////////////////////


    ////////////////////
   /* @Singleton
    @Provides
    INotification provideNotification(){return new YSNotification();};
    @Singleton
    @Provides
    INotificationView provideNotificationView(){return new NotificationView();};*/
    ///////////////////
    @Singleton
    @Provides
    YESNotification provideYESNotification(){
        YESNotification yesNotification=new YESNotification();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyService.PLAYER_STATE_INFO);
        filter.addAction(MyService.PLAYER_TRACK_INFO);
        context.registerReceiver(yesNotification, filter);
        return yesNotification;
    };

  /*  @Provides
    PausedState providePausedState(Player player, TrackManager trackManager, PlayingState playingState, PreparingState preparingState,SystemPlayer systemPlayer) {
        return new PausedState(player,trackManager,playingState,preparingState,systemPlayer);
    }

    @Provides
    PlayingState providePlayingState(Player player, SystemPlayer systemPlayer, TrackManager trackManager, PreparingState preparingState, PausedState pausedState) {
        return new PlayingState(player,systemPlayer,trackManager,preparingState,pausedState);
    }

    @Provides
    PreparingState providePreparingState(Player player, TrackManager trackManager, PlayingState playingState,SystemPlayer systemPlayer) {
        return new PreparingState(player,trackManager,playingState,systemPlayer);
    }

    @Provides
    public IdleState provideIdleState(Player player,PreparingState preparingState,SystemPlayer systemPlayer) {
        return new IdleState(player,preparingState,systemPlayer);
    }*/

  @Singleton
  @Provides
  PlayerFragmentProxy providePlayerFragmentProxy(){return new PlayerFragmentProxy(context);};

  @Singleton
  @Provides
  EqualizerFragmentProxy provideEqualizerFragmentProxy(){return new EqualizerFragmentProxy();};

  @Singleton
  @Provides
  ServiceController provideServiceController(PlayerFragmentProxy playerFragment, EqualizerFragmentProxy equalizerFragment){return new ServiceController(context,playerFragment,equalizerFragment);};
  ////////////////////////////////////////////////

}
