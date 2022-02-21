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
import com.ys.musicplayer.di.App;

import javax.inject.Inject;

public class PlayerFragment extends Fragment {
    private Context context;
    TextView playlist;
    TextView track_title;
    TextView duration_counter;
    SeekBar seekBar;
    TextView duration_info;
    private ImageButton playButton;
    @Inject
    public PlayerFragmentPresenter playerFragmentPresenter;

    public void setPlaylist(String playlist) {
        this.playlist.setText(playlist);
    }

    public void setTrack_title(String track_title) {
        this.track_title.setText(track_title);
    }

    public void setDuration_counter(String duration_counter) {
        this.duration_counter.setText(duration_counter);
    }

    public void setSeekBar(int progress) {
        this.seekBar.setProgress(progress);

    }

    public void setDuration_info(String duration_info) {
        this.duration_info.setText(duration_info);
    }

    public void setPlayButton(boolean playing) {
        playButton.setActivated(playing);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        App.get(context).getInjector().inject(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View playerFragmentView=inflater.inflate(R.layout.player, container, false);
        playlist = playerFragmentView.findViewById(R.id.playlist);
        track_title = playerFragmentView.findViewById(R.id.track_title);
        duration_counter = playerFragmentView.findViewById(R.id.duration_counter);
        seekBar = playerFragmentView.findViewById(R.id.seekBar);
        seekBar.setMax(100);
        duration_info = playerFragmentView.findViewById(R.id.duration_info);
        playButton = playerFragmentView.findViewById(R.id.btn_play);
        //////////////////////////////
        playerFragmentPresenter.setView(this);

        playButton.setOnClickListener(
                v->{
                    playerFragmentPresenter.onClickPlay();
                }
        );

        return playerFragmentView;
    }

}
