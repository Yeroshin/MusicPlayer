package com.ys.musicplayer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ys.musicplayer.R;
import com.ys.musicplayer.db.Track;

public class TrackAdapter extends BaseAdapter{

    @NonNull
    @Override
    public TrackAdapter.TrackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_track, viewGroup, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TrackViewHolder )holder).getSong_title().setText(((Track)items.get(position)).getTitle());
        ((TrackViewHolder )holder).getSong_duration().setText(((Track)items.get(position)).getDuration());
        ((TrackViewHolder )holder).getSong_info().setText(((Track)items.get(position)).getInfo());
    }

    public class TrackViewHolder  extends BaseAdapter.ViewHolder {
        private TextView song_title;
        private TextView song_duration;
        private TextView song_info;

        public TextView getSong_title() {
            return song_title;
        }

        public TextView getSong_duration() {
            return song_duration;
        }

        public TextView getSong_info() {
            return song_info;
        }

        public TrackViewHolder(View view) {
            super(view);
            song_title = itemView.findViewById(R.id.song_title);
            song_duration = itemView.findViewById(R.id.id);
            song_info = itemView.findViewById(R.id.song_info);
        }



    }
}
