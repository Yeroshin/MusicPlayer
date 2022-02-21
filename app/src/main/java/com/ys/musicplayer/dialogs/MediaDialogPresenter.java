package com.ys.musicplayer.dialogs;

import android.net.Uri;
import android.util.Log;

import com.ys.musicplayer.adapters.MediaAdapter;
import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.media.IMediaItem;
import com.ys.musicplayer.media.ITrackDialogPresenter;
import com.ys.musicplayer.media.MediaItemFactory;
import com.ys.musicplayer.media.PlayListFactory;
import com.ys.musicplayer.media.RootMediaItem;
import com.ys.musicplayer.models.TrackManager;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MediaDialogPresenter implements ITrackDialogPresenter {
    UniversalAdapter adapter;
    RootMediaItem rootMediaItem;
    TrackManager trackManager;
    public MediaDialogPresenter(MediaAdapter trackAdapter, RootMediaItem rootMediaItem, TrackManager trackManager) {
        this.adapter=trackAdapter;
        this.rootMediaItem=rootMediaItem;
        this.trackManager = trackManager;


    }

    public void init(UniversalAdapter adapter){

        rootMediaItem.onClick(adapter);

    }
    public void onAccept(){
        ArrayList selectedObservables=new ArrayList();
        for(int i=0;i<adapter.selectedItems.size();i++){
            if((boolean)adapter.selectedItems.get(i)){
                selectedObservables.add((Object) ((IMediaItem)adapter.items.get(i)).getContent());
            }
        }

        Observable.concat(selectedObservables)
                .flatMapCompletable(
                        data->{
                            return trackManager.addTracks((ArrayList) data);
                        }
                )
                .subscribeOn(Schedulers.io())
                .subscribe(
                        ()->{
                            adapter.clearItems();
                        },//complete
                        e->{}//error

                );
    }
}
