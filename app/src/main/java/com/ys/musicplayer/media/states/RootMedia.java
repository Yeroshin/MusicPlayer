package com.ys.musicplayer.media.states;

import android.net.Uri;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.dialogs.MediaDialogPresenter;
import com.ys.musicplayer.media.AlbumsContainerMediaItem;
import com.ys.musicplayer.media.ArtistsContainerMediaItem;
import com.ys.musicplayer.media.BackMediaItem;
import com.ys.musicplayer.media.IMediaItem;
import com.ys.musicplayer.media.MediaItemFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class RootMedia extends IMediaItem {
    private String title="media";
    private MediaItemFactory.Factory mediaItemFactory;
    public RootMedia(MediaItemFactory.Factory mediaItemFactory) {
       this.mediaItemFactory=mediaItemFactory;
       checkable=false;
    }
    @Override
    public String getTitle(){
        return title;
    };

   /* @Override
    public void onClick(UniversalAdapter adapter) {
        ArrayList arrayList= new ArrayList();
        arrayList.add(artistsContainerMediaItem);
        arrayList.add(albumsContainerMediaItem);
        adapter.setItems(arrayList);
    }*/


    @Override
    public void setTitle(String title) {

    }

    @Override
    public Observable<ArrayList> subscribeContent() {
        return Observable.create(
                subscriber->{
                    ArrayList arrayList= new ArrayList();
                    arrayList.add(mediaItemFactory.createArtistsContainerMediaItem(this));
                    arrayList.add(mediaItemFactory.createAlbumsContainerMediaItem());
                    subscriber.onNext( arrayList);
                    subscriber.onComplete();
                }
        );
    }

    @Override
    public Observable  subscribeBranches() {
        return null;
    }
}
