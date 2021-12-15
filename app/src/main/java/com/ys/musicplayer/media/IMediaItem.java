package com.ys.musicplayer.media;

import android.net.Uri;

import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.PlayList;

import java.util.List;

import io.reactivex.Flowable;

public interface IMediaItem {
    String getTitle();
    void onClick(UniversalAdapter adapter);
    void setBackItem(IMediaItem mediaItem);
    void setTitle(String title);
    Uri getContent();
}
