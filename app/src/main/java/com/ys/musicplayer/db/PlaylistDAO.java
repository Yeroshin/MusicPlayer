package com.ys.musicplayer.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


@Dao
public interface PlaylistDAO {
    @Query("SELECT * FROM PlayList")
    List<PlayList> getPlaylists();

    @Query("SELECT * FROM PlaylistItem WHERE playlist=:id")
    List<PlaylistItem> getSongsFromPlaylist(int id);

    @Query("SELECT * FROM PlaylistItem ")
    List<PlaylistItem> getAll();

  /*  @Query("SELECT * FROM PlaylistItem WHERE id = :id")
    PlaylistItem getById(long id);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(PlaylistItem playlistItem);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(PlayList playList);

    @Update
    void update(PlaylistItem playlistItem);

    @Delete
    void delete(PlaylistItem playlistItem);
}
