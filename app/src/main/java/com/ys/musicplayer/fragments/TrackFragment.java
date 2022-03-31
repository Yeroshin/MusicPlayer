package com.ys.musicplayer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ys.musicplayer.R;
import com.ys.musicplayer.adapters.BaseAdapter;
import com.ys.musicplayer.adapters.ItemTouchHelperCallback;
import com.ys.musicplayer.adapters.TrackAdapter;
import com.ys.musicplayer.adapters.UniversalAdapter;
import com.ys.musicplayer.db.Track;
import com.ys.musicplayer.di.App;
import com.ys.musicplayer.di.components.DaggerTrackFragmentComponent;
import com.ys.musicplayer.di.modules.AppModule;
import com.ys.musicplayer.di.modules.ModelModule;
import com.ys.musicplayer.di.modules.TrackFragmentModule;
import com.ys.musicplayer.dialogs.PlayListDialog;
import com.ys.musicplayer.dialogs.MediaDialog;
import com.ys.musicplayer.presenters.TrackFragmentPresenter;

import java.util.ArrayList;

import javax.inject.Inject;


public class TrackFragment extends Fragment implements ITrackFragment{
    private Context context;
    private ImageButton btn_add;
    private ImageButton btn_mode;
    private ImageButton btn_opt;
    private RecyclerView recyclerView;
    @Inject
    TrackAdapter adapter;
    @Inject
    MediaDialog mediaDialog;
  /*  @Inject
    PlayListDialog playListDialog;*/
    @Inject
    ItemTouchHelperCallback itemTouchHelperCallback;
    @Inject
    TrackFragmentPresenter trackFragmentPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
       DaggerTrackFragmentComponent.builder()
               .appModule(new AppModule(context))
                .trackFragmentModule(new TrackFragmentModule(this))
                .build()
                .inject(this);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View playlist_fragment_view=inflater.inflate(R.layout.fragment_playlist, container, false);
        recyclerView = playlist_fragment_view.findViewById(R.id.playList);
        btn_add = playlist_fragment_view.findViewById(R.id.btn_add);
        btn_mode = playlist_fragment_view.findViewById(R.id.btn_mode);
        btn_opt = playlist_fragment_view.findViewById(R.id.btn_opt);
        /////////////////////////////////////////////////////////////
//TODO!!!!!!!!!!!
        btn_add.setOnClickListener(v ->{
            mediaDialog.show(getChildFragmentManager(),null);
        });
      /*  btn_mode.setOnClickListener(v -> {
            trackFragmentPresenter.onClickMode();
        });
        btn_opt.setOnClickListener(v->{
            playListDialog.show(getChildFragmentManager(),null);
        });*/

        ///////////
      /*  trackAdapter.setView(recyclerView);
        trackAdapter.setItemTouchCallBack(trackFragmentPresenter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(trackAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);*/
        ////////////////////////////////
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerView);
        ////////////////////////////////
        return playlist_fragment_view;
    }

    @Override
    public void setModeButton(int level){
        btn_mode.setImageLevel(level);
    }
    @Override
    public void setItems(ArrayList items, ArrayList<Boolean> selectedItems, ArrayList<Boolean> activatedItems) {
        adapter.setItems(items, selectedItems, activatedItems);
    }

    @Override
    public void updateActivated() {
        adapter.updateActivated();
    }

    @Override
    public void updateSelected() {
        adapter.updateActivated();
    }
};






