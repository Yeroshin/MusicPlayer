package com.ys.musicplayer;

import com.ys.musicplayer.fragments.EqualizerFragment;
import com.ys.musicplayer.fragments.TrackFragment;

public class FragmentFactory {

        public interface Factory{
            TrackFragment createTrackFragment();
            EqualizerFragment createEqualizerFragment();
        }

        public FragmentFactory(){

        }
}
