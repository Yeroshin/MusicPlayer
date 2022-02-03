package com.ys.musicplayer.di.components;

import com.ys.musicplayer.MainActivity;
import com.ys.musicplayer.MyService;
import com.ys.musicplayer.NotificationView;
import com.ys.musicplayer.Widget;
import com.ys.musicplayer.di.modules.AppModule;
import com.ys.musicplayer.di.modules.PlayerFragmentModule;
import com.ys.musicplayer.di.modules.ServiceModule;
import com.ys.musicplayer.dialogs.MediaDialog;
import com.ys.musicplayer.fragments.PlayerFragment;
import com.ys.musicplayer.fragments.TrackFragment;

import javax.inject.Singleton;

import dagger.Component;
@Component(modules = {AppModule.class, PlayerFragmentModule.class, ServiceModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(Widget widget);
    void inject(MyService service);
    void inject(NotificationView notificationView);
    void inject(MediaDialog mediaDialog);
    void inject(TrackFragment trackFragment);
    void inject(PlayerFragment playerFragment);


}
