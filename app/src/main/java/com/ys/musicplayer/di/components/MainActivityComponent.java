package com.ys.musicplayer.di.components;

import com.ys.musicplayer.MainActivity;
import com.ys.musicplayer.di.modules.MainActivityModule;
import javax.inject.Singleton;
import dagger.Component;

@Component(modules = MainActivityModule.class)
@Singleton
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
