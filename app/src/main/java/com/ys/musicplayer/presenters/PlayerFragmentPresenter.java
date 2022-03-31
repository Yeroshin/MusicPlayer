package com.ys.musicplayer.presenters;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.db.PlaylistDAO;
import com.ys.musicplayer.fragments.PlayerFragment;
import com.ys.musicplayer.models.Settings;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlayerFragmentPresenter {

    private PlayerFragmentPresenterProxy playerFragmentPresenterProxy;
    private PlayerFragment playerFragment;
    private HashMap hashMap;
    private Settings settings;
    private PlaylistDAO playlistDAO;
    public PlayerFragmentPresenter(PlayerFragmentPresenterProxy playerFragmentPresenterProxy, Settings settings, PlaylistDAO playlistDAO){
        this.playerFragmentPresenterProxy = playerFragmentPresenterProxy;
        this.settings=settings;
        this.playlistDAO=playlistDAO;

    }
    private String timeConvert(String time){
        int duration=Integer.valueOf(time);
        Integer duration_m = (duration / 60000);
        Integer duration_s = (duration / 1000) % 60;
        return String.format("%1$02d:%2$02d", duration_m, duration_s);
    }
    public void setView(PlayerFragment playerFragment){
        this.playerFragment=playerFragment;
        playerFragmentPresenterProxy.subscribePlayerTrackInfo()
                .subscribe(
                        hashMap -> {
                            playerFragment.setTrack_title(hashMap.get(MyService.TITLE));
                        }
                );
        playerFragmentPresenterProxy.subscribePlayerStateInfo()
                .subscribe(
                    hashMap -> {
                        if(hashMap.get(MyService.STATE).equals(MyService.PLAYING_STATE)) {
                            playerFragment.setPlayButton(true);
                        }
                        else if(hashMap.get(MyService.STATE).equals(MyService.BUFFERING_STATE)){
                            playerFragment.setPlayButton(true);
                            playerFragment.setTrackTitleBuffering();
                        }else{
                            playerFragment.setPlayButton(false);
                        }
                    }
                );
        playerFragmentPresenterProxy.subscribePlayerTimeInfo()
                .subscribe(
                        hashMap->{

                            playerFragment.setDuration_counter(timeConvert(hashMap.get(MyService.CURRENT_TIME)));
                            playerFragment.setDuration_info(timeConvert(hashMap.get(MyService.DURATION)));
                            int duration=Integer.valueOf(hashMap.get(MyService.DURATION));
                            int current=Integer.valueOf(hashMap.get(MyService.CURRENT_TIME));

                            playerFragment.setSeekBar(current,duration);
                        }
                );
        settings.subscribePlaylistId()
                .subscribeOn(Schedulers.io())

                .flatMap(
                        id->{
                            return playlistDAO.getPlayListById(id);
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        playlist->{
                            playerFragment.setPlaylist(playlist.getName());
                        }

                );
      /*  playerFragmentPresenterProxy.subscribeAudioSessionId()
                .subscribe(
                        hashMap->{
                            playerFragment.setVisualizer(Integer.valueOf(hashMap.get(MyService.AUDIO_SESSION_ID)));
                        }
                );*/
    }

    public void onProgressChanged(int progress){
        hashMap=new HashMap();
        hashMap.put(MyService.EVENT,MyService.ON_PROGRESS_CHANGED);
        hashMap.put(MyService.DURATION,String.valueOf(progress));
        playerFragmentPresenterProxy.sendMessage(hashMap);
    }
    public void onClickRew() {
        hashMap=new HashMap();
        hashMap.put(MyService.EVENT,MyService.ON_REW_CLICK);
        playerFragmentPresenterProxy.sendMessage(hashMap);
    }
    public void onClickPlay() {
        hashMap=new HashMap();
        hashMap.put(MyService.EVENT,MyService.ON_PLAY_CLICK);
        playerFragmentPresenterProxy.sendMessage(hashMap);
    }
    public void onClickFwd() {
        hashMap=new HashMap();
        hashMap.put(MyService.EVENT,MyService.ON_FWD_CLICK);
        playerFragmentPresenterProxy.sendMessage(hashMap);
    }
}
