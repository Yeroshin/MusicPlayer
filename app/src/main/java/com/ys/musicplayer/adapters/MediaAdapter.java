package com.ys.musicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ys.musicplayer.R;
import com.ys.musicplayer.media.IMediaItem;
import java.util.ArrayList;



public class MediaAdapter extends BaseAdapter implements IMediaAdapter {

    private ArrayList<Boolean> checkedItems;
    public MediaAdapter(){}
    @NonNull
    @Override
    public MediaAdapter.MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_media, parent, false);
        MediaAdapter.MediaViewHolder viewHolder = new MediaAdapter.MediaViewHolder(view);
        viewHolders.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MediaViewHolder)holder).getMediaTitle().setText(((IMediaItem)items.get(position)).getTitle());
        if(((IMediaItem)items.get(position)).isCheckable()){
            ((MediaViewHolder)holder).getCheckBox().setVisibility(View.VISIBLE);
            ((MediaViewHolder)holder).getCheckBox().setChecked(checkedItems.get(position));
            ((MediaViewHolder)holder).getCheckBox().setOnCheckedChangeListener((View,isChecked)->{
                // callback.onCheckedChange(position,isChecked);
                checkedItems.set(position,isChecked);
                updateChecked();
            });
        }else {
            ((MediaViewHolder)holder).getCheckBox().setVisibility(View.GONE);
        }
        ((MediaViewHolder)holder).getIcon().setImageLevel(((IMediaItem) items.get(position)).getIcon());


    }
    @Override
    public void updateChecked(){
        for(int i=0;i<viewHolders.size();i++){
            ((MediaViewHolder)viewHolders.get(i)).getCheckBox().setChecked(checkedItems.get(viewHolders.get(i).getLayoutPosition()));
        }
    }

    @Override
    public void setItems(ArrayList items,ArrayList<Boolean> selectedItems, ArrayList<Boolean> activatedItems,ArrayList<Boolean> checkedItems){
        super.setItems(items,selectedItems, activatedItems);
        this.checkedItems=checkedItems;
    }

    class  MediaViewHolder extends ViewHolder {
        private TextView mediaTitle;
        private View itemView;
        private ImageView icon;
        private CheckBox checkBox;


        public TextView getMediaTitle() {
            return mediaTitle;
        }

        public View getItemView() {
            return itemView;
        }

        public ImageView getIcon() {
            return icon;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public MediaViewHolder (View itemView) {
            super(itemView);
            this.itemView = itemView;
            mediaTitle = itemView.findViewById(R.id.item_title);
            checkBox=itemView.findViewById(R.id.checkBox);
            icon=itemView.findViewById(R.id.icon);
        }
    }
}
