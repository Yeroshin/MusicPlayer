package com.ys.musicplayer.dialogs;

import com.ys.musicplayer.adapters.IMediaAdapter;


public interface IMediaDialog extends IMediaAdapter {
    void setLoading(boolean loading);
}
