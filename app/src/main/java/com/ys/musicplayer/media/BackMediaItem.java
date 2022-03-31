package com.ys.musicplayer.media;

import android.net.Uri;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;


public class BackMediaItem extends IMediaItem{
    private String title="..";
    private IMediaItem backContent;

    public BackMediaItem(IMediaItem backContent) {
        this.backContent=backContent;
        checkable=false;
        icon=3;
    }
    @Override
    public String getTitle(){
        return title;
    };

   /* @Override
    public void onClick(UniversalAdapter adapter) {
        backContent.onClick(adapter);
    }*/

    @Override
    public void setTitle(String title) {

    }

    @Override
    public Observable<ArrayList> subscribeContent() {
        return backContent.subscribeContent();
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
