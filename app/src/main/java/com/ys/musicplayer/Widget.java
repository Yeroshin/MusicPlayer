package com.ys.musicplayer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class Widget extends AppWidgetProvider {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);//pizdec vagnaya hyinya
        this.context=context;
        switch (intent.getAction()){
            case MyService.PLAYER_STATE_INFO:
               // updateState(intent.getStringExtra(MyService.PLAYING_STATE));
                break;
            case MyService.PLAYER_TRACK_INFO:
               // updateTitle(intent.getStringExtra(MyService.TITLE));
                break;
            default:
                break;
        }

    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.context=context;
        setView();
    }

    @Override
    public void onEnabled(Context context) {
       // Toast.makeText(context, "widgetON", Toast.LENGTH_SHORT).show();
      /*  Intent intent = new Intent(context, MyService.class);
        intent.putExtra("start_type", MyService.start_type_widget);
        intent.putExtra("action",MyService.action_widget_init);
        context.startService(intent);*/
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        //Toast.makeText(context, "widgetOFF", Toast.LENGTH_SHORT).show();
        // Enter relevant functionality for when the last widget is disabled
    }
    public void updateState(String playing){
        ComponentName thiswidget = new ComponentName(context, Widget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        if(playing.equals(MyService.PLAYING_STATE)){
            remoteViews.setImageViewResource(R.id.play_button, R.drawable.pause);
        }else{
            remoteViews.setImageViewResource(R.id.play_button, R.drawable.play);
        }
        appWidgetManager.partiallyUpdateAppWidget(appWidgetManager.getAppWidgetIds(thiswidget),remoteViews);
    }
    public void updateTitle(String title){
        ComponentName thiswidget = new ComponentName(context, Widget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        remoteViews.setTextViewText(R.id.widget_title, title);
        appWidgetManager.partiallyUpdateAppWidget(appWidgetManager.getAppWidgetIds(thiswidget),remoteViews);
    }
    public void setView(){
        ComponentName thiswidget = new ComponentName(context, Widget.class);
        AppWidgetManager appmanager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        ///////////////////////ACTIONS
        Intent playActionIntent = new Intent(context, MyService.class);
        playActionIntent.putExtra(MyService.EVENT, MyService.ON_PLAY_CLICK);
        PendingIntent playPendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            playPendingIntent = PendingIntent.getForegroundService(context, 1201, playActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            playPendingIntent = PendingIntent.getService(context, 1201, playActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        remoteViews.setOnClickPendingIntent(R.id.play_button,playPendingIntent);
        ////////////////////////VIEWS
        remoteViews.setImageViewResource(R.id.play_button, R.drawable.play);
        ////////////////////////
        appmanager.updateAppWidget(thiswidget, remoteViews);
    }
}
