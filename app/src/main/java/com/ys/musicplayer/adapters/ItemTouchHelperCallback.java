package com.ys.musicplayer.adapters;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.ys.musicplayer.R;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback{
    public interface Callback {
        boolean onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
    }
    private boolean dragEnabled=false;
    private boolean swipeEnabled=false;
    private  Callback callback;
    public ItemTouchHelperCallback(Callback callback) {
        this.callback = callback;
    }
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // We only want the active item to change
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            BaseAdapter.ViewHolder itemViewHolder = (BaseAdapter.ViewHolder) viewHolder;
            itemViewHolder.onItemSelected();
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return dragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return swipeEnabled;
    }
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
        callback.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        callback.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;

        int itemHeight = itemView.getBottom() -  itemView.getTop();
        ///////////////////
        Drawable icon;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            icon = VectorDrawableCompat.create(recyclerView.getContext().getResources(), R.drawable.trash_can_outline, null);
        } else {
            icon = ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.trash_can_outline);
        }
        ///////////////////
        // Drawable icon = ContextCompat.getDrawable(context, R.drawable.trash_can_outline);
        int color = recyclerView.getContext().getResources().getColor(R.color.blue);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            icon.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
        } else {
            icon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
        icon.setBounds(itemHeight/4, itemView.getTop()+itemHeight/4, itemHeight/4*3,  itemView.getBottom()-itemHeight/4);
        icon.draw(canvas);
    }
}
