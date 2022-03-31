package com.ys.musicplayer.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.ys.musicplayer.R;
import com.ys.musicplayer.adapters.MediaAdapter;
import com.ys.musicplayer.di.components.DaggerMediaDialogComponent;
import com.ys.musicplayer.di.modules.AppModule;
import com.ys.musicplayer.di.modules.MediaDialogModule;
import com.ys.musicplayer.fragments.IMediaDialogPresenter;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class MediaDialog extends UniversalDialog implements IMediaDialog{

    private Context context;
   /* private LinearLayout dialog_layout;
    private Dialog dialog;
    private ImageButton ok_btn;
    private ImageButton cancel_btn;
    private ImageButton network_btn;
    private TextView current_path;
    private TextView network_path;
    private RecyclerView recyclerView;
    private View disableView;*/
    @Inject
    MediaAdapter adapter;

    @Inject
    IMediaDialogPresenter mediaDialogPresenter;
    public MediaDialog(){

    }
    @Override
    public void onAttach(Context context) {
        this.context=context;
        DaggerMediaDialogComponent.builder()
                .appModule(new AppModule(context))
                .mediaDialogModule(new MediaDialogModule(context,this))
                .build()
                .inject(this);
        adapter.setCallback(mediaDialogPresenter);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout=R.layout.media_dialog;
        View view=super.onCreateView(inflater, container, savedInstanceState);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        cancel_btn.setOnClickListener(
                v->{
                   // adapter.clearItems();
                    dismiss();
                }
        );
        ok_btn.setOnClickListener(
                v->{
                    // adapter.selectedItems
                    mediaDialogPresenter.onAccept();
                    dismiss();

                }
        );
        return view;
    }
    @Override
    public void setLoading(boolean loading){
      /*  try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        if(loading){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void setItems(ArrayList items,ArrayList<Boolean> selectedItems, ArrayList<Boolean> activatedItems,ArrayList<Boolean> checkedItems) {
        adapter.setItems(items,selectedItems, activatedItems,checkedItems);
    }

    @Override
    public void updateChecked() {
        adapter.updateChecked();
    }


    @Override
    public void setItems(ArrayList items, ArrayList<Boolean> selectedItems, ArrayList<Boolean> activatedItems) {
        adapter.setItems(items, selectedItems, activatedItems);
    }

    @Override
    public void updateActivated() {
        adapter.updateActivated();
    }

    @Override
    public void updateSelected() {
        adapter.updateSelected();
    }
}
