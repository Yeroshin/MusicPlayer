package com.ys.musicplayer.adapters;

import androidx.recyclerview.widget.RecyclerView;

import com.ys.musicplayer.fragments.IMediaDialogPresenter;

import java.util.ArrayList;

public interface IBaseAdapter {
    void setItems(ArrayList items, ArrayList<Boolean> selectedItems, ArrayList<Boolean> activatedItems);
    void updateActivated();
    void updateSelected();
}
