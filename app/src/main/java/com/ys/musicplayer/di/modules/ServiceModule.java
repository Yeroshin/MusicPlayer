package com.ys.musicplayer.di.modules;

import android.content.Context;

import com.ys.musicplayer.INotification;
import com.ys.musicplayer.INotificationView;
import com.ys.musicplayer.NotificationView;
import com.ys.musicplayer.YSNotification;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.player.IdleState;
import com.ys.musicplayer.player.PausedState;
import com.ys.musicplayer.player.PlayBackMode;
import com.ys.musicplayer.player.Player;
import com.ys.musicplayer.player.PlayerStateFactory;
import com.ys.musicplayer.player.PlayingState;
import com.ys.musicplayer.player.SystemPlayer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {
    @Singleton
    @Provides
    INotification provideNotification(){return new YSNotification();};
    @Singleton
    @Provides
    INotificationView provideNotificationView(){return new NotificationView();};
    @Singleton
    @Provides
    SystemPlayer provideSystemPlayer (){return new SystemPlayer();};

    @Singleton
    @Provides
    PlayBackMode providePlayBackMode(){return new PlayBackMode();};
    @Singleton
    @Provides
    Player providePlayer(PlayerStateFactory.Factory playerStateFactory){return new Player(playerStateFactory);};
    @Singleton
    @Provides
    PlayerStateFactory.Factory providePlayerStateFactory (SystemPlayer systemPlayer, TrackManager trackManager){
        return new  PlayerStateFactory.Factory(){

            @Override
            public IdleState getIdleState() {
                return new IdleState(trackManager,systemPlayer,this);
            }


            @Override
            public PlayingState getPlayingState() {
                return new PlayingState(systemPlayer, this);
            }

            @Override
            public PausedState getPausedState() {
                return new PausedState(trackManager,systemPlayer,this);
            }

        };
    }
}
