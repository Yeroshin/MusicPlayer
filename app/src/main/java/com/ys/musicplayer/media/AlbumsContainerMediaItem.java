package com.ys.musicplayer.media;

import android.net.Uri;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class AlbumsContainerMediaItem implements IMediaItem{
    private String title= "Albums";
    @Inject
    ArtistsContainerMediaItem artistsMediaItem;

    IMediaItem backMediaItem;
    public AlbumsContainerMediaItem() {
        this.artistsMediaItem=artistsMediaItem;

    }
    @Override
    public String getTitle(){
        return title;
    }

    @Override
    public void onClick(UniversalAdapter adapter) {
        TrackMediaItem trackMediaItem=new TrackMediaItem();//tmp
        ArrayList arrayList=new ArrayList();
        arrayList.add(backMediaItem);
        arrayList.add(trackMediaItem);
        adapter.setItems(arrayList);
    }

    @Override
    public void setBackItem(IMediaItem mediaItem) {
        backMediaItem=new BackMediaItem();
        backMediaItem.setBackItem(mediaItem);
    }



    @Override
    public void setTitle(String title) {

    }

    @Override
    public Observable getContent() {
        return null;
    }
}
