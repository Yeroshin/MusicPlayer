package com.ys.musicplayer.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.ys.musicplayer.MainContract;
import com.ys.musicplayer.R;
import com.ys.musicplayer.di.App;

import javax.inject.Inject;

public class PlayerFragment extends Fragment {
    private Context context;
    private ImageButton playButton;
    @Inject
    public PlayerFragmentPresenter playerFragmentPresenter;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        App.get(context).getInjector().inject(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View playerFragmentView=inflater.inflate(R.layout.player, container, false);
        playButton = playerFragmentView.findViewById(R.id.btn_play);

        playButton.setOnClickListener(
                v->{
                    playerFragmentPresenter.onClickPlay();
                }
        );

        return playerFragmentView;
    }
}
