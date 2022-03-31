package com.ys.musicplayer.media;

import android.net.Uri;
import android.provider.MediaStore;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Flowable;
import io.reactivex.Observable;

public class ArtistsContainerMediaItem extends IMediaItem{
    private String title= "Artists";
    IMediaItem backMediaItemParent;
    IMediaItem backMediaItemChild;

    MediaItemFactory.Factory mediaItemFactory;
    MediaModel mediaModel;

    ArtistMediaItem artistMediaItem;


    public ArtistsContainerMediaItem(IMediaItem backMediaItemParent,MediaItemFactory.Factory mediaItemFactory,MediaModel mediaModel) {
        this.backMediaItemParent=backMediaItemParent;
        this.mediaItemFactory=mediaItemFactory;
        this.mediaModel=mediaModel;
        checkable=false;
    }
    @Override
    public String getTitle(){
        return title;
    }

   /* @Override
    public void onClick(UniversalAdapter adapter) {
        ArrayList<String> artists=mediaModel.query(MediaStore.Audio.Media.ARTIST,null,null);
        ArrayList artistsItems=new ArrayList();
        backMediaItemChild=mediaItemFactory.createBackMediaItem();
        backMediaItemChild.setBackItem(this);

        artistsItems.add(backMediaItemParent);
        for (int i=0;i<artists.size();i++){
            artistMediaItem=mediaItemFactory.createArtistMediaItem();
            artistMediaItem.setBackItem(backMediaItemChild);
            artistMediaItem.setTitle(artists.get(i));
            artistsItems.add(artistMediaItem);
        }
        adapter.setItems(artistsItems);
    }*/


    @Override
    public void setTitle(String title) {

    }

    @Override
    public Observable subscribeContent() {
        return  Observable.create(
                subscriber->{
                    ArrayList<String> artists=mediaModel.query(MediaStore.Audio.Media.ARTIST,null,null);
                    ArrayList artistsItems=new ArrayList();
                    backMediaItemChild=mediaItemFactory.createBackMediaItem(backMediaItemParent);
                    artistsItems.add(backMediaItemChild);
                    for (int i=0;i<artists.size();i++){
                        artistMediaItem=mediaItemFactory.createArtistMediaItem(this);
                        artistMediaItem.setTitle(artists.get(i));
                        artistsItems.add(artistMediaItem);
                    }
                    subscriber.onNext(artistsItems);
                    subscriber.onComplete();
                }
        );
    }

    @Override
    public Observable<ArrayList> subscribeBranches() {
        return null;
    }
}
