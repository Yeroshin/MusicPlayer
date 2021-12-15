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
public abstract class PlaylistDAO {
    @Query("SELECT * FROM PlayList")
    public abstract Flowable <List<PlayList>> subscribePlaylists();

    @Query("SELECT * FROM Track WHERE playlist IN (:playlist)")
    public abstract Flowable <List<PlayList>> subscribeTracksFromPlaylist(int playlist);

  /*  @Query("SELECT * FROM Track WHERE playlist=:id")
    List<Track> getSongsFromPlaylist(int id);*/

    @Query("SELECT * FROM Track ")
    abstract List<Track> getAll();

  /*  @Query("SELECT * FROM PlaylistItem WHERE id = :id")
    PlaylistItem getById(long id);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable insert(Track track);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable insert(PlayList playList);

    @Update
    abstract void update(Track track);

    @Delete
    abstract void delete(Track track);

    @Query("DELETE from Track WHERE playlist IN (:playlist)")
    abstract Completable deleteTracksByIdList(int playlist);

    @Query("DELETE from PlayList WHERE id IN (:id)")
    abstract Completable deletePlayList(int id);


    public Completable deletePlaylistAndItsTracks(int playlist) {
        return deleteTracksByIdList(playlist)
        .andThen(deletePlayList(playlist));

    }
}
