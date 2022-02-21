package com.ys.musicplayer.media;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.ys.musicplayer.db.Track;

import java.util.ArrayList;

import javax.inject.Inject;

public class MediaModel {
    Context context;
    PlayListFactory.Factory playListFactory;

    public MediaModel(Context context,PlayListFactory.Factory playListFactory) {
        this.context=context;
        this.playListFactory=playListFactory;
    }
  /*  public ArrayList getData(String query){
        ArrayList itemsList=new ArrayList();
        String[] mediaProjection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.MediaColumns.DISPLAY_NAME,
        };

        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(collection,  mediaProjection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String artist=cursor.getString( cursor.getColumnIndex(MediaStore.MediaColumns.ARTIST));
                if (artist != null) {
                    itemsList.add(artist);
                }
            } while (cursor.moveToNext());
        }
        return itemsList;
    }*/
    public ArrayList query(String select,String where,String what){
        ArrayList itemsList=new ArrayList();
        String[] mediaProjection = new String[]{select};
        String selection;
        if(where!=null){
            selection=where+"='"+what+"'";
        }else{
            selection=null;
        }

//"Pursuit"
        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(collection,  mediaProjection, selection, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tmp=cursor.getString( cursor.getColumnIndex(select));
                if (tmp!= null) {
                    itemsList.add(tmp);
                }
            } while (cursor.moveToNext());
        }
        return itemsList;
    }
    public ArrayList queryTracksUri(String where,String what){
        ArrayList itemsList=new ArrayList();
        String[] mediaProjection = new String[]{
                MediaStore.Audio.Media._ID,
        };
        String selection;
        if(where!=null){
            selection=where+"='"+what+"'";
        }else{
            selection=null;
        }

//"Pursuit"
        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(collection,  mediaProjection, selection, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursor.getLong(idColumn));
                String tmp=cursor.getString( cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                if (tmp!= null) {
                    itemsList.add(uri);
                }
            } while (cursor.moveToNext());
        }
        return itemsList;
    }
    public ArrayList getTracks(String where,String what){
        ArrayList tracks=queryTracksUri(where,what);
        for(int i=0;i<tracks.size();i++){
            Track track=playListFactory.createTrack();
            track.setUri((Uri)tracks.get(i));
            retrieveMetadata(track);
            tracks.set(i,track);
        }
        return tracks;
    }
    private void retrieveMetadata(Track track) {

        String displayName="";
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();


        retriever.setDataSource(context, Uri.parse(track.getUri()));




        String artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);//!
        String title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);//!
        if (title == null & artist != null) {
            track.setTitle(displayName);
            track.setArtist(artist);
        } else if(artist == null & title != null) {
            track.setTitle(title);
            track.setArtist("");
        }else if(artist == null & title == null){
            track.setTitle(displayName);
            track.setArtist("");
        }else{
            track.setTitle(title);
            track.setArtist(artist);
        }
        ////////////bitrate
        Integer tmp_btr = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
        Integer tmp_btr_kps = tmp_btr / 1000;
        track.setInfo(tmp_btr_kps.toString() +" "+"kbps;");
        ////////////size
/*
        Long len_mb = fileSize / 1000000;
        info += " " + len_mb + " " + "Mb";*/
        ////////////duration
        Integer tmp_dur = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        Integer tmp_dur_m = (tmp_dur / 60000);
        Integer tmp_dur_s = (tmp_dur / 1000) % 60;
        String time = String.format("%1$02d:%2$02d", tmp_dur_m, tmp_dur_s);
        track.setDuration(tmp_dur.toString());
        track.setDuration_sec(time);
    }

}
