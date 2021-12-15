package com.ys.musicplayer.dialogs;

import android.util.Log;

import com.ys.musicplayer.Settings;
import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.PlaylistDAO;
import com.ys.musicplayer.media.IPlayListDialogPresenter;
import com.ys.musicplayer.media.PlayListFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PlayListDialogPresenter implements IPlayListDialogPresenter,UniversalAdapter.ItemTouchCallBack{

    PlaylistDAO playlistDAO;
    PlayListFactory.Factory playListFactory;
    UniversalAdapter adapter;
    Settings settings;
    public PlayListDialogPresenter(PlaylistDAO playlistDAO, PlayListFactory.Factory playListFactory, Settings settings) {
        this.playlistDAO=playlistDAO;
        this.playListFactory=playListFactory;
        this.settings=settings;
    }

    @Override
    public void init(UniversalAdapter adapter) {
        this.adapter=adapter;
        this.adapter.setItemTouchCallBack(this);
        playlistDAO.subscribePlaylists()
                //.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    emittedData ->{
                        this.adapter.setItems((ArrayList) emittedData);
                        Log.d("getPlaylist", "ok");
                    },
                    throwable -> throwable.printStackTrace(),
                     () -> Log.d("getPlaylist", "complete")

                );
    }
    @Override
    public void onPlaylistAddButton(String name){
        PlayList playList=playListFactory.createPlayList();
        // playList.id=1;
        playList.name=name;
        playlistDAO.insert(playList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->Log.d("insertPlayList", "ok"),
                        e->Log.d("insertPlayList", "error"+e)
                );
    }
    public void onAccept(){
        for(int i=0;i<adapter.selectedItems.size();i++){
            if((boolean)adapter.selectedItems.get(i)){
                settings.setPlaylistId(((PlayList)adapter.items.get(i)).id);
            }
        }
    }

    @Override
    public void onItemDismiss(int position) {
        int id=((PlayList)adapter.items.get(position)).id;
        playlistDAO.deletePlaylistAndItsTracks(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->{
                            Log.d("deletePlayList", "ok");
                            adapter.removeItem(position);
                        },
                        e->Log.d("deletePlayList", "error"+e)
                );

        int a=0;
    }
}
