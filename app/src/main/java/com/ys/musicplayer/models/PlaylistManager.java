package com.ys.musicplayer.models;

import com.ys.musicplayer.Settings;
import com.ys.musicplayer.db.PlaylistDAO;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class PlaylistManager {
    ArrayList tracks;
    BehaviorSubject subjectTracks;
    Settings settings;
    PlaylistDAO playlistDAO;
    public PlaylistManager(Settings settings, PlaylistDAO playlistDAO){
        this.settings=settings;
        this.playlistDAO=playlistDAO;
        subjectTracks = BehaviorSubject.create();
        tracks=new ArrayList();
        settings.subscribePlaylistId();
        Observable observable=settings.subscribePlaylistId().map(
                playlistId->playlistDAO.subscribeTracksFromPlaylist(playlistId)
                .subscribe(
                        track->{
                            tracks.add(track);
                            subjectTracks.onNext(track);
                        }
                )
        );
    }
    public BehaviorSubject subscribeTracks(){
        Observable observable = Observable.create(subscriber -> {
            subscriber.onNext(tracks);
        });
        observable
                //.subscribeOn(Schedulers.io())
                .subscribe(
                        // o->Log.d("TAG",  "First  : " + o);
                        data->subjectTracks.onNext(data)
                );
        return subjectTracks;
    }
}
