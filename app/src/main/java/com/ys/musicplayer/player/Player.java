package com.ys.musicplayer.player;

import com.ys.musicplayer.utils.UIMessenger;

import java.util.Map;

public class Player {
    private State state;
    private UIMessenger uiMessenger;

    public Player(){

    }
    public void setUiMessenger(UIMessenger uiMessenger){
        this.uiMessenger=uiMessenger;
    }

    public void changeState(State state) {
        this.state = state;
    }
    //////////////////////////
    public void sendMessage(String action, Map<String,String> extraHashMap){
        uiMessenger.sendMessage(action,extraHashMap);
    }
    //////////////////////////
    public void onPlay(){
        state.onPlay();
    }
    public void onNext(){
        state.onNext();
    }
    public void onPrevious(){
        state.onPrevious();
    }

}
