package com.ys.musicplayer.di.components;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.di.modules.AppModule;
import com.ys.musicplayer.di.modules.PlayerModule;
import com.ys.musicplayer.fragments.IPlayerFragment;
import com.ys.musicplayer.fragments.PlayerFragment;
import com.ys.musicplayer.fragments.PlayerFragmentProxy;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {PlayerModule.class})
@Singleton
public interface PlayerComponent {
    void inject(PlayerFragmentProxy playerView);
}
