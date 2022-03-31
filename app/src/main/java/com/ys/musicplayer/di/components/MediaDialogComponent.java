package com.ys.musicplayer.di.components;

import com.ys.musicplayer.di.modules.MediaDialogModule;
import com.ys.musicplayer.dialogs.IMediaDialog;
import com.ys.musicplayer.dialogs.MediaDialog;
import javax.inject.Singleton;
import dagger.Component;

@Component(modules = MediaDialogModule.class)
@Singleton
public interface MediaDialogComponent {
    void inject(MediaDialog mediaDialog);
}
