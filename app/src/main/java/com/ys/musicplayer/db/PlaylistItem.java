package com.ys.musicplayer.db;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.ys.musicplayer.adapters.ItemTouchHelperAdapter;


@Entity
public class PlaylistItem  implements ItemTouchHelperAdapter.RecyclerListAdapter.RecyclerListArrayItem{
        @PrimaryKey
        public int id;
        public int playlist;
        public String artist;
        public String uri;
        public String title;
        public String duration;
        public String duration_sec;
        public String info;
        public Uri getUri() {
                return  Uri.parse(uri);
        }

/*   public int position=0;
        boolean stream;


        public String path;


        */

       // public Uri uri;
}
