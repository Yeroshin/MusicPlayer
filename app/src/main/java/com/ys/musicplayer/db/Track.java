package com.ys.musicplayer.db;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


import com.ys.musicplayer.adapters.UniversalAdapter;


@Entity
public class Track {
        @PrimaryKey(autoGenerate = true)
        public int id;
        public int playlist;
        public String artist;
        public String uri;
        public String title;
        public String duration;
        public String duration_sec;
        public String info;


        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public int getPlaylist() {
                return playlist;
        }

        public void setPlaylist(int playlist) {
                this.playlist = playlist;
        }

        public String getArtist() {
                return artist;
        }

        public void setArtist(String artist) {
                this.artist = artist;
        }

        public void setUri(Uri uri) {
                this.uri = uri.toString();
        }

        public Uri getUri() {
                return  Uri.parse(uri);
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getDuration() {
                return duration;
        }

        public void setDuration(String duration) {
                this.duration = duration;
        }

        public String getDuration_sec() {
                return duration_sec;
        }

        public void setDuration_sec(String duration_sec) {
                this.duration_sec = duration_sec;
        }

/*   public int position=0;
        boolean stream;


        public String path;


        */

       // public Uri uri;
}
