package com.ys.musicplayer.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PlaylistItem.class,PlayList.class}, version = 8)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaylistDAO employeeDao();
}
