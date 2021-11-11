package com.ys.musicplayer.adapters;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.ys.musicplayer.R;
import com.ys.musicplayer.db.PlaylistItem;


import java.util.ArrayList;
import java.util.List;


interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
    void onLongClick(RecyclerView.ViewHolder viewHolder);
    void onClick(UniversalAdapter.ViewHolder holder, int position);
}

public abstract class UniversalAdapter extends RecyclerView.Adapter<UniversalAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    interface ItemTouchHelperViewHolder {
        void onItemSelected();
    }
    public interface RecyclerListArrayItem{

    }
    private Context context;
    private ItemTouchHelper mItemTouchHelper;
    private ArrayList<RecyclerListArrayItem> items;
    public ArrayList<Boolean> selectedItems;
    private int viewType= R.layout.item_playlist;
    private ItemTouchHelperAdapter onClickListener;

    public UniversalAdapter(Context context) {
        this.context=context;
        this.onClickListener = this;
        this.items=new ArrayList<>();
        this.selectedItems=new ArrayList<>();
    }
    public void setView(RecyclerView playList){
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(this);
        mItemTouchHelper=new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(playList);
    }
    @Override
    public void onLongClick(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }


    public void add(ArrayList items){
        this.items.addAll(items);
        for (int i=0;i<items.size();i++){
            selectedItems.add(false);
        }
        notifyItemRangeInserted(this.items.size(),items.size());
    }

    @Override
    public void onItemDismiss(int position) {
        items.remove(position);
        selectedItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        RecyclerListArrayItem  itemFrom = items.get(fromPosition);
        RecyclerListArrayItem  itemTo = items.get(toPosition);
        items.set(fromPosition, itemTo);
        items.set(toPosition, itemFrom);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
    @Override
    public int getItemViewType(int position)
    {
        return viewType;
    }
    public void setItemViewType(int viewType)
    {
        this.viewType=viewType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerListArrayItem item=items.get(position);
        holder.itemView.setSelected(selectedItems.get(position));
        holder.bind(item);
        holder.itemView.setOnLongClickListener(v->{
            onClickListener.onLongClick(holder);
            return false;
        });
        holder.itemView.setOnClickListener(v->{
            onClickListener.onClick(holder,position);
        });
    }
    @Override
    public int getItemCount(){
        return items.size();
    }


    //////////////////////////////////////////////////////////////
    class ItemTouchHelperCallback extends ItemTouchHelper.Callback{

        private  ItemTouchHelperAdapter mAdapter;
        public ItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
            mAdapter = adapter;
        }
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            // We only want the active item to change
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                ViewHolder itemViewHolder = (ViewHolder) viewHolder;
                itemViewHolder.onItemSelected();
            }

            super.onSelectedChanged(viewHolder, actionState);
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

                icon = VectorDrawableCompat.create(context.getResources(), R.drawable.trash_can_outline, null);


            } else {
                icon = ContextCompat.getDrawable(context, R.drawable.trash_can_outline);
            }
            ///////////////////
            // Drawable icon = ContextCompat.getDrawable(context, R.drawable.trash_can_outline);
            int color = context.getResources().getColor(R.color.blue);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                icon.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
            } else {
                icon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
            icon.setBounds(itemHeight/4, itemView.getTop()+itemHeight/4, itemHeight/4*3,  itemView.getBottom()-itemHeight/4);
            icon.draw(canvas);
        }
    }
    public abstract class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        abstract void bind(RecyclerListArrayItem item);
        @Override
        public void onItemSelected() {
            float x=itemView.getX();
            int h=itemView.getHeight();
            itemView.setX(x+h/2);
            // itemView.setBackgroundColor(Color.LTGRAY);
        }


    }
}



