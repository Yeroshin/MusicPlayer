package com.ys.musicplayer.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Environment;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ys.musicplayer.R;
import com.ys.musicplayer.adapters.TrackAdapter;
import com.ys.musicplayer.db.PlaylistItem;
import com.ys.musicplayer.di.App;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

public class TrackManager {
    private Context context;
    private LinearLayout dialog_layout;
    private Dialog dialog;
    private ImageButton ok_btn;
    private ImageButton cancel_btn;
    private ImageButton network_btn;
    private TextView current_path;
    private TextView network_path;
    private RecyclerView recyclerView;
    private View disableView;

    @Inject
    TrackAdapter playlistAdapter;

    public TrackManager(){


    }
    public void init(Context context){
        this.context=context;
        App.get(context).getInjector().inject(this);
        ////////////////////
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialog_layout =  (LinearLayout) inflater.inflate(R.layout.track_dialog_layout, null, false);//files_list
        /////////////////////
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.track_dialog_layout);
        /////////////////////
        Display disp=((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point outSmallestSize=new Point();
        Point outLargestSize=new Point();
        disp.getCurrentSizeRange (outSmallestSize, outLargestSize);
        Point screeSize = new Point();
        Point screeSize1 = new Point();
        disp.getRealSize(screeSize);///Not Matched!
        disp.getSize(screeSize1);///Matched!
        ////////////notused?
        dialog_layout.setMinimumWidth((int)(screeSize1.x* 0.9f));
        dialog_layout.setMinimumHeight((int)(screeSize1.y* 0.9f));
        dialog.getWindow().setLayout((int)(screeSize1.x* 0.92f), ViewGroup.LayoutParams.MATCH_PARENT);
        ////////////
        current_path = dialog.findViewById(R.id.current_path);
        network_path = dialog.findViewById(R.id.network_path);
        recyclerView= dialog.findViewById(R.id.recyclerView);
        disableView= dialog.findViewById(R.id.disableView);
        network_btn= dialog.findViewById(R.id.network_btn);
        cancel_btn= dialog.findViewById(R.id.cancel_btn);
        ok_btn= dialog.findViewById(R.id.ok_btn);
        ///////////////////////////

        /////////////////////////
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setOnShowListener(new DialogInterface.OnShowListener(){
            @Override
            public void onShow(DialogInterface dialog) {

            }
        });
        dialog.setCancelable(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK&&event.getAction() == KeyEvent.ACTION_DOWN) {
                   /* file_manage("..");
                    recyclerListAdapter.notifyDataSetChanged();
                    dirlist_recyclerView.scrollToPosition(0);*/
                    return true;
                }
                return false;
            }
        });
        /////////////////////////////
        cancel_btn.setOnClickListener(v->dialog.dismiss());
        /////////////////////////////
        ArrayList arrayList=new ArrayList();
        ///////test
        for(int i=0;i<20;i++){
            PlaylistItem tmp=new PlaylistItem();
            tmp.title="asdfd";
            tmp.duration_sec="3:20";
            tmp.info="dfgdsfg";
            arrayList.add(tmp);
        }
        ///////////
        playlistAdapter.setView(recyclerView);
        playlistAdapter.setItemViewType(R.layout.item_playlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(playlistAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        ////////////////////////////////
        playlistAdapter.add(arrayList);
    }
    public void show(){
        dialog.show();
    }
}
