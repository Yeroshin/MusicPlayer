package com.ys.musicplayer.media;

import android.net.Uri;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class AlbumsContainerMediaItem extends IMediaItem{
    private String title= "Albums";

    ArtistsContainerMediaItem artistsMediaItem;

    IMediaItem backMediaItem;
    public AlbumsContainerMediaItem() {
        checkable=false;
    }
    @Override
    public String getTitle(){
        return title;
    }

  /*  @Override
    public void onClick(UniversalAdapter adapter) {
        TrackMediaItem trackMediaItem=new TrackMediaItem();//tmp
        ArrayList arrayList=new ArrayList();
        arrayList.add(backMediaItem);
        arrayList.add(trackMediaItem);
        adapter.setItems(arrayList);
    }*/


    @Override
    public void setTitle(String title) {

    }

    @Override
    public Observable<ArrayList> subscribeContent() {
        return Observable.create(
                subscriber->{
                    TrackMediaItem trackMediaItem=new TrackMediaItem();//tmp
                    ArrayList almbumsItems=new ArrayList();
                    almbumsItems.add(backMediaItem);
                    almbumsItems.add(trackMediaItem);
                    subscriber.onNext(almbumsItems);
                    subscriber.onComplete();
                }
        );
    }

    @Override
    public Observable<ArrayList> subscribeBranches() {
        return null;
    }

  /*  @Override
    public Observable getContent() {
        return null;
    }*/
}
