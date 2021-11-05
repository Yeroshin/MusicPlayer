package com.ys.musicplayer;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class PlaylistFragment extends Fragment  {
    Handler handler;
    Context context;
    ImageButton btn_add;
    ImageButton btn_shf;
    ImageButton btn_opt;

    RecyclerListAdapter playlistAdapter;
    OnStartDragListener onStartDragListener;

    RecyclerView playList;
    GestureDetector gestureDetector;
    TrackManager trackManager;

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

        btn_add.setOnClickListener(v -> trackManager = new TrackManager(context));
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
            playlistAdapter = new RecyclerListAdapter(context, arrayList,onStartDragListener);
            playList.setHasFixedSize(true);
            playList.setAdapter(playlistAdapter);
            playList.setLayoutManager(layoutManager);

            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(playlistAdapter);
            mItemTouchHelper=new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(playList);

            ////////////////////////////////


        return playlist_fragment_view;
    }
};

////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////NEW//////////////////////////////////////////////////////////////////////

    interface ItemTouchHelperViewHolder {
        void onItemSelected();
        void onItemClear();
    }
    interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }
    interface ItemTouchHelperAdapter {
        boolean onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
    }
    class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback{
        private final ItemTouchHelperAdapter mAdapter;
        public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
            mAdapter = adapter;
        }
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            // We only want the active item to change
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                itemViewHolder.onItemSelected();
            }

            super.onSelectedChanged(viewHolder, actionState);
        }
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);

            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
            itemViewHolder.onItemClear();

        }
        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
            mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
        }

        @Override
        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
            super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            View itemView = viewHolder.itemView;

            int itemHeight = itemView.getBottom() -  itemView.getTop();
            ///////////////////
            Drawable icon;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                icon = VectorDrawableCompat.create(getResources(), R.drawable.trash_can_outline, null);


            } else {
                icon = ContextCompat.getDrawable(context, R.drawable.trash_can_outline);
            }
            ///////////////////
            // Drawable icon = ContextCompat.getDrawable(context, R.drawable.trash_can_outline);
            int color = getResources().getColor(R.color.blue);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                icon.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
            } else {
                icon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
            icon.setBounds(itemHeight/4, itemView.getTop()+itemHeight/4, itemHeight/4*3,  itemView.getBottom()-itemHeight/4);
            icon.draw(canvas);
        }
    }

    class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {
        ArrayList<MyService.Playlist_item> current_playlist;
        int selected_item=-1;

        private OnStartDragListener mDragStartListener;

        public RecyclerListAdapter(Context context, ArrayList dataSet, OnStartDragListener dragStartListener) {
            mDragStartListener = dragStartListener;
            current_playlist = dataSet;
        }
        void add(int position,int itemCount){
            notifyItemRangeInserted(position,itemCount);
        }
        void remove(int position){
            onItemDismiss(position);
        }
        @Override
        public void onItemDismiss(int position) {
            if(selected_item==position){
                selected_item--;
            }
            if(position==MyService.thread.settings.current_song_pos){
                MyService.thread.settings.current_song_pos--;
            }
            current_playlist.remove(position);
            save_playlist_to_file(current_playlist,MyService.thread.settings.playlist_path);
            notifyItemRemoved(position);
        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            MyService.Playlist_item itemFrom = current_playlist.get(fromPosition);
            MyService.Playlist_item itemTo = current_playlist.get(toPosition);
            current_playlist.set(fromPosition, itemTo);
            current_playlist.set(toPosition, itemFrom);
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.item_playlist, parent, false);
            ItemViewHolder viewHolder = new ItemViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            MyService.Playlist_item item=current_playlist.get(position);
            holder.song_title.setText(item.title);
            holder.song_duration.setText(item.duration_sec);
            holder.song_info.setText(item.info);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mDragStartListener.onStartDrag(holder);
                    return false;
                }
            });
            if(position==selected_item){
                // selectedListener.song_title_selected=song_title;
                holder.song_title.setSelected(true);
                holder.song_title.setTextColor(Color.parseColor("#ffffff"));
                holder.song_title.setShadowLayer(
                        10f,   //float radius
                        0f,  //float dx
                        0f,  //float dy
                        Color.parseColor("#00ccff") //int color
                );
            }else{
                holder.song_title.setSelected(false);
                holder.song_title.setTextColor(Color.parseColor("#918F8F"));
                holder.song_title.setShadowLayer(
                        0f,   //float radius
                        0f,  //float dx
                        0f,  //float dy
                        Color.parseColor("#00ccff") //int color
                );
            }

        }

        @Override
        public int getItemCount(){
            return current_playlist.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder,View.OnClickListener {
            TextView song_title;
            TextView song_duration;
            TextView song_info;
            View itemView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                song_title = itemView.findViewById(R.id.song_title);
                song_duration = itemView.findViewById(R.id.song_duration);
                song_info = itemView.findViewById(R.id.song_info);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onItemSelected() {
                float x=itemView.getX();
                int h=itemView.getHeight();
                itemView.setX(x+h/2);
                itemView.setBackgroundColor(Color.LTGRAY);
            }

            @Override
            public void onItemClear() {
                itemView.setBackgroundColor(0);
            }

            @Override
            public void onClick(View v) {
                int position=getLayoutPosition();
                int deselected_item=selected_item;
                if(selected_item==position){
                    selected_item=-1;
                }else{
                    selected_item=position;
                    notifyItemChanged(selected_item);
                }
                notifyItemChanged(deselected_item);
            }


        }
    }
}

