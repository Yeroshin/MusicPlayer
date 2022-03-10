package com.ys.musicplayer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ys.musicplayer.R;
import com.ys.musicplayer.adapters.TrackAdapter;
import com.ys.musicplayer.di.App;
import com.ys.musicplayer.dialogs.PlayListDialog;
import com.ys.musicplayer.dialogs.MediaDialog;

import javax.inject.Inject;


public class TrackFragment extends Fragment {
    Context context;
    ImageButton btn_add;
    ImageButton btn_mode;
    ImageButton btn_opt;

    RecyclerView recyclerView;
    @Inject
    TrackAdapter trackAdapter;
    @Inject
    MediaDialog mediaDialog;
    @Inject
    PlayListDialog playListDialog;
    @Inject
    TrackFragmentPresenter trackFragmentPresenter;

/*
    public TrackFragment(TrackAdapter trackAdapter,MediaDialog mediaDialog,PlayListDialog pLayListDialog,TrackFragmentPresenter trackFragmentPresenter){
        this.trackAdapter=trackAdapter;
        this.mediaDialog=mediaDialog;
        this.pLayListDialog=pLayListDialog;
        this.trackFragmentPresenter=trackFragmentPresenter;
    }*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        App.get(context).getInjector().inject(this);

    }
    public void setModeButton(int level){
        btn_mode.setImageLevel(level);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View playlist_fragment_view=inflater.inflate(R.layout.fragment_playlist, container, false);
        recyclerView = playlist_fragment_view.findViewById(R.id.playList);
        btn_add = playlist_fragment_view.findViewById(R.id.btn_add);
        btn_mode = playlist_fragment_view.findViewById(R.id.btn_mode);
        btn_opt = playlist_fragment_view.findViewById(R.id.btn_opt);
        /////////////////////////////////////////////////////////////
      /*  ColorStateList csl = AppCompatResources.getColorStateList(context, R.color.color_state_list);
        ImageViewCompat.setImageTintList(btn_add, csl);
        ImageViewCompat.setImageTintList(btn_shf, csl);
        ImageViewCompat.setImageTintList(btn_opt, csl);*/
        /////////////////////////////////////////////////////
        trackFragmentPresenter.attachView(this);
        ////////////////////////////////////////////////////

        btn_add.setOnClickListener(v ->{
            mediaDialog.show(getChildFragmentManager(),null);
        });
        btn_mode.setOnClickListener(v -> {
            trackFragmentPresenter.onClickMode();
        });
        btn_opt.setOnClickListener(v->{
            playListDialog.show(getChildFragmentManager(),null);
        });

        ///////////
        trackAdapter.setView(recyclerView);
        trackAdapter.setItemTouchCallBack(trackFragmentPresenter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(trackAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        ////////////////////////////////

        return playlist_fragment_view;
    }
    @Override
    public void onResume(){
        super.onResume();
        trackFragmentPresenter.onResume();
    }
};





