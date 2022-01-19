package com.ys.musicplayer.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Track.class,PlayList.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaylistDAO playlistDao();
}
