package com.ys.musicplayer.fragments;

import android.util.Log;

import com.ys.musicplayer.models.Settings;
import com.ys.musicplayer.adapters.TrackAdapter;
import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.models.TrackManager;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TrackFragmentPresenter implements UniversalAdapter.ItemTouchCallBack{

    private Settings settings;
    private TrackAdapter trackAdapter;
    private TrackManager trackManager;
    public TrackFragmentPresenter(TrackAdapter trackAdapter, Settings settings, TrackManager trackManager){
        this.settings=settings;
        this.trackAdapter = trackAdapter;
        this.trackManager = trackManager;
        ///////////////////////////
      /*  trackManager.subscribePlaylistId()
                .flatMap(
                        id->{
                            trackAdapter.clearItems();
                            return trackManager.subscribeTracks();
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tracks->{
                            trackAdapter.addItems(tracks);
                            Log.d("TAG", "onNext value : ");
                        },
                        e->{},
                        ()->{},
                        s->{}
                );*/
        ///////////////////////////////last
       /* trackManager.subscribePlaylistId()
                .subscribe(
                        id->{
                            trackAdapter.clearItems();
                            Log.d("TAG", "onNext value : ");
                        },
                        e->{},
                        ()->{},
                        s->{}
                );
        trackManager.subscribeTracks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tracks->{
                            trackAdapter.addItems(tracks);
                            Log.d("TAG", "onNext value : ");
                        },
                        e->{},
                        ()->{},
                        s->{}
                );*/
        ///////////////////////////
        trackManager.subscribeTracks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tracks->{
                            trackAdapter.setItems(tracks);
                            Log.d("TAG", "onNext value : ");
                        },
                        e->{},
                        ()->{},
                        s->{}
                );
        trackAdapter.subscribeSelectedItem()
                .subscribe(
                        position->{
                            trackManager.setSelectedTrack(position);
                        }

                );
        ///////////////////////////
       /* settings.subscribePlaylistId()
               .flatMap(
                       PlaylistId-> {
                           trackAdapter.clearItems();
                   return trackManager.subscribeTracks();
               })
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        o->{
                            trackAdapter.addItems(o);
                            Log.d("TAG", "onNext value : ");
                        },
                        e->{
                            Log.d("TAG", "onError : ");
                        },
                        ()->{
                            Log.d("TAG", " onComplete");
                        },
                        c->{
                            Log.d("TAG", "onSubscribe");
                        }
                        /////////////////////////////////////
                       /* new Observer() {

                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.d("TAG", " First onSubscribe : " + d.isDisposed());
                            }



                            @Override
                            public void onNext(@NonNull Object o) {
                                trackAdapter.addItems((ArrayList) o);
                                Log.d("TAG", " First onNext value : ");
                            }

                            @Override
                            public void onError(Throwable e) {

                                Log.d("TAG", " First onError : " + e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                                Log.d("TAG", " First onComplete");
                            }
                        }*/
              //  );*/
       // Log.d("TAG",  "First  : " + o)
    }


    public void onResume(){
       /* Random random=new Random();
        int number=random.nextInt(10);
        settings.setPlaylistId(number);
        int a=5;*/
    }

    @Override
    public void onItemDismiss(int position) {
        trackManager.removeTrack(position);
    }
}
