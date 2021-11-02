package com.ys.musicplayer.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity
public class PlayList {
    @PrimaryKey
    public int id;
    public String name;
}
