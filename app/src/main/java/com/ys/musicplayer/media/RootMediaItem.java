package com.ys.musicplayer.media;

import android.net.Uri;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class RootMediaItem implements IMediaItem{
    private String title="media";

    BackMediaItem backItem;
    ArtistsContainerMediaItem artistsContainerMediaItem;
    AlbumsContainerMediaItem albumsContainerMediaItem;


    public RootMediaItem(BackMediaItem backItem,ArtistsContainerMediaItem artistsContainerMediaItem, AlbumsContainerMediaItem albumsContainerMediaItem) {
        this.artistsContainerMediaItem = artistsContainerMediaItem;
        this.albumsContainerMediaItem = albumsContainerMediaItem;
        this.backItem=backItem;
        backItem.setBackItem(this);
        artistsContainerMediaItem.setBackItem(backItem);
        albumsContainerMediaItem.setBackItem(backItem);
    }
    @Override
    public String getTitle(){
        return title;
    };

    @Override
    public void onClick(UniversalAdapter adapter) {
        ArrayList arrayList= new ArrayList();
        arrayList.add(artistsContainerMediaItem);
        arrayList.add(albumsContainerMediaItem);
        adapter.setItems(arrayList);
    }

    @Override
    public void setBackItem(IMediaItem mediaItem) {

    }



    @Override
    public void setTitle(String title) {

    }

    @Override
    public Observable getContent() {
        return null;
    }
}
