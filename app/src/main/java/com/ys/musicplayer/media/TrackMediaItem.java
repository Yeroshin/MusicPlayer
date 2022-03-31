package com.ys.musicplayer.media;

import android.net.Uri;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class TrackMediaItem extends IMediaItem{
    private String title;

    public TrackMediaItem() {
        checkable=true;
        icon=2;
    }

    @Override
    public String getTitle() {
        return title;
    }

  /*  @Override
    public void onClick(UniversalAdapter adapter){

    }*/


    @Override
    public void setTitle(String title) {
        this.title=title;
    }

    @Override
    public Observable<ArrayList> subscribeContent() {
        return Observable.empty();
    }

    @Override
    public Observable<ArrayList> subscribeBranches() {
        return null;
    }

   /* @Override
    public Observable getContent() {
        return null;
    }*/
}
