package com.ys.musicplayer.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
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
import android.widget.ProgressBar;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ys.musicplayer.R;
import com.ys.musicplayer.adapters.UniversalAdapter;

import java.util.ArrayList;

interface IUniversalDialog{
    void init();
}
public abstract class UniversalDialog extends DialogFragment implements IUniversalDialog{
   /* public interface DialogListener {
        void onOKClick();
    };*/
    private Context context;
    public FrameLayout dialog_layout;
    public ImageButton ok_btn;
    private ImageButton cancel_btn;
    private RecyclerView recyclerView;
    public ProgressBar progressBar;
    UniversalAdapter adapter;
    int layout;
    Dialog dialog;

    public UniversalDialog() {
    }
  /*  public void setDialogListener(DialogListener dialogListener){
        this.dialogListener=dialogListener;
    }*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        context=getActivity();
        dialog=getDialog();
        dialog_layout =  (FrameLayout) inflater.inflate(layout, null, false);//files_list
        /////////////////////
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        /////////////////////
        Display disp=((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point outSmallestSize=new Point();
        Point outLargestSize=new Point();
        disp.getCurrentSizeRange (outSmallestSize, outLargestSize);
        // Point screeSize = new Point();
        Point screeSize1 = new Point();
        //  disp.getRealSize(screeSize);///Not Matched!
        disp.getSize(screeSize1);///Matched!
        ////////////notused?
        dialog_layout.setMinimumWidth((int)(screeSize1.x* 0.9f));
        dialog_layout.setMinimumHeight((int)(screeSize1.y* 0.9f));
        dialog.getWindow().setLayout((int)(screeSize1.x* 0.92f), ViewGroup.LayoutParams.MATCH_PARENT);
        ////////////
        recyclerView= dialog_layout.findViewById(R.id.recyclerView);
        cancel_btn= dialog_layout.findViewById(R.id.cancel_btn);
        ok_btn= dialog_layout.findViewById(R.id.ok_btn);
        progressBar= dialog_layout.findViewById(R.id.progressBar);
        /////////////////////////
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setOnShowListener(new DialogInterface.OnShowListener(){
            @Override
            public void onShow(DialogInterface dialog) {
              init();
            }
        });

        setCancelable(false);
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
        cancel_btn.setOnClickListener(
                v->{
                    adapter.clearItems();
                    dismiss();
                }
        );

        /////////////////////////////
      //  ArrayList arrayList=new ArrayList();


        ///////test
        //RootMediaItem mediaItem=new RootMediaItem();
      /*  for(int i=0;i<20;i++){
            PlaylistItem tmp=new PlaylistItem();
            tmp.title="asdfd";
            tmp.duration_sec="3:20";
            tmp.info="dfgdsfg";
            arrayList.add(tmp);
        }*/
        ///////////
        adapter.setView(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        ////////////////////////////////

      /*  arrayList.add(rootMediaItem);
        trackAdapter.addItems(rootMediaItem.getContent());*/
        return dialog_layout;
    }


}
