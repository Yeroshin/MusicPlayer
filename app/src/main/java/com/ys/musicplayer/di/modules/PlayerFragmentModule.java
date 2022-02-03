package com.ys.musicplayer.di.modules;

import android.content.Context;

import com.ys.musicplayer.fragments.PlayerFragmentPresenter;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.player.IdleState;
import com.ys.musicplayer.player.PausedState;
import com.ys.musicplayer.player.PlayingState;
import com.ys.musicplayer.player.PlayBackMode;
import com.ys.musicplayer.player.Player;
import com.ys.musicplayer.player.PlayerStateFactory;
import com.ys.musicplayer.player.SystemPlayer;
import com.ys.musicplayer.utils.ServiceMessenger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class PlayerFragmentModule {
    private Context context;
    public PlayerFragmentModule(Context context){
        this.context=context;
    }
    @Singleton
    @Provides
    PlayerFragmentPresenter  providePlayerFragmentPresenter(ServiceMessenger serviceMessenger){
        return new PlayerFragmentPresenter(serviceMessenger);
    };
    ///////////////////////////////////////////////

    //////////////////////////////////
}
