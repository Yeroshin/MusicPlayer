package com.ys.musicplayer.dialogs;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ys.musicplayer.R;
import com.ys.musicplayer.adapters.PlayListAdapter;
import com.ys.musicplayer.media.IPlayListDialogPresenter;

import javax.inject.Inject;

public class PlayListDialog extends UniversalDialog  {

    IPlayListDialogPresenter playListDialogPresenter ;
    ImageButton playlistAddButton;
    TextView path;
    @Inject
    PlayListAdapter adapter;
    public PlayListDialog( IPlayListDialogPresenter  playListDialogPresenter) {
        layout= R.layout.playlist_dialog;
        this.playListDialogPresenter=playListDialogPresenter;
    }
    public void showError(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Dialog title");
        builder.setMessage("Dialog message");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, you will be overriding this anyway
            }
        });

        final AlertDialog dialog = builder.create();
        // Make sure you show the dialog first before overriding the
        // OnClickListener
        dialog.show();
    }

    public void init() {
       /* playListDialogPresenter.init(adapter);
        playlistAddButton=findViewById(R.id.playlist_btn);
        path=dialog.findViewById(R.id.path);
        playlistAddButton.setOnClickListener((v)-> {
            if(playListDialogPresenter.onPlaylistAddButton(path.getText().toString())){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.noti_icon);
                builder.setTitle(R.string.error);
                builder.setMessage(R.string.aPlaylistWithTheSameNameAlreadyExists);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing, you will be overriding this anyway
                    }
                });
                final AlertDialog dialog = builder.create();
                // Make sure you show the dialog first before overriding the
                // OnClickListener
                dialog.show();
            };
        });
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
                    playListDialogPresenter.onAccept();
                    dismiss();

                }
        );*/

    }



    // playListContainerMediaItem.onClick(adapter);

}
