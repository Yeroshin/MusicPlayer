package com.ys.musicplayer.utils;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.ys.musicplayer.MyService;
import com.ys.musicplayer.Widget;
import com.ys.musicplayer.player.Player;
import com.ys.musicplayer.player.PlayerStateFactory;
import com.ys.musicplayer.player.State;
import com.ys.musicplayer.player.SystemPlayer;

import java.util.HashMap;
import java.util.Map;

public class UIMessenger {
    Player player;
    Context context;
    public UIMessenger(Context context,Player player, PlayerStateFactory.Factory playerStateFactory) {
        this.context=context;
        this.player = player;
        player.setUiMessenger(this);
        player.changeState(playerStateFactory.getIdleState());
    }

    public void sendMessage(String action, Map<String,String> extraHashMap){
       // Intent intent = new Intent(context,Widget.class);
        Intent intent = new Intent();
       /* intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, title);
        intent.setType("text/plain");*/
        intent.setPackage(context.getPackageName());
        intent.setAction(action);
       // intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
       // intent.addCategory("android.intent.category.DEFAULT");
        for(Map.Entry<String,String> entry:extraHashMap.entrySet()){
            intent.putExtra(entry.getKey(), entry.getValue());
        }

        context.sendBroadcast(intent);
    }


    public void  handleMessage(Message msg){
        switch (((Intent)msg.obj).getStringExtra(MyService.EVENT)){
            case MyService.ON_PLAY_CLICK:
                player.onPlay();
                break;
        }
    }
}
