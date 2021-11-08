package com.ys.musicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ys.musicplayer.R;
import com.ys.musicplayer.db.PlayList;
import com.ys.musicplayer.db.PlaylistItem;


import java.util.ArrayList;


public class TrackAdapter extends ItemTouchHelperAdapter.RecyclerListAdapter {


    public TrackAdapter(Context context, RecyclerView playList) {
        super(context,playList);

    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(viewType, parent, false);
        TrackViewHolder viewHolder = new TrackViewHolder(view);
        return viewHolder;
    }



    class  TrackViewHolder extends ViewHolder {
        TextView song_title;
        TextView song_duration;
        TextView song_info;
        View itemView;

        public TrackViewHolder (View itemView) {
            super(itemView);

            this.itemView = itemView;
            song_title = itemView.findViewById(R.id.song_title);
            song_duration = itemView.findViewById(R.id.song_duration);
            song_info = itemView.findViewById(R.id.song_info);
            //itemView.setOnClickListener(this);
        }

        @Override
        void bind(RecyclerListAdapter.RecyclerListArrayItem item){
            song_title.setText(((PlaylistItem)item).title);
            song_duration.setText(((PlaylistItem)item).duration_sec);
            song_info.setText(((PlaylistItem)item).info);
        }

        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {

        }
    }
}
