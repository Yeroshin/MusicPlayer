package com.ys.musicplayer.dialogs;

import android.net.Uri;
import android.util.Log;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.media.IMediaItem;
import com.ys.musicplayer.media.ITrackDialogPresenter;
import com.ys.musicplayer.media.PlayListFactory;
import com.ys.musicplayer.models.TrackManager;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TrackDialogPresenter implements ITrackDialogPresenter {
    UniversalAdapter adapter;
    PlayListFactory.Factory playlistFactory;
    TrackManager trackManager;
    public TrackDialogPresenter(PlayListFactory.Factory playlistFactory, TrackManager trackManager) {
        this.trackManager = trackManager;
        this.playlistFactory=playlistFactory;
    }

    public void init(UniversalAdapter adapter){
        this.adapter=adapter;
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
                        ()->{},//complete
                        e->{}//error

                );
    }
}
