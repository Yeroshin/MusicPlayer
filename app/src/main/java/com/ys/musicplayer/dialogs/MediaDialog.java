package com.ys.musicplayer.dialogs;

import android.view.View;

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



    MediaDialogPresenter mediaDialogPresenter;
    public MediaDialog(MediaAdapter adapter, MediaDialogPresenter mediaDialogPresenter){
        this.adapter=adapter;
        layout=R.layout.track_dialog;
        this.mediaDialogPresenter = mediaDialogPresenter;
    }
    public void init(){
        mediaDialogPresenter.init(adapter);

        adapter.subscribeLoading()
                .subscribe(
                        loading->{
                            if(loading){
                                progressBar.setVisibility(View.VISIBLE);
                            }else {
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                );
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
