package com.ys.musicplayer.di.modules;

import android.content.Context;

import com.ys.musicplayer.db.PlaylistDAO;
import com.ys.musicplayer.fragments.PlayerFragmentPresenter;
import com.ys.musicplayer.models.Settings;
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
    PlayerFragmentPresenter  providePlayerFragmentPresenter(ServiceMessenger serviceMessenger, Settings settings, PlaylistDAO playlistDAO){
        return new PlayerFragmentPresenter(serviceMessenger,settings,playlistDAO);
    };
    ///////////////////////////////////////////////

    //////////////////////////////////
}
