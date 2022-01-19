package com.ys.musicplayer.dialogs;

import com.ys.musicplayer.R;
import com.ys.musicplayer.adapters.MediaAdapter;

import com.ys.musicplayer.media.IMediaItem;
import com.ys.musicplayer.media.RootMediaItem;

public class TrackDialog extends UniversalDialog {
   /* private Context context;
    private LinearLayout dialog_layout;
    private Dialog dialog;
    private ImageButton ok_btn;
    private ImageButton cancel_btn;
    private ImageButton network_btn;
    private TextView current_path;
    private TextView network_path;
    private RecyclerView recyclerView;
    private View disableView;*/



    IMediaItem rootMediaItem;
    TrackDialogPresenter trackDialogPresenter;
    public TrackDialog(MediaAdapter trackAdapter,RootMediaItem rootMediaItem,TrackDialogPresenter trackDialogPresenter){
        this.adapter=trackAdapter;
        this.rootMediaItem=rootMediaItem;
        this.trackDialogPresenter=trackDialogPresenter;
        layout=R.layout.track_dialog;
    }
    public void init(){
        trackDialogPresenter.init(adapter);
        rootMediaItem.onClick(adapter);
        ok_btn.setOnClickListener(
                v->{
                    // adapter.selectedItems
                    trackDialogPresenter.onAccept();
                    dismiss();

                }
        );
    }

  /*  public void show(){
        dialog.show();
    }*/
}
