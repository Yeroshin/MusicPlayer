package com.ys.musicplayer.di.components;

import com.ys.musicplayer.MainActivity;
import com.ys.musicplayer.MyService;
import com.ys.musicplayer.NotificationView;
import com.ys.musicplayer.PlaylistFragment;
import com.ys.musicplayer.Widget;
import com.ys.musicplayer.di.modules.AppModule;
import com.ys.musicplayer.dialogs.TrackManager;

import javax.inject.Singleton;

import dagger.Component;
@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(Widget widget);
    void inject(MyService service);
    void inject(NotificationView notificationView);
    void inject(PlaylistFragment playlistFragment);
    void inject(TrackManager trackManager);
  /*  void inject(Model model);
    void inject(MainPresenter mainPresenter);*/


}
