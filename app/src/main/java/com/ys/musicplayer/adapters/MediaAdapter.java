package com.ys.musicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ys.musicplayer.R;
import com.ys.musicplayer.db.PlaylistItem;

public class MediaAdapter extends UniversalAdapter{
    public MediaAdapter(Context context) {
        super(context);
    }
    @NonNull
    @Override
    public MediaAdapter.MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(viewType, parent, false);
        MediaAdapter.MediaViewHolder viewHolder = new MediaAdapter.MediaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onClick(ViewHolder holder, int position) {

    }
    class  MediaViewHolder extends ViewHolder {
        TextView media_title;
        View itemView;
        public MediaViewHolder (View itemView) {
            super(itemView);
            this.itemView = itemView;
            media_title = itemView.findViewById(R.id.song_title);
        }

        @Override
        void bind(UniversalAdapter.RecyclerListArrayItem item){
            media_title.setText(((PlaylistItem)item).title);
        }
    }
}
