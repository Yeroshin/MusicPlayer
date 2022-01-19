package com.ys.musicplayer.models;

import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import com.ys.musicplayer.Settings;
import com.ys.musicplayer.db.PlaylistDAO;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.media.PlayListFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class TrackManager {
    ArrayList<Track> tracks;
    PublishSubject subjectTracks;
    PublishSubject subjectPLaylistId;
    Settings settings;
    PlaylistDAO playlistDAO;
    int playlistId;

    public TrackManager(Settings settings, PlaylistDAO playlistDAO){
        this.settings=settings;
        this.playlistDAO=playlistDAO;

        subjectTracks = PublishSubject.create();
        subjectPLaylistId = PublishSubject.create();
       /* settings.subscribePlaylistId()
                .flatMap(
                    playlistId->{
                        this.playlistId=playlistId;
                        tracks=new ArrayList();
                        subjectPLaylistId.onNext(playlistId);
                       return playlistDAO.subscribeTracksFromPlaylist(playlistId);
                }).subscribe(
                data->{
                                for(int i=0;i<data.size();i++){
                                    tracks.add(data.get(i));
                                }
                                subjectTracks.onNext(data);
                            },
                            e->{},//error
                            ()->{},//complete
                            c->{
                                Log.d("TAG", "onSubscribe");
                            }
        );*/
        ///////////////////////////
       /* playlistDAO.subscribeTracksFromPlaylist(1)
                .subscribe(
                        data->{
                            for(int i=0;i<data.size();i++){
                                tracks.add(data.get(i));
                            }
                            subjectTracks.onNext(data);
                        }
                );*/
        ///////////////////////////
        Disposable TracksFromPlaylistDisposable;
        BehaviorSubject subjectTmp= BehaviorSubject .create();
      /*  TracksFromPlaylistDisposable=settings.subscribePlaylistId()
                .subscribe(
                        id-> {
                            this.playlistId = id;
                            tracks = new ArrayList();
                            playlistDAO.subscribeTracksFromPlaylist(id)
                                    .subscribe(subjectTmp);
                        }
                );

        subjectTmp.subscribe(
                d->{
                    Log.d("TAG",  "First  : " );
                    TracksFromPlaylistDisposable.dispose();
                }
        );*/
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


        //////////////////////////////////
       /* settings.subscribePlaylistId()
                .subscribe(
                id->{
                    this.playlistId=id;
                    tracks=new ArrayList();
                    playlistDAO.subscribeTracksFromPlaylist(id)
                    .subscribe(
                            data->{
                                for(int i=0;i<data.size();i++){
                                    tracks.add(data.get(i));
                                }
                                //tracks.add(data);
                                subjectTracks.onNext(data);
                            },
                            e->{},
                            ()->{},
                            s->{}
                    );
                },
                e->{},//error
                ()->{},//complete
                c->{
                    Log.d("TAG", "onSubscribe");
                }
        );*/
        ////////////////////////////
      /*  observable
              /*  .map(
                        data->{
                            return data;
                        }
                )*/
             /*   .subscribe(
                        track->{
                            ((Flowable)track).subscribe(
                                    data->{
                                        tracks.add(data);
                                       subjectTracks.onNext(data);
                                    },
                                    e->{}
                            );
                          /*  tracks.add(track);
                            subjectTracks.onNext(track);*/
                    /*    },
                        e->{}

                );*/
    }

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
                        data->{
                            return playlistDAO.insert(data);
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
        playlistDAO.removeTrack(playlistId,tracks.get(position).id)
                .subscribe(
                        ()->{},
                        o-> Log.d("TAG",  "First  : " + o)
                );
    }



}
