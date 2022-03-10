package com.ys.musicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ys.musicplayer.R;
import com.ys.musicplayer.db.Track;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class TrackAdapter extends UniversalAdapter implements TrackItemTouchHelperAdapter{



    private PublishSubject selectedSubject;
    public TrackAdapter(Context context) {
        super(context);
        this.DragEnabled=true;
        this.SwipeEnabled=true;
        selection=single;
        selectedSubject=PublishSubject.create();
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_track, parent, false);
        TrackViewHolder viewHolder = new TrackViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onClick(ViewHolder holder, int position) {
        if(holder.itemView.isSelected()){
            position=-1;
        }
        selectedSubject.onNext(position);
        //selection(holder,position);
    }
    @Override
    public Observable<Integer> subscribeSelectedItem() {
        return selectedSubject;
    }

    @Override
    public void onChecked(int position,boolean isChecked) {

    }



    class  TrackViewHolder extends ViewHolder{
        TextView song_title;
        TextView song_duration;
        TextView song_info;
        //View itemView;
        boolean selected=false;
        public TrackViewHolder (View itemView) {
            super(itemView);
            //this.itemView = itemView;
            song_title = itemView.findViewById(R.id.playlist_name);
            song_duration = itemView.findViewById(R.id.id);
            song_info = itemView.findViewById(R.id.song_info);
           // itemView.setOnClickListener(this);
        }

        @Override
        void bind(Object item,ItemTouchHelperAdapter adapter){
            song_title.setText(((Track)item).getTitle());
            song_duration.setText(((Track)item).getDuration_sec());
            song_info.setText(((Track)item).getInfo());
            itemView.setSelected(selectedItems.get(getLayoutPosition()));
            if(activatedItem==getLayoutPosition()){
                itemView.setActivated(true);
            }

           /* itemView.setOnLongClickListener(v->{
                adapter.onLongClick(this);
                return false;
            });*/
            itemView.setOnClickListener(v->{
                subjectLoading.onNext(true);
                adapter.onClick(this,getLayoutPosition());
            });
        }



      /*  @Override
        public void onClick(View v) {
            selected=!selected;
            itemView.setSelected(selected);
            int position=getLayoutPosition();
        }*/

    }
}
