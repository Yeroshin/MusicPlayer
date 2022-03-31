package com.ys.musicplayer.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ys.musicplayer.FragmentFactory;
import com.ys.musicplayer.fragments.EqualizerFragment;
import com.ys.musicplayer.fragments.TrackFragment;

public class YSFragmentStateAdapter extends FragmentStateAdapter {
    FragmentFactory.Factory fragmentFactory;
    public YSFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, FragmentFactory.Factory fragmentFactory) {
        super(fragmentActivity);
        this.fragmentFactory=fragmentFactory;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return fragmentFactory.createEqualizerFragment();
            default:
                return fragmentFactory.createTrackFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
