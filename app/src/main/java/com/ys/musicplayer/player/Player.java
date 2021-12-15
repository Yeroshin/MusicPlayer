package com.ys.musicplayer.player;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;


import com.ys.musicplayer.db.Track;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

public class Player implements SystemPlayer.SystemPlayerObserver{
    private State state;
    private SystemPlayer player;
    private boolean playing = false;
    private List<Track> playlist;
    private int currentTrack;
    private int pausedTrack;
    private PlayBackMode playBackMode;

    public void setPlaylist(List<Track> playlist) {
        this.playlist = playlist;
    }

    public void setCurrentTrack(int currentTrack) {
        this.currentTrack = currentTrack;
    }
    @Inject
    public Player(SystemPlayer player,PlayBackMode playBackMode){
        this.player = player;
        this.playBackMode=playBackMode;
        player.setObserver(this);
    }

    public void changeState(State state) {
        this.state = state;
    }
    public void startPlayback() {

        if(pausedTrack==currentTrack){
            resumePlayback();
        }else{
            player.play(playlist.get(currentTrack).getUri());
        }

    }
    public void resumePlayback(){
        player.resume();
    }
    public void pausePlayback() {
        pausedTrack=currentTrack;
        player.pause();
    }
    public void playNext(){
        currentTrack=playBackMode.getNext(currentTrack,playlist.size());
        player.play(playlist.get(currentTrack).getUri());
    }
    public void playPrevious(){
        currentTrack=playBackMode.getPrevious(currentTrack,playlist.size());
        player.play(playlist.get(currentTrack).getUri());
    }
    @Override
    public void onCompletion() {
        playNext();
    }

}
