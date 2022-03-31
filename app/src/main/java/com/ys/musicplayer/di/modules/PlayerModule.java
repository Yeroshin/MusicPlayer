package com.ys.musicplayer.di.modules;

import android.content.Context;


import com.ys.musicplayer.db.PlaylistDAO;
import com.ys.musicplayer.fragments.IPlayerFragment;
import com.ys.musicplayer.fragments.PlayerFragmentProxy;
import com.ys.musicplayer.models.Settings;
import com.ys.musicplayer.models.SystemPlayer;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.player.IPlayerPresenter;
import com.ys.musicplayer.player.IdleState;
import com.ys.musicplayer.player.PausedState;
import com.ys.musicplayer.player.Player;
import com.ys.musicplayer.player.PlayerStateFactory;
import com.ys.musicplayer.player.PlayingState;
import com.ys.musicplayer.player.PreparingState;
import com.ys.musicplayer.player.State;
import com.ys.musicplayer.utils.PlayBackMode;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module(includes = ModelModule.class)
public class PlayerModule {
    Context context;
    IPlayerFragment playerView;

    public PlayerModule(Context context, IPlayerFragment playerView) {
        this.context = context;
        this.playerView=playerView;
    }



    @Singleton
    @Provides
    SystemPlayer provideSystemPlayer (){return new SystemPlayer(context);};
    @Singleton
    @Provides
    Player providePlayer( TrackManager trackManager, SystemPlayer systemPlayer, PlayerStateFactory.Factory playerStateFactory, PlayBackMode playBackMode, Settings settings, PlaylistDAO playlistDAO){
        return new Player(playerView,trackManager,systemPlayer,playerStateFactory,playBackMode,settings,playlistDAO);};

    @Singleton
    @Provides
    PlayerStateFactory.Factory providePlayerStateFactory(){
        return new PlayerStateFactory.Factory() {
            @Override
            public IdleState getIdleState(Player player) {
                return new IdleState(player);
            }

            @Override
            public PreparingState getPreparingState(Player player) {
                return new PreparingState(player);
            }

            @Override
            public PlayingState getPlayingState(Player player) {
                return new PlayingState(player);
            }

            @Override
            public PausedState getPausedState(Player player) {
                return new PausedState(player);
            }
        };
    }
}
