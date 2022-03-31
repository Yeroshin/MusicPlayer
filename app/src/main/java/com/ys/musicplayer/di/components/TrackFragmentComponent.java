package com.ys.musicplayer.di.components;

import com.ys.musicplayer.di.modules.PlayerModule;
import com.ys.musicplayer.di.modules.TrackFragmentModule;
import com.ys.musicplayer.fragments.PlayerFragmentProxy;
import com.ys.musicplayer.fragments.TrackFragment;

import javax.inject.Singleton;

import dagger.Component;
@Component(modules = {TrackFragmentModule.class})
@Singleton
public interface TrackFragmentComponent {
    void inject(TrackFragment trackFragment);
}
