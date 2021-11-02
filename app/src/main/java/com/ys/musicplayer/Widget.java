package com.ys.musicplayer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ys.musicplayer.di.App;

import javax.inject.Inject;

public class Widget extends AppWidgetProvider implements MainContract.MainView{
    private static final int actionPlay=1;
    @Inject
    MainContract.MainPresenter mainPresenter;

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);//pizdec vagnaya hyinya
        this.context=context;
        App.get(context).getInjector().inject(this);
        mainPresenter.onAttachView(this);
        switch (intent.getIntExtra("action",0)){
            case actionPlay:
                mainPresenter.onClickPlay();
                break;
            default:
                break;
        }

    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.context=context;
        setArtist("");
    }

    @Override
    public void onEnabled(Context context) {
        //Toast.makeText(context, "widgetON", Toast.LENGTH_SHORT).show();
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

    @Override
    public void setArtist(String text){
        ComponentName thiswidget = new ComponentName(context, Widget.class);
        AppWidgetManager appmanager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        remoteViews.setTextViewText(R.id.textView, text);

        ///////////////////////
        Intent playActionIntent = new Intent(context, Widget.class);
        playActionIntent.putExtra("action", actionPlay);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(context, 1201, playActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.play_button,playPendingIntent);
        ////////////////////////
        remoteViews.setImageViewResource(R.id.play_button, R.drawable.play);
        ////////////////////////
        appmanager.updateAppWidget(thiswidget, remoteViews);
    }
}
