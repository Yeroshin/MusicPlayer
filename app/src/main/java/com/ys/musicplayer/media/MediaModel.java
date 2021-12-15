package com.ys.musicplayer.media;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.util.ArrayList;

import javax.inject.Inject;

public class MediaModel {
    Context context;
    public MediaModel(Context context) {
        this.context=context;
    }
    public ArrayList getData(String query){
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
    }
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

}
