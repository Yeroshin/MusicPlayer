package com.ys.musicplayer;

import android.app.Notification;
import android.app.Service;
import android.widget.RemoteViews;

public interface INotification {
    Notification getNotification(Service context, RemoteViews remoteView);
    void updateNotification(RemoteViews remoteView);
}
