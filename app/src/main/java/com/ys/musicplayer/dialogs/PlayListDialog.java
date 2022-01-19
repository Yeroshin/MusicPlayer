package com.ys.musicplayer.dialogs;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.musicplayer.R;
import com.ys.musicplayer.adapters.PlayListAdapter;
import com.ys.musicplayer.media.IPlayListDialogPresenter;

public class PlayListDialog extends UniversalDialog  {

    IPlayListDialogPresenter playListDialogPresenter ;
    ImageButton playlistAddButton;
    TextView path;
    public PlayListDialog(PlayListAdapter playListAdapter, IPlayListDialogPresenter  playListDialogPresenter) {
        adapter= playListAdapter;

        layout= R.layout.playlist_dialog;
        this.playListDialogPresenter=playListDialogPresenter;
    }

    @Override
    public void init() {
        playListDialogPresenter.init(adapter);
        playlistAddButton=dialog.findViewById(R.id.playlist_btn);
        path=dialog.findViewById(R.id.path);
        playlistAddButton.setOnClickListener((v)-> {
            playListDialogPresenter.onPlaylistAddButton(path.getText().toString());
        });
        ok_btn.setOnClickListener(
                v->{
                    // adapter.selectedItems
                    playListDialogPresenter.onAccept();
                    dismiss();

                }
        );

    }



    // playListContainerMediaItem.onClick(adapter);

}
