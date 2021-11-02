package com.ys.musicplayer.db;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class PlaylistItem  {
        @PrimaryKey
        public int id;
        public int playlist;
        public String artist;
     /*   public int position=0;
        boolean stream;
        public String title;

        public String path;
        public String duration;
        public String duration_sec;
        public String info;*/

       // public Uri uri;
}
