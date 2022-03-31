package com.ys.musicplayer.fragments;

import android.content.Context;
import android.content.Intent;


import com.ys.musicplayer.MyService;


import com.ys.musicplayer.di.components.DaggerPlayerComponent;
import com.ys.musicplayer.di.modules.PlayerModule;

import com.ys.musicplayer.player.Player;
import com.ys.musicplayer.presenters.PlayerPresenterProxy;

import javax.inject.Inject;


public class PlayerFragmentProxy implements IPlayerFragment,Proxy{
    public static final String PLAYER_PRESENTER="com.ys.musicplayer.PlayerPresenter";
    public static final String FUNCTION="function";

    public static final String ON_PREVIOUS="onPrevious";
    public static final String ON_PLAY="onPlay";
    public static final String ON_NEXT="onNext";
    public static final String ON_PROGRESS_CHANGED="onProgressChanged";
    public static final String PROGRESS="progress";

    private Context context;
    @Inject
    public Player player;
    private Intent intent;


    public PlayerFragmentProxy(Context context) {
        this.context = context;
        this.player=player;
        DaggerPlayerComponent.builder()
                .playerModule(new PlayerModule(context,this))
                .build()
                .inject(this);

    }
    public void  handleMessage(Intent intent){
        switch (intent.getStringExtra(MyService.EVENT)){
            case ON_PREVIOUS:
                player.onPrevious();
                break;
            case ON_PLAY:
                player.onPlay();
                break;
            case ON_NEXT:
                player.onNext();
                break;
            case ON_PROGRESS_CHANGED:
                player.onProgressChanged(intent.getIntExtra(PROGRESS,0));
                break;
        }
    }
    @Override
    public void setPlaylist(String playlist) {
        intent = new Intent();
        intent.putExtra(PlayerPresenterProxy.FUNCTION,PlayerPresenterProxy.SET_PLAYLIST);
        intent.putExtra(PlayerPresenterProxy.PLAYLIST,playlist);
        intent.setAction(PlayerPresenterProxy.PLAYER_VIEW);
        context.sendBroadcast(intent);
    }

    @Override
    public void setTrack_title(String track_title) {
        intent = new Intent();
        intent.putExtra(PlayerPresenterProxy.FUNCTION,PlayerPresenterProxy.SET_TRACK_TITLE);
        intent.putExtra(PlayerPresenterProxy.TRACK_TITLE,track_title);
        intent.setAction(PlayerPresenterProxy.PLAYER_VIEW);
        context.sendBroadcast(intent);
    }

    @Override
    public void setTrackTitleBuffering() {
        intent = new Intent();
        intent.putExtra(PlayerPresenterProxy.FUNCTION,PlayerPresenterProxy.SET_TRACK_TITLE_BUFFERING);
        intent.setAction(PlayerPresenterProxy.PLAYER_VIEW);
        context.sendBroadcast(intent);
    }

    @Override
    public void setDuration_counter(String duration_counter) {
        intent = new Intent();
        intent.putExtra(PlayerPresenterProxy.FUNCTION,PlayerPresenterProxy.SET_DURATION_COUNTER);
        intent.putExtra(PlayerPresenterProxy.DURATION_COUNTER,duration_counter);
        intent.setAction(PlayerPresenterProxy.PLAYER_VIEW);
        context.sendBroadcast(intent);
    }

    @Override
    public void setSeekBar(int progress, int max) {
        intent = new Intent();
        intent.putExtra(PlayerPresenterProxy.FUNCTION,PlayerPresenterProxy.SET_SEEKBAR);
        intent.putExtra(PlayerPresenterProxy.PROGRESS,progress);
        intent.putExtra(PlayerPresenterProxy.MAX,max);
        intent.setAction(PlayerPresenterProxy.PLAYER_VIEW);
        context.sendBroadcast(intent);
    }

    @Override
    public void setDuration_info(String duration_info) {
        intent = new Intent();
        intent.putExtra(PlayerPresenterProxy.FUNCTION,PlayerPresenterProxy.SET_DURATION_INFO);
        intent.putExtra(PlayerPresenterProxy.DURATION_INFO,duration_info);
        intent.setAction(PlayerPresenterProxy.PLAYER_VIEW);
        context.sendBroadcast(intent);
    }

    @Override
    public void setPlayButton(boolean playing) {
        intent = new Intent();
        intent.putExtra(PlayerPresenterProxy.FUNCTION,PlayerPresenterProxy.SET_PLAY_BUTTON);
        intent.putExtra(PlayerPresenterProxy.PLAYING,playing);
        intent.setAction(PlayerPresenterProxy.PLAYER_VIEW);
        context.sendBroadcast(intent);
    }

    @Override
    public void setVisualizer(int sessionId) {
        intent = new Intent();
        intent.putExtra(PlayerPresenterProxy.FUNCTION,PlayerPresenterProxy.AUDIO_SESSION_ID);
        intent.putExtra(PlayerPresenterProxy.AUDIO_SESSION_ID,sessionId);
        intent.setAction(PlayerPresenterProxy.PLAYER_VIEW);
        context.sendBroadcast(intent);
    }

}
