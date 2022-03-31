package com.ys.musicplayer.di.components;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.di.modules.EqualizerModule;
import com.ys.musicplayer.di.modules.ServiceModule;
import com.ys.musicplayer.fragments.EqualizerFragmentProxy;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ServiceModule.class, EqualizerModule.class})
@Singleton
public interface EqualizerComponent {
    void inject(EqualizerFragmentProxy equalizerFragmentProxy);
}
