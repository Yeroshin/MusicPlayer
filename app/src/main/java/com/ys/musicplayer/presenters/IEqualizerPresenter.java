package com.ys.musicplayer.presenters;

public interface IEqualizerPresenter {
    void onSetBandLevel(int band,int level);
    void onEqualizerSwitchChanged(boolean isChecked);
}
