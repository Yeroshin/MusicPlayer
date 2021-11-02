package com.ys.musicplayer;

import android.content.Context;

public interface MainContract {

    interface MainView{
        void setArtist(String text);
    }
    interface MainPresenter{
        void onAttachView(MainContract.MainView mainView);
        void onClickPlay();

    }
    interface Model{
        String getPlaylist();
        void onPlayListLoaded();
    }
}
