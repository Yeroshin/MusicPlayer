package com.ys.musicplayer.media;

import android.net.Uri;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;

import java.util.List;

import io.reactivex.Flowable;

public class TrackMediaItem implements IMediaItem{
    private String title;

    public TrackMediaItem() {
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void onClick(UniversalAdapter adapter){

    }

    @Override
    public void setBackItem(IMediaItem mediaItem) {
    }



    @Override
    public void setTitle(String title) {
        this.title=title;
    }

    @Override
    public Uri getContent() {
        return null;
    }
}
