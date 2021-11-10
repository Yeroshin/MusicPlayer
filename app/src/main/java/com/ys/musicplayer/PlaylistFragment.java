package com.ys.musicplayer;

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


import com.ys.musicplayer.adapters.TrackAdapter;
import com.ys.musicplayer.db.PlaylistItem;
import com.ys.musicplayer.di.App;
import com.ys.musicplayer.dialogs.TrackManager;

import java.util.ArrayList;

import javax.inject.Inject;


public class PlaylistFragment extends Fragment {
    Context context;
    ImageButton btn_add;
    ImageButton btn_shf;
    ImageButton btn_opt;

    RecyclerView recyclerView;
    @Inject
    TrackAdapter playlistAdapter;
    @Inject
    TrackManager trackManager;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=getContext();
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
        ColorStateList csl = AppCompatResources.getColorStateList(context, R.color.color_state_list);
        ImageViewCompat.setImageTintList(btn_add, csl);
        ImageViewCompat.setImageTintList(btn_shf, csl);
        ImageViewCompat.setImageTintList(btn_opt, csl);
        /////////////////////////////////////////////////////

        btn_add.setOnClickListener(v ->{
            trackManager.init(context);
            trackManager.show();
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
           // fileManager = new FileManager(context, options_type, PlaylistFragment::add_to_PlayList);
        });






        ArrayList arrayList=new ArrayList();
        ///////test
        for(int i=0;i<20;i++){
            PlaylistItem tmp=new PlaylistItem();
            tmp.title="asdfd";
            tmp.duration_sec="3:20";
            tmp.info="dfgdsfg";
            arrayList.add(tmp);
        }
        ///////////
        playlistAdapter.setView(recyclerView);
        playlistAdapter.setItemViewType(R.layout.item_playlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(playlistAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        ////////////////////////////////
        playlistAdapter.add(arrayList);

        return playlist_fragment_view;
    }
};






