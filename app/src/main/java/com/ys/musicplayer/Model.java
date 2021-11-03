package com.ys.musicplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.ys.musicplayer.db.AppDatabase;
import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.PlaylistDAO;
import com.ys.musicplayer.db.PlaylistItem;
import com.ys.musicplayer.di.App;
import com.ys.musicplayer.player.Player;

import java.util.List;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class Model implements MainContract.Model, ServiceConnection{

    Context context;

    ClientService clientService;
    Player player;
    @Inject
    public Model(Context context,Player player){
        this.player=player;
        Intent serviceIntent = new Intent(context, MyService.class);
        context.startService(serviceIntent);
        context.bindService(serviceIntent, this, 0);

        ////////////////////////

        AppDatabase db = App.get(context).getDatabase();
        PlaylistDAO employeeDao = db.employeeDao();

        PlayList playList1=new PlayList();
        playList1.id=1;
        playList1.name="1";

        PlayList playList2=new PlayList();
        playList2.id=2;
        playList2.name="2";


        PlaylistItem playlistItem1=new PlaylistItem();
        playlistItem1.id=1;
        playlistItem1.artist="Hello world";
        playlistItem1.playlist=2;
        PlaylistItem playlistItem2=new PlaylistItem();
        playlistItem1.id=2;
        playlistItem2.artist="Again Hello world";
        playlistItem2.playlist=2;


        employeeDao.insert(playList1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{int a=1;},throwable -> Log.e(TAG, "Unable to update username", throwable));
        employeeDao.insert(playList2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{int a=1;},throwable -> Log.e(TAG, "Unable to update username", throwable));
        employeeDao.insert(playlistItem1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{int a=1;},throwable -> Log.e(TAG, "Unable to update username", throwable));
        employeeDao.insert(playlistItem2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{int a=1;},throwable -> Log.e(TAG, "Unable to update username", throwable));
        /*employeeDao.insert(playList2);
        employeeDao.insert(playlistItem1);
        employeeDao.insert(playlistItem2);*/
       // List<PlaylistItem> playListsitems = employeeDao.getAll();
        //List<PlaylistItem> playLists = employeeDao.getSongsFromPlaylist(2);

      /*  Observable<PlaylistItem> playlistItem=Observable
                .fromIterable(employeeDao.getSongsFromPlaylist(2));*/

       /* employeeDao.getSongsFromPlaylist(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PlaylistItem>>() {
                    @Override
                    public void accept(@NonNull List<PlaylistItem> playlistWithItems) throws Exception {

                    }
                });*/
    }


    @Override
    public String getPlaylist() {
        player.startPlayback();
        if(clientService!=null){
            return clientService.getString();
        }
        return "-";
    }

    @Override
    public void onPlayListLoaded() {

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        clientService = ((MyService.LocalBinder) binder).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        clientService=null;
    }
}
