package com.ys.musicplayer.presenters;

import com.ys.musicplayer.adapters.ItemTouchHelperCallback;
import com.ys.musicplayer.adapters.UniversalAdapter;

import com.ys.musicplayer.fragments.ITrackFragment;
import com.ys.musicplayer.fragments.TrackFragment;
import com.ys.musicplayer.media.IMediaItem;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.utils.PlayBackMode;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TrackFragmentPresenter implements ItemTouchHelperCallback.Callback {


    private ITrackFragment listView;
    private TrackManager trackManager;
    private PlayBackMode playBackMode;
    private ArrayList<IMediaItem> items;
    private ArrayList<Boolean> selectedItems;
    private ArrayList<Boolean> activatedItems;
    public TrackFragmentPresenter(ITrackFragment listView,PlayBackMode playBackMode, TrackManager trackManager){
        this.listView=listView;
        this.playBackMode=playBackMode;
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
      /*  BehaviorSubject<String> subject=BehaviorSubject.create();
        subject
               // .replay(1)
              //  .autoConnect()
                .subscribe(
                s->{
                    Log.d("TAG","subscriber1:"+ s);
                }
        );
        subject.onNext("a");
        subject.onNext("b");
        subject.onNext("c");
        subject.subscribe(
                s->{
                    Log.d("TAG","subscriber2:"+ s);
                }
        );
        subject.onNext("d");
        subject.onNext("e");
        subject.onNext("f");*/
        ///////////////////////////
//ActivateThis!!!!!!!!!!!!!
       trackManager.subscribeTracks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .flatMap(
                        tracks->{
                            selectedItems=new ArrayList();
                            for (int i=0;i<items.size();i++){
                                selectedItems.add(false);
                            }
                            activatedItems=new ArrayList();
                            for (int i=0;i<items.size();i++){
                                activatedItems.add(false);
                            }

                            this.items=items;
                            listView.setItems(items,selectedItems,activatedItems);
                            return trackManager.subscribeCurrentTrack();
                        }
                )
                .subscribe(
                        currentTrack->{
                            for(int i=0;i<activatedItems.size();i++){
                               if(i!=currentTrack){
                                   activatedItems.set(i,false);
                               }else {
                                   activatedItems.set(i,true);
                               }
                            }
                            listView.updateActivated();
                        },
                        e->{},
                        ()->{},
                        s->{}
                );
      /* trackAdapter.subscribeSelectedItem()
                .subscribe(
                        selectedItem->{
                            trackManager.setSelectedTrack(selectedItem);
                        },
                        e->{},
                        ()->{},
                        s->{}
                );*/
        trackManager.subscribeSelectedTrack()
                .subscribe(
                        selectedItem->{
                            for(int i=0;i<selectedItems.size();i++){
                                if(i!=selectedItem){
                                    selectedItems.set(i,false);
                                }else {
                                    selectedItems.set(i,true);
                                }
                            }
                            listView.updateSelected();
                        },
                        e->{},
                        ()->{},
                        s->{}
                );
        playBackMode.subscribeModeDrawable()
                .subscribe(
                        mode->{
                        //    listView.setModeButton(mode);
                        }
                );
        ///////////////////////////
    /*    trackManager.subscribeTracks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .flatMap(
                        tracks->{
                             trackAdapter.setItems(tracks);
                            return trackManager.subscribeCurrentTrack();
                        }
                )
                .flatMap(
                        currentTrack->{
                            trackAdapter.setActivated(currentTrack);
                            return trackAdapter.subscribeSelectedItem();
                        }
                )
                .flatMap(
                        selectedTrack->{
                            trackManager.setSelectedTrack(selectedTrack);
                           return trackManager.subscribeSelectedTrack();
                        }
                )
                .subscribe(
                        selectedItem->{
                            Log.d("TAG", "onNext value : ");
                           // trackManager.setSelectedTrack(selectedItem);
                            trackAdapter.setSelected(selectedItem);
                        },
                        e->{},
                        ()->{},
                        s->{}
                );*/
        ///////////////////////////
      /*  trackManager.subscribeTracks()
                .subscribeOn(Schedulers.io())
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
        trackManager.subscribeCurrentTrack()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        currentTrack->{
                           // trackAdapter.setActivated(currentTrack);
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

                );*/
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

    public void onClickMode(){
        playBackMode.changeMode();
    }



    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}
