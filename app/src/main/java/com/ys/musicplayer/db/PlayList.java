package com.ys.musicplayer.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ys.musicplayer.adapters.UniversalAdapter;


@Entity
public class PlayList implements UniversalAdapter.RecyclerListArrayItem {
    @PrimaryKey
    public int id;
    public String name;
}
