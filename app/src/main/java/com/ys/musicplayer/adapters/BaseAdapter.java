package com.ys.musicplayer.adapters;

import android.view.View;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ys.musicplayer.fragments.IMediaDialogPresenter;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseAdapter extends RecyclerView.Adapter implements IBaseAdapter {

    private IMediaDialogPresenter mediaDialogPresenter;
    protected ArrayList items;
    private ArrayList<Boolean> selectedItems;
    private ArrayList<Boolean> activatedItems;
    protected ArrayList<ViewHolder> viewHolders;



    public BaseAdapter() {
       // this.items=new ArrayList();
       // this.selectedItems=new ArrayList<>();
        viewHolders=new ArrayList();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.itemView.setActivated(activatedItems.get(position));
        holder.itemView.setSelected(selectedItems.get(position));
        holder.itemView.setOnClickListener(v->{
            mediaDialogPresenter.onClick(position);
        });
    }

    public void setCallback(IMediaDialogPresenter mediaDialogPresenter){
        this.mediaDialogPresenter=mediaDialogPresenter;
    }

  /*  @Override
    public void setActivated(int position){

        for (int i=0;i<viewHolders.size();i++){
            ((ViewHolder)viewHolders.get(i)).itemView.setActivated(false);
        }
        for(int i=0;i<viewHolders.size();i++){
            if(((ViewHolder)viewHolders.get(i)).getLayoutPosition()==position){
                ((ViewHolder)viewHolders.get(i)).itemView.setActivated(true);
            }
        }

    }*/
  /*  @Override
    public void setSelected(int position){

        for (int i=0;i<viewHolders.size();i++){
            ((ViewHolder)viewHolders.get(i)).itemView.setSelected(false);
        }
        for(int i=0;i<viewHolders.size();i++){
            if(((ViewHolder)viewHolders.get(i)).getLayoutPosition()==position){
                ((ViewHolder)viewHolders.get(i)).itemView.setSelected(true);
            }
        }

    }*/
    public void selection(ViewHolder holder, int position){


    }
  /*  public void clearItems(){
        selectedItems=new ArrayList();
        items=new ArrayList();
        notifyDataSetChanged();
    }*/
  /*  public void addItems(ArrayList items){
        // this.items.addAll(items);
        for (int i=0;i<items.size();i++){
            this.items.add(items.get(i));
        }
        for (int i=0;i<items.size();i++){
            selectedItems.add(false);
        }
        notifyDataSetChanged();
        //notifyItemRangeInserted(this.items.size(),items.size());
    }*/
    @Override
    public void setItems(ArrayList items,ArrayList<Boolean> selectedItems, ArrayList<Boolean> activatedItems){
        this.items=items;
        this.selectedItems=selectedItems;
        this.activatedItems=activatedItems;
        // this.items.addAll(items);
       /* selectedItems=new ArrayList();
        for (int i=0;i<items.size();i++){
            selectedItems.add(false);
        }*/
        notifyDataSetChanged();
    }
    @Override
    public void updateActivated(){
        for(int i=0;i<viewHolders.size();i++){
            viewHolders.get(i).itemView.setActivated(activatedItems.get(viewHolders.get(i).getLayoutPosition()));
        }
    }
    @Override
    public void updateSelected(){
        for(int i=0;i<viewHolders.size();i++){
            viewHolders.get(i).itemView.setSelected(selectedItems.get(viewHolders.get(i).getLayoutPosition()));
        }
    }

  /*  public void removeItem(int position){
        items.remove(position);
        selectedItems.remove(position);
        notifyItemRemoved(position);
    }*/

    @Override
    public int getItemCount(){
        if(items!=null){
            return items.size();
        }else {
            return 0;
        }

    }


    //////////////////////////////////////////////////////////////
    public static abstract class ViewHolder<T> extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }


        public void onItemSelected() {
            float x=itemView.getX();
            int h=itemView.getHeight();
            itemView.setX(x+h/2);
        }


    }

}
