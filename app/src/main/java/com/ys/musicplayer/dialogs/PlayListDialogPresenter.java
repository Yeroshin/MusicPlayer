package com.ys.musicplayer.dialogs;

import android.util.Log;

import com.ys.musicplayer.models.Settings;
import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.PlaylistDAO;
import com.ys.musicplayer.media.IPlayListDialogPresenter;
import com.ys.musicplayer.media.PlayListFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
        this.adapter.subjectLoading.onNext(true);
        playlistDAO.subscribePlaylists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(
                        emittedData ->{
                            this.adapter.setItems((ArrayList) emittedData);
                            // Log.d("getPlaylist", "ok");
                            return settings.subscribePlaylistId();
                        }
                )
                .subscribe(
                        id->{
                            for(int i=0;i<adapter.items.size();i++){
                                if(((PlayList)adapter.items.get(i)).getId()==id){
                                    adapter.setActivated(i);
                                    return;
                                }
                            }
                        },
                        e -> {},//error
                        () ->{}//complete

                );

    }
    @Override
    public boolean onPlaylistAddButton(String name){
        for(int i=0;i<adapter.items.size();i++){
            if(name.equals(((PlayList)adapter.items.get(i)).name)){

                return true;
            }
        }
        PlayList playList=playListFactory.createPlayList();
        playList.name=name;
        playlistDAO.insertPlaylist(playList)
                .subscribeOn(Schedulers.io())
               // .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> {},//complete
                        e-> {}//error
                );
        return false;
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
    }
}
