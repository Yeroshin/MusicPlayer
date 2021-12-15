package com.ys.musicplayer.media;

import com.ys.musicplayer.adapters.UniversalAdapter;

import io.reactivex.Completable;

public interface IPlayListDialogPresenter  {
    void init(UniversalAdapter universalAdapter);
    void onPlaylistAddButton(String name);
    void onAccept();
}
