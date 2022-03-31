package com.ys.musicplayer.media;


import com.ys.musicplayer.adapters.UniversalAdapter;

import java.util.ArrayList;

import io.reactivex.Observable;

public abstract class IMediaItem {
    protected boolean checkable;
    protected int icon=1;
    public boolean isCheckable(){
        return checkable;
    };
    public int getIcon(){
        return icon;
    }
    public abstract String getTitle();
    public abstract void setTitle(String title);
    public abstract Observable<ArrayList> subscribeContent();
    public abstract Observable<ArrayList> subscribeBranches();
}
