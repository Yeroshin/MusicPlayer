package com.ys.musicplayer.di.modules;

import androidx.fragment.app.FragmentActivity;

import com.ys.musicplayer.FragmentFactory;
import com.ys.musicplayer.adapters.YSFragmentStateAdapter;
import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.fragments.EqualizerFragment;
import com.ys.musicplayer.fragments.TrackFragment;
import com.ys.musicplayer.media.PlayListFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    FragmentActivity fragmentActivity;
    public MainActivityModule(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }



    @Singleton
    @Provides
    YSFragmentStateAdapter provideYSFragmentStateAdapter(FragmentFactory.Factory fragmentFactory){
        return new YSFragmentStateAdapter(fragmentActivity,fragmentFactory);
    }

    @Singleton
    @Provides
    FragmentFactory.Factory provideFragmentFactory(){
        return new FragmentFactory.Factory(){


            @Override
            public TrackFragment createTrackFragment() {
                return new TrackFragment();
            }

            @Override
            public EqualizerFragment createEqualizerFragment() {
                return new EqualizerFragment();
            }
        };
    }
}
