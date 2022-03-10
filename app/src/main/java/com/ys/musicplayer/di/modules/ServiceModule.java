package com.ys.musicplayer.di.modules;

import android.content.Context;
import android.content.IntentFilter;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.YESNotification;
import com.ys.musicplayer.audio_effects.EqualizerModel;
import com.ys.musicplayer.fragments.EqualizerFragmentPresenter;
import com.ys.musicplayer.models.Settings;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.player.IdleState;
import com.ys.musicplayer.player.PausedState;
import com.ys.musicplayer.player.Player;
import com.ys.musicplayer.utils.PlayBackMode;

import com.ys.musicplayer.player.PlayerStateFactory;
import com.ys.musicplayer.player.PlayingState;
import com.ys.musicplayer.player.PreparingState;
import com.ys.musicplayer.models.SystemPlayer;
import com.ys.musicplayer.utils.UIMessenger;

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




    ////////////////////////////////////////////////

    @Singleton
    @Provides
    SystemPlayer provideSystemPlayer (){return new SystemPlayer(context);};
    @Singleton
    @Provides
    Player providePlayer(){return new Player();};

    @Singleton
    @Provides
    PlayerStateFactory.Factory providePlayerStateFactory(Player player, TrackManager trackManager, SystemPlayer systemPlayer, PlayBackMode playBackMode){
        return new PlayerStateFactory.Factory() {
            @Override
            public IdleState getIdleState() {
                return new IdleState(player,trackManager,systemPlayer,this,playBackMode);
            }

            @Override
            public PreparingState getPreparingState() {
                return new PreparingState(player,trackManager,systemPlayer,this,playBackMode);
            }

            @Override
            public PlayingState getPlayingState() {
                return new PlayingState(player,trackManager,systemPlayer,this,playBackMode);
            }

            @Override
            public PausedState getPausedState() {
                return new PausedState(player,trackManager,systemPlayer,this,playBackMode);
            }
        };
    }

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
    EqualizerModel provideEqualizerModel( SystemPlayer systemPlayer){return new EqualizerModel(context,systemPlayer);};

    @Singleton
    @Provides
    EqualizerFragmentPresenter provideFragmentPresenter (Settings settings, EqualizerModel equalizerModel){return new EqualizerFragmentPresenter(settings,equalizerModel);};
    @Provides
    UIMessenger provideUIMessenger(Player player, PlayerStateFactory.Factory playerStateFactory, EqualizerFragmentPresenter equalizerFragmentPresenter){return new UIMessenger(context,player,playerStateFactory,equalizerFragmentPresenter);};

    ////////////////////////////////////////////////
}
