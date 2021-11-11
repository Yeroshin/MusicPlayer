package com.ys.musicplayer.adapters;

import android.content.Context;
import android.graphics.Color;
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


public class TrackAdapter extends UniversalAdapter {


    public TrackAdapter(Context context) {
        super(context);
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

    @Override
    public void onClick(ViewHolder holder, int position) {
        selectedItems.set(position,!selectedItems.get(position));
        holder.itemView.setSelected(selectedItems.get(position));
    }

    class  TrackViewHolder extends ViewHolder{
        TextView song_title;
        TextView song_duration;
        TextView song_info;
        View itemView;
        boolean selected=false;
        public TrackViewHolder (View itemView) {
            super(itemView);
            this.itemView = itemView;
            song_title = itemView.findViewById(R.id.song_title);
            song_duration = itemView.findViewById(R.id.song_duration);
            song_info = itemView.findViewById(R.id.song_info);
           // itemView.setOnClickListener(this);
        }

        @Override
        void bind(UniversalAdapter.RecyclerListArrayItem item){
            song_title.setText(((PlaylistItem)item).title);
            song_duration.setText(((PlaylistItem)item).duration_sec);
            song_info.setText(((PlaylistItem)item).info);
        }



      /*  @Override
        public void onClick(View v) {
            selected=!selected;
            itemView.setSelected(selected);
            int position=getLayoutPosition();
        }*/

    }
}
