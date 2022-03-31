package com.ys.musicplayer.di.components;


import com.ys.musicplayer.MyService;
import com.ys.musicplayer.di.modules.EqualizerModule;
import com.ys.musicplayer.di.modules.PlayerFragmentModule;
import com.ys.musicplayer.di.modules.ServiceModule;


import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ServiceModule.class})
@Singleton
public interface ServiceComponent {
    void inject(MyService service);
}