package com.ys.musicplayer.fragments;

import android.content.Intent;

import com.ys.musicplayer.presenters.IEqualizerPresenter;

public class EqualizerFragmentProxy implements IEqualizerFragment,Proxy{
    public EqualizerFragmentProxy() {
        this.equalizerFragmentPresenter = equalizerFragmentPresenter;

    }

    private IEqualizerPresenter equalizerFragmentPresenter;
    @Override
    public void setEqualizerSwitch(boolean isEnabled) {

    }

    @Override
    public void setEqualizerSeekbarsValue() {

    }

    @Override
    public void handleMessage(Intent intent) {

    }
}
