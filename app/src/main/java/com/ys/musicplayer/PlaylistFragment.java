package com.ys.musicplayer;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;


import com.ys.musicplayer.adapters.TrackAdapter;
import com.ys.musicplayer.db.PlaylistItem;

import java.util.ArrayList;




public class PlaylistFragment extends Fragment {
    Context context;
    ImageButton btn_add;
    ImageButton btn_shf;
    ImageButton btn_opt;

    RecyclerView playList;


    ItemTouchHelper mItemTouchHelper;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=getContext();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View playlist_fragment_view=inflater.inflate(R.layout.fragment_playlist, container, false);
        playList = playlist_fragment_view.findViewById(R.id.playList);
        btn_add = playlist_fragment_view.findViewById(R.id.btn_add);
        btn_shf = playlist_fragment_view.findViewById(R.id.btn_shf);
        btn_opt = playlist_fragment_view.findViewById(R.id.btn_opt);
        /////////////////////////////////////////////////////////////
        ColorStateList csl = AppCompatResources.getColorStateList(context, R.color.color_state_list);
        ImageViewCompat.setImageTintList(btn_add, csl);
        ImageViewCompat.setImageTintList(btn_shf, csl);
        ImageViewCompat.setImageTintList(btn_opt, csl);
        /////////////////////////////////////////////////////

      //  btn_add.setOnClickListener(v -> /*trackManager = new TrackManager(context)*/);
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


        LinearLayoutManager layoutManager=new LinearLayoutManager(context);



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
        int i=R.layout.item_playlist;
       // int id=getResources().getIdentifier(R.layout.item_playlist,)
        TrackAdapter playlistAdapter = new TrackAdapter(context,playList);
        playlistAdapter.setItemViewType(R.layout.item_playlist);
        playList.setHasFixedSize(true);
        playList.setAdapter(playlistAdapter);
        playList.setLayoutManager(layoutManager);

      /*  ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(playlistAdapter);
        mItemTouchHelper=new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(playList);*/

        ////////////////////////////////
        playlistAdapter.add(arrayList);

        return playlist_fragment_view;
    }
};






