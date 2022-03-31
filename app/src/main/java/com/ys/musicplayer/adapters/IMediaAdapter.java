package com.ys.musicplayer.adapters;

import java.util.ArrayList;

public interface IMediaAdapter extends IBaseAdapter{
    void setItems(ArrayList items, ArrayList<Boolean> selectedItems, ArrayList<Boolean> activatedItems, ArrayList<Boolean> checkedItems);
    void updateChecked();
}
