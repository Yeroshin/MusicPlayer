package com.ys.musicplayer.di.components;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.di.modules.PlayerFragmentModule;
import com.ys.musicplayer.di.modules.ServiceModule;
import com.ys.musicplayer.fragments.PlayerFragment;

import javax.inject.Singleton;

import dagger.Component;
@Component(modules = {PlayerFragmentModule.class})
@Singleton
public interface PlayerFragmentComponent {
    void inject(PlayerFragment playerFragment);
}
