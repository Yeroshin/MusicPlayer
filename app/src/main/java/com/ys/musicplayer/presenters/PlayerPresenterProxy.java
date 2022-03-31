package com.ys.musicplayer.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.fragments.IPlayerFragment;
import com.ys.musicplayer.fragments.PlayerFragmentProxy;
import com.ys.musicplayer.player.IPlayerPresenter;

public class PlayerPresenterProxy extends BroadcastReceiver implements IPlayerPresenter {
    public static final String PLAYER_VIEW="com.ys.musicplayer.PlayerView";
    public static final String FUNCTION="function";

    public static final String SET_VISUALIZER="setVisualizer";
    public static final String AUDIO_SESSION_ID="audioSessionId";
    public static final String SET_PLAY_BUTTON="setPlayButton";
    public static final String PLAYING="playing";
    public static final String SET_PLAYLIST="setPlaylist";
    public static final String PLAYLIST="playlist";
    public static final String SET_TRACK_TITLE="setTrackTitle";
    public static final String TRACK_TITLE="trackTitle";
    public static final String SET_TRACK_TITLE_BUFFERING="setTrackTitleBuffering";
    public static final String SET_DURATION_COUNTER="setDurationCounter";
    public static final String DURATION_COUNTER="durationCounter";
    public static final String SET_SEEKBAR="setSeekBar";
    public static final String PROGRESS="progress";
    public static final String MAX="max";
    public static final String SET_DURATION_INFO="setDuration_info";
    public static final String DURATION_INFO="duration_info";

    //////////////
    private Intent intent;
    private Context context;
    private IPlayerFragment playerFragment;
    public PlayerPresenterProxy(Context context,IPlayerFragment playerFragment){
        this.context=context;
        this.playerFragment=playerFragment;
        ////////////////////////////
    }

    @Override
    public void onPlay() {
        intent = new Intent(context, MyService.class);
        intent.putExtra(PlayerFragmentProxy.FUNCTION, PlayerFragmentProxy.ON_PLAY);
        intent.setAction(PlayerFragmentProxy.PLAYER_PRESENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @Override
    public void onNext() {
        intent = new Intent(context, MyService.class);
        intent.putExtra(PlayerFragmentProxy.FUNCTION, PlayerFragmentProxy.ON_NEXT);
        intent.setAction(PlayerFragmentProxy.PLAYER_PRESENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @Override
    public void onPrevious() {
        intent = new Intent(context, MyService.class);
        intent.putExtra(PlayerFragmentProxy.FUNCTION, PlayerFragmentProxy.ON_PREVIOUS);
        intent.setAction(PlayerFragmentProxy.PLAYER_PRESENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }
    @Override
    public void onProgressChanged(int progress){
        intent = new Intent(context, MyService.class);
        intent.putExtra(PlayerFragmentProxy.FUNCTION,PlayerFragmentProxy.ON_PROGRESS_CHANGED);
        intent.putExtra(PlayerFragmentProxy.PROGRESS,progress);
        intent.setAction(PlayerFragmentProxy.PLAYER_PRESENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        }else{
            context.startService(intent);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getStringExtra(FUNCTION)){
            case SET_VISUALIZER:
                playerFragment.setVisualizer(intent.getIntExtra(AUDIO_SESSION_ID,0));
                break;
            case SET_PLAY_BUTTON:
                playerFragment.setPlayButton(intent.getBooleanExtra(PLAYING,false));
                break;
                /////////
            case SET_PLAYLIST:
                playerFragment.setPlaylist(intent.getStringExtra(PLAYLIST));
                break;
            case SET_TRACK_TITLE:
                playerFragment.setTrack_title(intent.getStringExtra(TRACK_TITLE));
                break;
            case SET_TRACK_TITLE_BUFFERING:
                playerFragment.setTrackTitleBuffering();
                break;
            case SET_DURATION_COUNTER:
                playerFragment.setDuration_counter(intent.getStringExtra(DURATION_COUNTER));
                break;
            case SET_SEEKBAR:
                playerFragment.setSeekBar(intent.getIntExtra(PROGRESS,0),intent.getIntExtra(MAX,0));
                break;
            case SET_DURATION_INFO:
                playerFragment.setDuration_info(intent.getStringExtra(DURATION_INFO));
                break;
            default:
                break;
        }
    }
}
