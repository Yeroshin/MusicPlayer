package com.ys.musicplayer.dialogs;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.PlaylistDAO;

import java.util.ArrayList;

public class TrackDialogPresenter {
    UniversalAdapter adapter;
    PlaylistDAO playlistDAO;
    int currentPlaylist;
    public TrackDialogPresenter(UniversalAdapter adapter, PlaylistDAO playlistDAO) {
        this.adapter = adapter;
        this.playlistDAO=playlistDAO;
    }

    public void init(UniversalAdapter adapter){

    }
    public void onAccept(){
        ArrayList selectedItems=new ArrayList();
        for(int i=0;i<adapter.selectedItems.size();i++){
            if((boolean)adapter.selectedItems.get(i)){

            }
        }
    }
}
