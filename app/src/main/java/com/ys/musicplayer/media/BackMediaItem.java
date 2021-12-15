package com.ys.musicplayer.media;

import android.net.Uri;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;

import java.util.List;

import io.reactivex.Flowable;


public class BackMediaItem implements IMediaItem{
    private String title="..";
    private IMediaItem backContent;

    public BackMediaItem() {

    }
    @Override
    public String getTitle(){
        return title;
    };

    @Override
    public void onClick(UniversalAdapter adapter) {
        backContent.onClick(adapter);
    }

    @Override
    public void setBackItem(IMediaItem backContent) {
        this.backContent=backContent;
    }



    @Override
    public void setTitle(String title) {

    }

    @Override
    public Uri getContent() {
        return null;
    }

}
