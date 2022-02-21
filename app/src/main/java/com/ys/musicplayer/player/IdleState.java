package com.ys.musicplayer.player;

import android.net.Uri;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.ys.musicplayer.models.TrackManager;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class IdleState implements State {
    private Player player;
    private PlayerStateFactory.Factory playerStateFactory;

    public IdleState(Player player, TrackManager trackManager, SystemPlayer systemPlayer,PlayerStateFactory.Factory playerStateFactory) {
        this.player = player;
        this.playerStateFactory = playerStateFactory;
        ///////////////////////
        systemPlayer.init();
    }

    @Override
    public void onPlay() {
        player.changeState(playerStateFactory.getPreparingState());
    }

    @Override
    public void onNext() {
        player.changeState(playerStateFactory.getPreparingState());
    }

    @Override
    public void onPrevious() {
        player.changeState(playerStateFactory.getPreparingState());
    }
}
