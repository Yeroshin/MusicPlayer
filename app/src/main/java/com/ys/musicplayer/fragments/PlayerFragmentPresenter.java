package com.ys.musicplayer.fragments;

import com.ys.musicplayer.MainContract;
import com.ys.musicplayer.MyService;
import com.ys.musicplayer.player.Player;
import com.ys.musicplayer.player.SystemPlayer;
import com.ys.musicplayer.utils.ServiceMessenger;

import java.util.ArrayList;

import javax.inject.Inject;

public class PlayerFragmentPresenter {

    ServiceMessenger serviceMessenger;
    PlayerFragment playerFragment;
    public PlayerFragmentPresenter(ServiceMessenger serviceMessenger){
        this.serviceMessenger=serviceMessenger;

    }
    private String timeConvert(String time){
        int duration=Integer.valueOf(time);
        Integer duration_m = (duration / 60000);
        Integer duration_s = (duration / 1000) % 60;
        return String.format("%1$02d:%2$02d", duration_m, duration_s);
    }
    public void setView(PlayerFragment playerFragment){
        this.playerFragment=playerFragment;
        serviceMessenger.subscribePlayerTrackInfo()
                .subscribe(
                        hashMap -> {
                            playerFragment.setTrack_title(hashMap.get(MyService.TITLE));
                        }
                );
        serviceMessenger.subscribePlayerStateInfo()
                .subscribe(
                    hashMap -> {
                        if(hashMap.get(MyService.STATE)!=null&&hashMap.get(MyService.STATE).equals(MyService.PLAYING_STATE)){
                            playerFragment.setPlayButton(true);
                        }else{
                            playerFragment.setPlayButton(false);
                        }
                    }
                );
        serviceMessenger.subscribePlayerTimeInfo()
                .subscribe(
                        hashMap->{

                            playerFragment.setDuration_counter(timeConvert(hashMap.get(MyService.CURRENT_TIME)));
                            playerFragment.setDuration_info(timeConvert(hashMap.get(MyService.DURATION)));
                            int dur=Integer.valueOf(hashMap.get(MyService.DURATION));
                            int cur=Integer.valueOf(hashMap.get(MyService.CURRENT_TIME));
                            int seek=cur*100/dur;
                            playerFragment.setSeekBar(seek);
                        }
                );
    }


    public void onClickPlay() {
        serviceMessenger.play();
    }
}
