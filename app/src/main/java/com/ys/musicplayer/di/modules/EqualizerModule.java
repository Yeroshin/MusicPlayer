package com.ys.musicplayer.di.modules;

import android.content.Context;

import com.ys.musicplayer.models.EqualizerModel;
import com.ys.musicplayer.presenters.EqualizerPresenter;
import com.ys.musicplayer.fragments.IEqualizerFragment;
import com.ys.musicplayer.models.Settings;
import com.ys.musicplayer.models.SystemPlayer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class EqualizerModule {
    private Context context;
    public EqualizerModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    EqualizerPresenter provideFragmentPresenter (IEqualizerFragment equalizerFragment, Settings settings, EqualizerModel equalizerModel){return new EqualizerPresenter(equalizerFragment,settings,equalizerModel);};
    @Singleton
    @Provides
    EqualizerModel provideEqualizerModel( SystemPlayer systemPlayer){return new EqualizerModel(context,systemPlayer);};

}
