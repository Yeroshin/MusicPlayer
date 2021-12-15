package com.ys.musicplayer.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ys.musicplayer.R;
import com.ys.musicplayer.adapters.TrackAdapter;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.di.App;
import com.ys.musicplayer.dialogs.PlayListDialog;
import com.ys.musicplayer.dialogs.TrackDialog;
import com.ys.musicplayer.dialogs.UniversalDialog;

import java.util.ArrayList;

import javax.inject.Inject;


public class PlaylistFragment extends Fragment {
    Context context;
    ImageButton btn_add;
    ImageButton btn_shf;
    ImageButton btn_opt;

    RecyclerView recyclerView;
    @Inject
    TrackAdapter trackAdapter;
    @Inject
    TrackDialog trackDialog;
    @Inject
    PlayListDialog playListDialog;
    @Inject
    PlaylistFragmentPresenter playlistFragmentPresenter;

/*
    public PlaylistFragment(TrackAdapter trackAdapter,TrackDialog trackDialog,PlayListDialog pLayListDialog,PlaylistFragmentPresenter playlistFragmentPresenter){
        this.trackAdapter=trackAdapter;
        this.trackDialog=trackDialog;
        this.pLayListDialog=pLayListDialog;
        this.playlistFragmentPresenter=playlistFragmentPresenter;
    }*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        App.get(context).getInjector().inject(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View playlist_fragment_view=inflater.inflate(R.layout.fragment_playlist, container, false);
        recyclerView = playlist_fragment_view.findViewById(R.id.playList);
        btn_add = playlist_fragment_view.findViewById(R.id.btn_add);
        btn_shf = playlist_fragment_view.findViewById(R.id.btn_shf);
        btn_opt = playlist_fragment_view.findViewById(R.id.btn_opt);
        /////////////////////////////////////////////////////////////
      /*  ColorStateList csl = AppCompatResources.getColorStateList(context, R.color.color_state_list);
        ImageViewCompat.setImageTintList(btn_add, csl);
        ImageViewCompat.setImageTintList(btn_shf, csl);
        ImageViewCompat.setImageTintList(btn_opt, csl);*/
        /////////////////////////////////////////////////////

        btn_add.setOnClickListener(v ->{
            trackDialog.show(getChildFragmentManager(),null);
        });
        btn_shf.setOnClickListener(v -> {
          /*  switch (MyService.thread.shf_rep_lin) {
                case 0:
                    btn_shf.setImageResource(R.drawable.shuffle_disabled);
                    break;
                case 1:

                    btn_shf.setImageResource(R.drawable.shuffle);
                    break;
                case 2:

                    btn_shf.setImageResource(R.drawable.repeat);
                    break;
            }
            break;*/
        });
        btn_opt.setOnClickListener(v->{
            playListDialog.show(getChildFragmentManager(),null);
        });






        ArrayList arrayList=new ArrayList();
        ///////test
        for(int i=0;i<20;i++){
            Track tmp=new Track();
            tmp.title="asdfd";
            tmp.duration_sec="3:20";
            tmp.info="dfgdsfg";
            arrayList.add(tmp);
        }
        ///////////
        trackAdapter.setView(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(trackAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        ////////////////////////////////
      //  trackAdapter.addItems(arrayList);

        return playlist_fragment_view;
    }
    @Override
    public void onResume(){
        super.onResume();
        playlistFragmentPresenter.onResume();
    }
};






