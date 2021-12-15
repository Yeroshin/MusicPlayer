package com.ys.musicplayer.fragments;

import android.util.Log;

import com.ys.musicplayer.Settings;
import com.ys.musicplayer.models.PlaylistManager;

import java.util.Random;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class PlaylistFragmentPresenter {
    PlaylistFragment playlistFragment;
    Settings settings;
    public int tmp=0;
    public PlaylistFragmentPresenter(Settings settings, PlaylistManager playlistManager){
        this.playlistFragment=playlistFragment;
        this.settings=settings;


        settings.subscribePlaylistId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        o->    Log.d("TAG",  "First  : " + o)
                );
        playlistManager.subscribeTracks()
                .subscribe(
                        o->    Log.d("TAG",  "First  : " + o)
                );
        int a=0;
    }


    public void onResume(){
        Random random=new Random();
        int number=random.nextInt(10);
        settings.setPlaylistId(number);
        int a=5;
    }
}
