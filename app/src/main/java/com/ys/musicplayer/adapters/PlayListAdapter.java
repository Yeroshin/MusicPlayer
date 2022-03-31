package com.ys.musicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ys.musicplayer.R;
import com.ys.musicplayer.db.PlayList;

public class PlayListAdapter extends UniversalAdapter{
    public PlayListAdapter(Context context) {
        super(context);
        this.DragEnabled=false;
        this.SwipeEnabled=true;
        selection=single;
    }

    @NonNull
    @Override
    public PlayListAdapter.PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_playlist, parent, false);
        PlayListAdapter.PlayListViewHolder viewHolder = new PlayListAdapter.PlayListViewHolder(view);
        return viewHolder;
    }

  /*  @Override
    public void onItemDismiss(int position) {
        items.remove(position);
        selectedItems.remove(position);
        notifyItemRemoved(position);
    }*/

    @Override
    public void onClick(ViewHolder holder, int position) {
        selection(holder,position);
    }

    @Override
    public void onChecked(int position, boolean isChecked) {

    }


    class PlayListViewHolder extends ViewHolder{
        TextView id;
        TextView playlist_name;
        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);
          //  id = itemView.findViewById(R.id.id);
            playlist_name = itemView.findViewById(R.id.song_title);
        }

        @Override
        void bind(Object item, ItemTouchHelperAdapter adapter) {
           // id.setText(String.valueOf(((PlayList)item).id));
            playlist_name.setText(((PlayList)item).name);
            itemView.setSelected(selectedItems.get(getLayoutPosition()));
            if(activatedItem==getLayoutPosition()){
                itemView.setActivated(true);
            }
            itemView.setOnTouchListener(
                    (v,s)->{
                        if(items.size()!=1){
                            SwipeEnabled=true;
                        }else{
                            SwipeEnabled=false;
                        }
                        return false;
                    }
            );
            itemView.setOnClickListener(v->{

                adapter.onClick(this,getLayoutPosition());
            });
        }
    }
}
