package com.ys.musicplayer.dialogs;

import com.ys.musicplayer.R;
import com.ys.musicplayer.adapters.MediaAdapter;

import com.ys.musicplayer.media.IMediaItem;
import com.ys.musicplayer.media.RootMediaItem;

public class MediaDialog extends UniversalDialog {
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
    MediaDialogPresenter mediaDialogPresenter;
    public MediaDialog(MediaAdapter trackAdapter, RootMediaItem rootMediaItem, MediaDialogPresenter mediaDialogPresenter){
        this.adapter=trackAdapter;
        this.rootMediaItem=rootMediaItem;
        this.mediaDialogPresenter = mediaDialogPresenter;
        layout=R.layout.track_dialog;
    }
    public void init(){
        mediaDialogPresenter.init(adapter);
        rootMediaItem.onClick(adapter);
        ok_btn.setOnClickListener(
                v->{
                    // adapter.selectedItems
                    mediaDialogPresenter.onAccept();
                    dismiss();

                }
        );
    }

  /*  public void show(){
        dialog.show();
    }*/
}
