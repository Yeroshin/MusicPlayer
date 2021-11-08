package com.ys.musicplayer.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.ys.musicplayer.adapters.ItemTouchHelperAdapter;


@Entity
public class PlayList implements ItemTouchHelperAdapter.RecyclerListAdapter.RecyclerListArrayItem {
    @PrimaryKey
    public int id;
    public String name;
}
