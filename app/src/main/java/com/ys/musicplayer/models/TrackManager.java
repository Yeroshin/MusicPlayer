package com.ys.musicplayer.models;

import android.net.Uri;
import android.util.Log;

import com.ys.musicplayer.db.PlaylistDAO;
import com.ys.musicplayer.db.Track;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class TrackManager {
    private ArrayList<Track> tracks;
    private BehaviorSubject subjectTracks;
    private BehaviorSubject  subjectCurrentTrack;
    private BehaviorSubject  subjectSelectedTrack;


    private Settings settings;
    private PlaylistDAO playlistDAO;
    private int playlistId;
    private int currentTrack;
    private int selectedTrack;

    public TrackManager(Settings settings, PlaylistDAO playlistDAO){
        this.settings=settings;
        this.playlistDAO=playlistDAO;


        subjectTracks = BehaviorSubject.create();
        subjectCurrentTrack = BehaviorSubject .create();
        subjectSelectedTrack= BehaviorSubject .create();
        subjectSelectedTrack.onNext(-1);///tmp -

        //////////////////////////////////
         settings.subscribePlaylistId()
                .map(
                        id-> {
                           // tracks = new ArrayList();
                            playlistId=id;
                            return id;
                        })
                .flatMap(
                        id->{
                            return playlistDAO.subscribeTracksFromPlaylist(id);
                        }
                )
                 .subscribeOn(Schedulers.io())
                 //.observeOn(AndroidSchedulers.mainThread())
         .subscribe(
                 newTracks->{
                     tracks=(ArrayList) newTracks;
                     subjectTracks.onNext(newTracks);
                 },
                 e->{},
                 ()->{},
                 s->{}
         );
        settings.subscribeCurrentTrack()
                .subscribeOn(Schedulers.io())
                .subscribe(
                       trackId->{
                           this.currentTrack=trackId;
                           subjectCurrentTrack.onNext(trackId);
                       }
                );

        //////////////////////////////////
    }

    //////////////////////////////

    public Observable<ArrayList> subscribeTracks(){
      /*  Observable observable = Observable.create(subscriber -> {
            subscriber.onNext(tracks);
        });
        observable
                //.subscribeOn(Schedulers.io())
                .subscribe(
                        // o->Log.d("TAG",  "First  : " + o);
                        data->subjectTracks.onNext(data),
                        error->{}
                );*/
        return subjectTracks;
    }
    //////////////////////////////
    public Completable addTracks(ArrayList tracks){
        return Observable.fromArray(tracks)
                .map(
                        data->{
                            for(int i=0;i<data.size();i++){
                                ((Track)data.get(i)).setPlaylist(playlistId);
                            }
                            return data;
                        }
                )
                .flatMapCompletable(
                        track->{
                            return playlistDAO.insertTrack(track);
                        }
                );
              /*  .subscribe(
                        ()->{
                            Log.d("TAG",  "complete  : " );
                        },
                        o-> Log.d("TAG",  "error : " + o)
                );*/
       /* playlistDAO.insert(tracks)
                .subscribe(
                        ()->{},
                        o-> Log.d("TAG",  "First  : " + o)
                );*/
       /* for(int i=0;i<tracks.size();i++){
            tracks.get(i).setPlaylist(playlistId);

            playlistDAO.insert(tracks.get(i))

            .subscribe(
                    ()->{},
                    o-> Log.d("TAG",  "First  : " + o)
            );*/
    }
    public void removeTrack(int position){
        playlistDAO.removeTrack(playlistId,tracks.get(position).getId())
                .subscribe(
                        ()->{},
                        o-> Log.d("TAG",  "First  : " + o)
                );
    }
    public Track getTrack(int position){
        return tracks.get(position);
    }
    ///////////////////////////////
    public void setCurrentTrack(int position){
        currentTrack=position;
        subjectCurrentTrack.onNext(position);
    }
    public Observable<Integer> subscribeCurrentTrack(){

        return subjectCurrentTrack;
    }
    ///////////////////////////////
    public void setSelectedTrack(int position){
        selectedTrack=position;
        subjectSelectedTrack.onNext(position);
    }
    public Observable<Integer> subscribeSelectedTrack(){

        return subjectSelectedTrack;
    }
    //////////////////////////////


    //////////////////////////////


}
