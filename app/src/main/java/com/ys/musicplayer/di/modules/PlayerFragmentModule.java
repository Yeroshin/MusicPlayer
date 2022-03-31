package com.ys.musicplayer.di.modules;

import android.content.Context;

import com.ys.musicplayer.fragments.IPlayerFragment;
import com.ys.musicplayer.presenters.PlayerPresenterProxy;
import com.ys.musicplayer.player.IPlayerPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class PlayerFragmentModule {
    Context context;
    IPlayerFragment playerFragment;
    public PlayerFragmentModule(Context context,IPlayerFragment playerFragment) {
        this.context = context;
        this.playerFragment=playerFragment;
    }

    @Singleton
    @Provides
    IPlayerPresenter providePlayerFragmentPresenter(){
        return new PlayerPresenterProxy(context,playerFragment);
    };
}
