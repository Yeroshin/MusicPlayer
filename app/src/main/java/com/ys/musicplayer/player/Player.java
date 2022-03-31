package com.ys.musicplayer.player;

import com.ys.musicplayer.db.PlaylistDAO;
import com.ys.musicplayer.fragments.IPlayerFragment;
import com.ys.musicplayer.models.Settings;
import com.ys.musicplayer.models.SystemPlayer;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.utils.PlayBackMode;
import com.ys.musicplayer.utils.ServiceController;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Player implements IPlayerPresenter{


    private State state;
    public IPlayerFragment playerFragment;
    public TrackManager trackManager;
    public SystemPlayer systemPlayer;
    public PlayerStateFactory.Factory playerStateFactory;
    public PlayBackMode playBackMode;
    private Settings settings;
    private PlaylistDAO playlistDAO;

    public Player(IPlayerFragment playerFragment, TrackManager trackManager, SystemPlayer systemPlayer, PlayerStateFactory.Factory playerStateFactory, PlayBackMode playBackMode, Settings settings, PlaylistDAO playlistDAO) {
        this.playerFragment = playerFragment;
        this.trackManager = trackManager;
        this.systemPlayer = systemPlayer;
        this.playerStateFactory = playerStateFactory;
        this.playBackMode = playBackMode;
        this.settings=settings;
        this.playlistDAO=playlistDAO;
        changeState(playerStateFactory.getIdleState(this));
        settings.subscribePlaylistId()
                .subscribeOn(Schedulers.io())

                .flatMap(
                        id->{
                            return playlistDAO.getPlayListById(id);
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        playlist->{
                            playerFragment.setPlaylist(playlist.getName());
                        }

                );
    }
    public String timeConvert(int duration){

        Integer duration_m = (duration / 60000);
        Integer duration_s = (duration / 1000) % 60;
        return String.format("%1$02d:%2$02d", duration_m, duration_s);
    }
    public void changeState(State state) {
        this.state = state;
    }

    //////////////////////////
    @Override
    public void onPlay(){
        state.onPlay();
    }
    @Override
    public void onNext(){
        state.onNext();
    }
    @Override
    public void onPrevious(){
        state.onPrevious();
    }
    @Override
    public  void onProgressChanged(int progress){
        state.setProgress(progress);
    }

}
