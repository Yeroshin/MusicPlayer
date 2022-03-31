package com.ys.musicplayer.di.components;

import com.ys.musicplayer.MainActivity;
import com.ys.musicplayer.MyService;
import com.ys.musicplayer.NotificationView;
import com.ys.musicplayer.Widget;
import com.ys.musicplayer.di.modules.AppModule;
import com.ys.musicplayer.di.modules.EqualizerModule;
import com.ys.musicplayer.di.modules.PlayerFragmentModule;
import com.ys.musicplayer.di.modules.ServiceModule;
import com.ys.musicplayer.dialogs.MediaDialog;
import com.ys.musicplayer.fragments.EqualizerFragment;
import com.ys.musicplayer.fragments.PlayerFragment;
import com.ys.musicplayer.fragments.TrackFragment;

import javax.inject.Singleton;

import dagger.Component;
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(Widget widget);
    void inject(NotificationView notificationView);






}
