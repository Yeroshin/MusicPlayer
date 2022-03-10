package com.ys.musicplayer.media;

import android.net.Uri;
import android.provider.MediaStore;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.Track;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class ArtistMediaItem implements IMediaItem{
    private String title;
    IMediaItem backMediaItemParent;
    IMediaItem backMediaItemChild;
    MediaItemFactory.Factory mediaItemFactory;
    MediaModel mediaModel;
    TrackMediaItem trackMediaItem;


    public ArtistMediaItem(MediaItemFactory.Factory mediaItemFactory,MediaModel mediaModel) {
        this.mediaItemFactory=mediaItemFactory;
        this.mediaModel=mediaModel;

    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void onClick(UniversalAdapter adapter) {
        ArrayList<String> tracks=mediaModel.query(MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST,title);
        ArrayList trackItems=new ArrayList();
        backMediaItemChild=mediaItemFactory.createBackMediaItem();
        backMediaItemChild.setBackItem(this);
        trackItems.add(backMediaItemParent);
        for (int i=0;i<tracks.size();i++){
            trackMediaItem=mediaItemFactory.createTrackMediaItem();
            trackMediaItem.setBackItem(backMediaItemChild);
            trackMediaItem.setTitle(tracks.get(i));
            trackItems.add(trackMediaItem);
        }
        adapter.setItems(trackItems);
    }

    @Override
    public void setBackItem(IMediaItem mediaItem) {
        this.backMediaItemParent=mediaItem;
    }



    @Override
    public void setTitle(String title) {
        this.title=title;
    }

    @Override
    public Observable getContent() {
        return Observable.create(subscriber -> {
            // TimeUnit.SECONDS.sleep(10);

            ArrayList tracks=mediaModel.getTracks(MediaStore.Audio.Media.ARTIST,title);

            subscriber.onNext( tracks);
            subscriber.onComplete();
        });

    }
}
