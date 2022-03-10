package com.ys.musicplayer.media;

import com.ys.musicplayer.adapters.UniversalAdapter;

import io.reactivex.Completable;

public interface IPlayListDialogPresenter  {
    void init(UniversalAdapter universalAdapter);
    boolean onPlaylistAddButton(String name);
    void onAccept();
}
