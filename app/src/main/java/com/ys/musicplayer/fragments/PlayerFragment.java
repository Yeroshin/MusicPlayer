package com.ys.musicplayer.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ys.musicplayer.MainContract;
import com.ys.musicplayer.R;
import com.ys.musicplayer.custom_views.Visualizer_view;
import com.ys.musicplayer.di.App;
import com.ys.musicplayer.player.IPlayerPresenter;

import javax.inject.Inject;

public class PlayerFragment extends Fragment {
    private Context context;
    private TextView playlist;
    private TextView track_title;
    private TextView duration_counter;
    private SeekBar seekBar;
    private TextView duration_info;
    private ImageButton rewButton;
    private ImageButton playButton;
    private ImageButton fwdButton;
    private Visualizer_view visualizerView;
    @Inject
    public IPlayerPresenter playerPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View playerFragmentView=inflater.inflate(R.layout.player, container, false);
        playlist = playerFragmentView.findViewById(R.id.playlist);
        track_title = playerFragmentView.findViewById(R.id.track_title);
        track_title.setSelected(true);
        duration_counter = playerFragmentView.findViewById(R.id.duration_counter);
        seekBar = playerFragmentView.findViewById(R.id.seekBar);

        duration_info = playerFragmentView.findViewById(R.id.duration_info);
        rewButton = playerFragmentView.findViewById(R.id.btn_rew);
        playButton = playerFragmentView.findViewById(R.id.btn_play);
        fwdButton = playerFragmentView.findViewById(R.id.btn_fwd);
        visualizerView = playerFragmentView.findViewById(R.id.visualizer);
        //////////////////////////////



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    playerPresenter.onProgressChanged(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rewButton.setOnClickListener(
                v->{
                    playerPresenter.onPrevious();
                }
        );
        playButton.setOnClickListener(
                v->{
                    playerPresenter.onPlay();
                }
        );
        fwdButton.setOnClickListener(
                v->{
                    playerPresenter.onNext();
                }
        );

        return playerFragmentView;
    }

    public void setPlaylist(String playlist) {
        this.playlist.setText(playlist);
    }

    public void setTrack_title(String track_title) {
        this.track_title.setText(track_title);
    }
    public void setTrackTitleBuffering(){
        this.track_title.setText(getResources().getText(R.string.buffering));
    }

    public void setDuration_counter(String duration_counter) {
        this.duration_counter.setText(duration_counter);
    }

    public void setSeekBar(int progress,int max) {
        seekBar.setMax(max);
        this.seekBar.setProgress(progress);

    }

    public void setDuration_info(String duration_info) {
        this.duration_info.setText(duration_info);
    }

    public void setPlayButton(boolean playing) {
        playButton.setActivated(playing);
    }
    public void setVisualizer(int sessionId){
        visualizerView.setAudioSessionId(sessionId);
    }

}
