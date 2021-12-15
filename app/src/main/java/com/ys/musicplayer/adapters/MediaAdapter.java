package com.ys.musicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ys.musicplayer.R;

import com.ys.musicplayer.media.BackMediaItem;
import com.ys.musicplayer.media.IMediaItem;


import java.util.ArrayList;

interface IAdapterItem{
    ArrayList getItems();
    void onClick(UniversalAdapter adapter);
}
public class MediaAdapter extends UniversalAdapter{
    public MediaAdapter(Context context) {
        super(context);
        this.DragEnabled=false;
        this.SwipeEnabled=false;
    }
    @NonNull
    @Override
    public MediaAdapter.MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_media, parent, false);
        MediaAdapter.MediaViewHolder viewHolder = new MediaAdapter.MediaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onClick(ViewHolder holder, int position) {

        ((IMediaItem)items.get(position)).onClick(this);

        //notifyDataSetChanged();

    }

    @Override
    public void onChecked(int position,boolean isChecked) {
        selectedItems.set(position,isChecked);
    }

    @Override
    public ArrayList getSelectedItems() {
        return items;
    }

    class  MediaViewHolder extends ViewHolder {
        TextView media_title;
        View itemView;
        ImageView icon;
        CheckBox checkBox;
        public MediaViewHolder (View itemView) {
            super(itemView);
            this.itemView = itemView;
            media_title = itemView.findViewById(R.id.item_title);
            checkBox=itemView.findViewById(R.id.checkBox);
        }

        @Override
        void bind(Object item,ItemTouchHelperAdapter adapter){
            media_title.setText(((IMediaItem)item).getTitle());
            itemView.setOnClickListener(v->{
                adapter.onClick(this,getLayoutPosition());
            });
            checkBox.setOnCheckedChangeListener((View,isChecked)->{
               onChecked(getLayoutPosition(),isChecked);
            });
        }
    }
}
