package com.ys.musicplayer.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;


@Dao
public abstract class PlaylistDAO {
    @Query("SELECT * FROM PlayList")
    public abstract Observable<List<PlayList>> subscribePlaylists();

    @Query("SELECT * FROM Track WHERE playlist IN (:playlist)")
    public abstract Observable<List<Track>> subscribeTracksFromPlaylist(int playlist);

  /*  @Query("SELECT * FROM Track WHERE playlist=:id")
    List<Track> getSongsFromPlaylist(int id);*/

    @Query("SELECT * FROM Track ")
    abstract List<Track> getAll();

    @Query("SELECT * FROM Playlist WHERE id = :id")
    public abstract Observable<PlayList> getPlayListById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable insertTrack(List<Track> track);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable insertPlaylist(PlayList playList);

    @Update
    abstract void update(Track track);

    @Delete
    abstract void delete(Track track);

    @Query("DELETE from Track WHERE playlist IN (:playlist) AND id IN (:id)")
    public abstract Completable removeTrack(int playlist,int id);

    @Query("DELETE from Track WHERE playlist IN (:playlist)")
    abstract Completable deleteTracksByIdList(int playlist);

    @Query("DELETE from PlayList WHERE id IN (:id)")
    abstract Completable deletePlayList(int id);


    public Completable deletePlaylistAndItsTracks(int playlist) {
        return deleteTracksByIdList(playlist)
        .andThen(deletePlayList(playlist));

    }
}
