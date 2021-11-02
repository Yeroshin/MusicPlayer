package com.ys.musicplayer;

import android.content.Context;
import android.widget.RemoteViews;

public interface INotificationView {
    void init(Context context);
    RemoteViews getRemoteView(boolean playBtnStatus);
}
