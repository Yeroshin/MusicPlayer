package com.ys.musicplayer.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ys.musicplayer.adapters.UniversalAdapter;


@Entity
public class PlayList  {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
}
