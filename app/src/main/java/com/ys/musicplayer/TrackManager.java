package com.ys.musicplayer;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TrackManager {
    Context context;
    Dialog dialog;
    RecyclerListAdapter recyclerListAdapter;
    ImageButton ok_btn;
    ImageButton cancel_btn;
    ImageButton http_button;
    FileDialogClickListener fileDialogClickListener;
    LinearLayout dialog_layout;
    FrameLayout current_file_name_panel;
    LinearLayout files_btns_layout;

    FrameLayout http_source_layout;

    TextView current_path_string;
    TextView selected_file_name;

    RecyclerView dirlist_recyclerView;
    View disableView;




    TrackManager(Context context){
        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialog_layout =  (LinearLayout) inflater.inflate(R.layout.track_dialog_layout, null, false);//files_list
        /////////////////////
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.track_dialog_layout);

        ////////////////////////////////////////////////////////////////////////////////
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
        ///////////////////
     /*  files_list_layout.setMinimumWidth((int)(screeSize1.x* 0.9f));
        files_list_layout.setMinimumHeight((int)(screeSize1.y* 0.9f));*/
////////////////////////////////////////////////////////////////////////////////
        current_path_string = dialog.findViewById(R.id.current_path_string);
        selected_file_name = dialog.findViewById(R.id.selected_file_name);
        dirlist_recyclerView= dialog.findViewById(R.id.dirlist_recyclerView);
        disableView= dialog.findViewById(R.id.disableView);

/////////////////////////////////////

        current_file_name_panel = dialog.findViewById(R.id.current_file_name_panel);
        fileDialogClickListener=new FileDialogClickListener(callback);
        pathContentNames=new ArrayList();
        checkedItems=new ArrayList();
        //////////////////////////////rootPrepare



        //////////////////////////////////
        files_btns_layout= dialog.findViewById(R.id.files_btns_layout);
        files_btns_layout.setVisibility(View.VISIBLE);
        http_source_layout= dialog.findViewById(R.id.http_source_layout);
        http_source_layout.setVisibility(View.VISIBLE);

        http_button= dialog.findViewById(R.id.http_button);
        cancel_btn= dialog.findViewById(R.id.cancel_btn);
        ok_btn= dialog.findViewById(R.id.ok_btn);

        http_button.setOnClickListener(fileDialogClickListener);
        cancel_btn.setOnClickListener(fileDialogClickListener);
        ok_btn.setOnClickListener(fileDialogClickListener);


        /////////////////initPath


        ColorStateList csl = AppCompatResources.getColorStateList(context, R.color.color_state_list);
        ImageViewCompat.setImageTintList(ok_btn, csl);
        ImageViewCompat.setImageTintList(cancel_btn, csl);
        ImageViewCompat.setImageTintList(http_button, csl);


        http_button.setColorFilter(Color.parseColor("#070707"));
        selected_file_name.setText("");
        selected_file_name.setVisibility(View.GONE);
        disableView.setVisibility(View.GONE);



        ///////////////////////////////



        RecyclerViewClickListener recyclerViewClickListener= new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, Object position) {
                if(!http_button_state){
                    file_manage(position);
                    recyclerListAdapter.notifyDataSetChanged();
                    dirlist_recyclerView.scrollToPosition(0);
                }

            }
        };

        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerListAdapter = new RecyclerListAdapter(context,  pathContentNames,recyclerViewClickListener);

        SimpleItemTouchHelperCallback listViewCallback = new SimpleItemTouchHelperCallback(recyclerListAdapter);
        ItemTouchHelper mItemTouchHelper=new ItemTouchHelper(listViewCallback);
        mItemTouchHelper.attachToRecyclerView(dirlist_recyclerView);
        dirlist_recyclerView.setAdapter(recyclerListAdapter);
        dirlist_recyclerView.setLayoutManager(layoutManager);


        ///////////////////////////////
        // alertDialog=builder.show();
        //  WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        //  layoutParams.copyFrom(dialog.getWindow().getAttributes());
    /* lp.width = (int)(screeSize1.x* 0.9f);
        lp.height = (int)(screeSize1.y* 0.9f);*/

      /*  lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;*/
     /*  lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;*/
        //  dialog.getWindow().setLayout((int)(screeSize1.x),(int)(screeSize1.y));

        /////////////////////////
        // dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setLayout((int)(screeSize1.x* 0.92f), ViewGroup.LayoutParams.MATCH_PARENT);
        // dialog.getWindow().setLayout((int)(screeSize1.x* 0.9f), (int)(screeSize1.y* 0.9f));
        /////////////////////////

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setOnShowListener(new DialogInterface.OnShowListener(){

            @Override
            public void onShow(DialogInterface dialog) {
                rootPathInternal = Environment.getExternalStorageDirectory().toString();
                file_manage(my_initial_path);
                recyclerListAdapter.notifyDataSetChanged();
            }
        });
        dialog.setCancelable(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK&&event.getAction() == KeyEvent.ACTION_DOWN) {
                    file_manage("..");
                    recyclerListAdapter.notifyDataSetChanged();
                    dirlist_recyclerView.scrollToPosition(0);
                    return true;
                }
                return false;
            }
        });
        dialog.show();



      /*  layoutParams.width = (int)(screeSize1.x*0.9);
        layoutParams.height = (int)(screeSize1.y*0.9);*/
        // dialog.getWindow().setAttributes(layoutParams);
    }

    static class FileDialogClickListener implements View.OnClickListener{
        View view;
        static Callback callback;

        FileDialogClickListener(Callback callback){

            this.callback=callback;
        }

        ArrayList dir_list_files(File file){
            ArrayList files_array=new ArrayList();
            File[] files_list=file.listFiles();
            for(int i=0;i<files_list.length;i++){
                File item=new File(files_list[i].getPath());
                if(item.isFile()){
                    files_array.add(item);
                }else{
                    ArrayList tmp_array=dir_list_files(item);
                    for(int a=0;a<tmp_array.size();a++){
                        files_array.add(tmp_array.get(a));
                    }
                }
            }
            return files_array;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ok_btn:
                    ArrayList song_path=new ArrayList();
                    if(http_button_state){
                        song_path.add(selected_file_name.getText().toString());
                        // String path=selected_file_name.getText().toString();
                        //  MyService.add_item_to_playlist(path,MyService.thread.current_playlist);
                    }else{
                        for (int i = 0; i <checkedItems.size(); i++) {
                            if((boolean)checkedItems.get(i)){
                                if(pathContentNames.get(i).getClass() == File.class){
                                    File file=(File)pathContentNames.get(i);
                                    if(file.isFile()){
                                        song_path.add(((File)pathContentNames.get(i)).getAbsolutePath());
                                    }else{
                                        ArrayList tmp_array=dir_list_files(file);
                                        for(int x=0;x<tmp_array.size();x++){
                                            boolean supported=false;
                                            ArrayList file_types=new ArrayList();
                                            file_types.add(".mp3");
                                            file_types.add(".wav");
                                            file_types.add(".flac");


                                            String extension="";
                                            if(tmp_array.get(x).toString().lastIndexOf(".")!=-1){
                                                extension = tmp_array.get(x).toString().substring(tmp_array.get(x).toString().lastIndexOf(".")).toLowerCase();
                                            }

                                            for(int y=0;y<file_types.size();y++){
                                                if(extension.equals(file_types.get(y))){
                                                    supported=true;
                                                    break;
                                                }
                                            }
                                            ///////////////////////
                                            if(supported){
                                                song_path.add(tmp_array.get(x).toString());
                                            }

                                        }
                                    }
                                }else{
                                    ArrayList <String>song_uri=new ArrayList();
                                    for (int a = 1; a <checkedItems.size(); a++) {
                                        if((boolean)checkedItems.get(a)){
                                            if(((Media)pathContentNames.get(a)).contains!=""){
                                                Media tmpMedia=new Media();
                                                tmpMedia.contains=((Media)pathContentNames.get(a)).contains;
                                                ArrayList <Media>tmp=getMediaStore(((Media)pathContentNames.get(a)));
                                                for(int z=0;z<tmp.size();z++){
                                                    song_uri.add(tmp.get(z).uri.toString());
                                                }
                                            }else{
                                                song_uri.add(((Media)pathContentNames.get(a)).uri.toString());
                                            }

                                        }

                                    }
                                    callback.callback(song_uri);
                                    dialog.dismiss();
                                    return;
                                }

                            }



                            //  String t=dir_adapteer.getItem(i).toString();
                            //   File tmp = new File(dir_adapteer.getItem(i).toString());
                            // String path = tmp.getPath();
                            // MyService.add_item_to_playlist(path,MyService.thread.current_playlist);
                        }
                    }
                    callback.callback(song_path);
                    dialog.dismiss();
                  /*  MainActivity.playlist_adapter.notifyDataSetChanged();
                    MainActivity.save_playlist_to_file(MyService.thread.current_playlist,MyService.thread.playlist_path);
                    alertDialog.dismiss();*/
                    break;
                case R.id.cancel_btn:
                    dialog.dismiss();
                    break;
                case R.id.cancel_playlist_btn:
                    dialog.dismiss();
                    break;
              /*  case R.id.remove_playlist_btn:
                    for (int i = dir_adapteer.getCount()-1; i >-1; i--) {
                        LinearLayout ll = (LinearLayout) dirlist_view.getChildAt(i);
                        CheckBox check = ll.findViewById(R.id.checkBox);
                        boolean h = check.isChecked();
                        if (h) {
                            File tmp = new File(dir_adapteer.getItem(i).toString());
                            tmp.delete();
                            dir_adapteer.checked_items.remove(i);
                        }
                    }
                    updateFilesList();
                    break;*/
                case R.id.add_playlist_btn:
                    try {
                        for(int i=0;i<pathContentNames.size();i++){
                            if(pathContentNames.get(i).equals("..")){
                                continue;
                            }

                            if(((File)pathContentNames.get(i)).getAbsolutePath().equals(current_path+"/"+selected_file_name.getText()+ ".m3u")){
                                Toast.makeText(context,"File with such name already exists", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        // PlaylistFragment.save_playlist_to_file(MyService.thread.current_playlist, MyService.thread.settings.playlist_path);
                        File saved_file = new File(current_path, selected_file_name.getText().toString() + ".m3u");
                        saved_file.createNewFile();
                        //MyService.thread.settings.playlist_path=saved_file.getAbsolutePath();
                        pathContentNames.add(saved_file);
                        checkedItems.add(false);
                        dirlist_recyclerView.smoothScrollToPosition(pathContentNames.size());
                        recyclerListAdapter.notifyItemInserted(pathContentNames.size());
                        // recyclerListAdapter.notifyDataSetChanged();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.load_playlist_btn:
                    //PlaylistFragment.save_playlist_to_file(MyService.thread.current_playlist,MyService.thread.settings.playlist_path);
                  /*  for(int i=0;i<MyService.thread.current_playlist.size();i++){
                        PlaylistFragment.playlistAdapter.remove(0);
                    }*/

                    //  MyService.thread.current_playlist.clear();
                    boolean playlistSelected=false;
                    ArrayList playlist_song_path=new ArrayList();
                    String current_playlist="";
                    for (int i = 0; i < checkedItems.size(); i++) {
                        if((boolean)checkedItems.get(i)){
                            playlistSelected=true;
                            current_playlist=((File)pathContentNames.get(i)).getAbsolutePath();
                            try{
                                BufferedReader song_path_reader = new BufferedReader(new FileReader((File) pathContentNames.get(i)));
                                String tmp=song_path_reader.readLine();
                                while ((tmp = song_path_reader.readLine())!= null) {
                                    String path=song_path_reader.readLine();
                                    playlist_song_path.add(path);

                                    //   MyService.add_item_to_playlist(path,MyService.thread.current_playlist);
                                }
                                break;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }

                  /*  File default_playlist_file_with_path= new File(context.getFilesDir() + "/default.txt");
                    BufferedWriter bufferedWriter = null;
                    try {
                        bufferedWriter = new BufferedWriter(new FileWriter(default_playlist_file_with_path));
                        bufferedWriter.write(current_playlist);
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    if(playlistSelected){
                        MyService.thread.settings.playlist_path=current_playlist;
                        MyService.thread.current_playlist.clear();
                        dialog.dismiss();
                        callback.callback(playlist_song_path);
                    }else{
                        Toast.makeText(context,R.string.selectPlaylist, Toast.LENGTH_LONG).show();
                    }

                    break;
                case R.id.http_button:
                    http_button_state=!http_button_state;
                    if(http_button_state){
                        http_button.setColorFilter(Color.parseColor("#A3B3CE"));
                        selected_file_name.setText("http:");
                        selected_file_name.setVisibility(View.VISIBLE);
                        disableView.setVisibility(View.VISIBLE);
                        dirlist_recyclerView.suppressLayout(true);
                    }else{
                        http_button.setColorFilter(Color.parseColor("#070707"));
                        selected_file_name.setText("");
                        selected_file_name.setVisibility(View.GONE);
                        disableView.setVisibility(View.GONE);
                        dirlist_recyclerView.suppressLayout(false);
                    }


                    break;
                default:
                    break;
            }
        }
    }
    /*  static public void updateFilesList(){
          childDir = new File(current_path);
          files = childDir.listFiles();
          recyclerListAdapter.clear();
          if(!current_path.equals(initial_path)) {
              dir_adapteer.add("..");
          }
          for (int i = 0; i < files.length; i++) {
              dir_adapteer.add(files[i]);
          }
          dir_adapteer.notifyDataSetChanged();

      }*/
    public static class Media{
        Uri uri;
        String displayName;
        String title;
        String artist;
        String album;
        String genre;
        String contains;
        String containsDisplayInfo;
        boolean checkable=true;

    }

    public static ArrayList getMediaStore(Media object){

        ArrayList<Media> itemsList=new ArrayList();
        //////////////////////////




        String[] mediaProjection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.MediaColumns.DISPLAY_NAME,
        };
        String[] genresProjection = new String[]{
                MediaStore.Audio.Genres.NAME,
                MediaStore.Audio.Genres._ID
        };

        //////////////////////////
      /*  String info ="";
        String[] mediaProjection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.MediaColumns.DISPLAY_NAME,
        };
        String[] genresProjection = {
                MediaStore.Audio.Genres.NAME,
                MediaStore.Audio.Genres._ID
        };
        Cursor mediaCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                mediaProjection, null, null, null);
        int id_column_index = mediaCursor.getColumnIndex(MediaStore.Audio.Media._ID);
        if (mediaCursor.moveToFirst()) {
            do {

                int musicId = Integer.parseInt(mediaCursor.getString(id_column_index));

                Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId("external", musicId);
                Cursor genresCursor = context.getContentResolver().query(uri,
                        genresProjection, null, null, null);
                int genre_column_index = genresCursor.getColumnIndex(MediaStore.Audio.Genres.NAME);
                if (genresCursor.moveToFirst()) {
                    info += "Genres: ";
                    do {
                        info += genresCursor.getString(genre_column_index) + " ";
                    } while (genresCursor.moveToNext());
                }
            }while (mediaCursor.moveToNext());
        }*/
        //////////////////////////

        //////////////////////////
        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
        //////////////
      /*Songs
        Artists
        Albums
        Genres*/
        /////////////
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(collection,  mediaProjection, null, null, null);
        int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        //     current_path=current_path.concat(actions[1]);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Media item=new Media();
                ///////////
                item.uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursor.getLong(idColumn));
                item.title =cursor.getString( cursor.getColumnIndex(MediaStore.MediaColumns.TITLE));
                item.displayName =cursor.getString( cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
                item.artist =cursor.getString( cursor.getColumnIndex(MediaStore.MediaColumns.ARTIST));
                item.album =cursor.getString( cursor.getColumnIndex(MediaStore.MediaColumns.ALBUM));

                int musicId = cursor.getInt(idColumn);
                Uri uriForAudioId = MediaStore.Audio.Genres.getContentUriForAudioId("external", musicId);
                Cursor genresCursor = context.getContentResolver().query(uriForAudioId, genresProjection, null, null, null);
                int genre_column_index = genresCursor.getColumnIndex(MediaStore.Audio.Genres.NAME);
                if (genresCursor.moveToFirst()) {
                    do {
                        item.genre= genresCursor.getString(genre_column_index);
                    } while (genresCursor.moveToNext());
                }
                boolean exist=false;

                switch (object.contains) {
                    case "Artists":
                        if (item.artist != null) {
                            for (int i = 0; i < itemsList.size(); i++) {
                                if (itemsList.get(i).artist.equals(item.artist)) {
                                    exist = true;
                                }
                            }
                            if (!exist) {
                                item.contains= "artistsSongs";
                                item.containsDisplayInfo= "Artist:"+item.artist;
                                item.displayName = item.artist;
                                itemsList.add(item);

                            }
                        }
                        break;
                    case "Albums":
                        if (item.album != null) {
                            for (int i = 0; i < itemsList.size(); i++) {
                                if ( itemsList.get(i).album.equals(item.album)) {
                                    exist = true;
                                }
                            }
                            if (!exist) {
                                item.contains= "albumSongs";
                                item.containsDisplayInfo= "Album:"+item.album;
                                item.displayName = item.album;
                                itemsList.add(item);

                            }
                        }
                        break;
                    case "Genres":
                        if (item.genre != null) {
                            for (int i = 0; i < itemsList.size(); i++) {
                                if ((itemsList.get(i)).genre.equals(item.genre)) {
                                    exist = true;
                                }
                            }
                            if (!exist) {
                                item.contains= "genreSongs";
                                item.containsDisplayInfo= "Genre:"+item.genre;
                                item.displayName = item.genre;
                                itemsList.add(item);

                            }
                        }
                        break;
                    case "artistsSongs":
                        if (item.artist!=null&&item.artist.equals(object.artist)) {
                            item.contains= "";
                            itemsList.add(item);

                        }
                        break;
                    case "albumSongs":
                        if (item.album!=null&&item.album.equals(object.album)) {
                            item.contains= "";
                            itemsList.add(item);

                        }
                        break;
                    case "genreSongs":
                        if (item.genre!=null&&item.genre.equals(object.genre)) {
                            item.contains= "";
                            itemsList.add(item);

                        }
                        break;
                    case "Songs":
                        item.contains= "";
                        itemsList.add(item);

                        break;
                    default:
                        break;
                }


            } while (cursor.moveToNext());

        }

        return itemsList;
    }
    public void file_manage(Object object){
        String new_current_path="";
        if(object.getClass()==Media.class){
            if(((Media)object).contains.equals("")){
                return;
            }
            current_path=current_path.concat("<>"+((Media)object).contains+"</>");
            pathContentNames.clear();
            checkedItems.clear();
            pathContentNames.add("..");
            checkedItems=new ArrayList();
            checkedItems.add(false);
            ArrayList<Media> tmp=getMediaStore((Media)object);
            for(int i=0;i<tmp.size();i++){
                pathContentNames.add(tmp.get(i));
                checkedItems.add(false);
            }
            current_path_string.setText(((Media)object).containsDisplayInfo);
            return;
        }else if(object.getClass()==File.class){
            new_current_path=((File)object).getAbsolutePath();
        }else if(object.getClass()==String.class){
            new_current_path=(String)object;
        }
        String[] command=new_current_path.split("/");

        File dir= new File(new_current_path);
       /* if(!dir.exists()){
           pathContentNames.clear();
            checkedItems.clear();
            if(!new_currrent_path.equals(initial_path)){
                pathContentNames.add("..");
            }
            for(int i=0;i<pathContentNames.size();i++){
                checkedItems.add(i,false);
            }
        }*/
        if(dir.exists()&&!dir.isDirectory()){

            return;
        }



        if(new_current_path.equals("..")){
            if(current_path.lastIndexOf("<>")>0){
                current_path=current_path.substring(0,current_path.lastIndexOf("<>"));
                if(current_path.lastIndexOf("<>")>0){
                    Media tmp=new Media();
                    tmp.contains=current_path.substring(current_path.lastIndexOf("<>")+2,current_path.lastIndexOf("</>"));
                    tmp.displayName=current_path.substring(current_path.lastIndexOf("<>")+2,current_path.lastIndexOf("</>"));
                    pathContentNames.clear();
                    checkedItems.clear();
                    pathContentNames.add("..");
                    checkedItems=new ArrayList();
                    checkedItems.add(false);
                    ArrayList<Media> tmp1=getMediaStore(tmp);
                    for(int i=0;i<tmp1.size();i++){
                        pathContentNames.add(tmp1.get(i));
                        checkedItems.add(false);
                    }
                    return;
                }
            }else{
                if(pathContentNames.get(0).equals("..")){
                    current_path=current_path.substring(0,current_path.lastIndexOf("/"));
                }else{
                    return;
                }

            }
            dir= new File(current_path);
        }else if(new_current_path.lastIndexOf("/")!=-1){
            current_path = new_current_path;
        }else{
            return;
        }


        File[] files = dir.listFiles();///errror!api10
        if(files==null&((rootFileInternal!=null&&!current_path.equals(rootFileInternal.getParentFile().getAbsolutePath()))&(rootFileSDcard!=null&&!current_path.equals(rootFileSDcard.getParentFile().getAbsolutePath())))){
            files=new File[0];
            String access_restricted_string = context.getResources().getString(R.string.access_restricted);
            Toast.makeText(context, access_restricted_string+dir.getName(), Toast.LENGTH_LONG).show();
            //return;
        }
        pathContentNames.clear();
        checkedItems.clear();
       /* boolean a=rootFileInternal!=null;
        boolean b=!current_path.equals(rootFileInternal.getParentFile().getAbsolutePath());
        boolean c=rootFileSDcard!=null;
        boolean d=!current_path.equals(rootFileSDcard.getParentFile().getAbsolutePath());*/
        boolean var=false;
        boolean var2=false;
        if(rootFileInternal!=null){
            if(!current_path.equals(rootFileInternal.getParentFile().getAbsolutePath())){
                var=true;
            }
        }else{
            var=true;
        }
        if(rootFileSDcard!=null){
            if(!current_path.equals(rootFileSDcard.getParentFile().getAbsolutePath())){
                var2=true;
            }
        }else{
            var2=true;
        }
        //if((rootFileInternal!=null&&!current_path.equals(rootFileInternal.getParentFile().getAbsolutePath()))|(rootFileSDcard!=null&&!current_path.equals(rootFileSDcard.getParentFile().getAbsolutePath()))){
        if(var&var2){
            if(type!=options_type){
                pathContentNames.add("..");
            }

        }else{
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
                if(rootFileInternal!=null){
                    pathContentNames.add(rootFileInternal);
                }
                if(rootFileSDcard!=null){
                    pathContentNames.add(rootFileSDcard);
                }
            }

            Media tmpSongs=new Media();
            tmpSongs.checkable=true;
            tmpSongs.contains="Songs";
            tmpSongs.containsDisplayInfo="Songs";
            tmpSongs.displayName="Songs";
            pathContentNames.add(tmpSongs);

            String[] media={"Artists","Albums","Genres"};
            for(int i=0;i<media.length;i++){
                Media tmp=new Media();
                tmp.checkable=false;
                tmp.contains=media[i];
                tmp.containsDisplayInfo=media[i];
                tmp.displayName=media[i];
                pathContentNames.add(tmp);
            }
          /*  pathContentNames.add("MediaStore/Songs");
            pathContentNames.add("MediaStore/Artists");
            pathContentNames.add("MediaStore/Albums");
            pathContentNames.add("MediaStore/Genres");*/

            current_path_string.setSelected(true);

            checkedItems=new ArrayList();
            for(int i=0;i<pathContentNames.size();i++){
                checkedItems.add(i,false);
            }
            current_path_string.setText("");

            return;
        }



        for (int i = 0; i < files.length; i++) {
            if(!files[i].isHidden()){
                pathContentNames.add(files[i]);
            }

        }
        ////////////////sort
        Collections.sort(pathContentNames, new FileNameComparator());
        ////////////////////////////

        current_path_string.setText(current_path);
        current_path_string.setSelected(true);

        checkedItems=new ArrayList();
        for(int i=0;i<pathContentNames.size();i++){
            checkedItems.add(i,false);
        }
       /* dir_adapteer = new File_adapteer(context, R.layout.item, pathContentNames);
        dirlist_view.setAdapter(dir_adapteer);
        dirlist_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                TextView selectedItem = view.findViewById(R.id.item_checked);
                String selectedItemString = (String) selectedItem.getText();

                if (!selectedItemString.equals("..")) {
                    childDir = new File(current_path, selectedItemString);
                    if (childDir.isDirectory()) {
                        files = childDir.listFiles();
                        current_path=childDir.toString();
                        dir_adapteer.clear();
                        dir_adapteer.checked_items.clear();
                        dir_adapteer.add("..");
                        dir_adapteer.checked_items.add(0,false);

                        for (int i = 0; i < files.length; i++) {
                            dir_adapteer.add(files[i]);

                            dir_adapteer.checked_items.add(i,false);
                        }
                        dir_adapteer.notifyDataSetChanged();
                    }
                    else{
                        selected_file_name.setText(selectedItemString);
                    }
                } else {
                    childDir = new File(current_path).getParentFile();
                    current_path = childDir.toString();
                    files = childDir.listFiles();
                    dir_adapteer.clear();
                    dir_adapteer.checked_items.clear();
                    if (!current_path.equals(initial_path)) {
                        dir_adapteer.add("..");
                        dir_adapteer.checked_items.add(0,false);
                    }

                    for (int i = 0; i < files.length; i++) {
                        dir_adapteer.add(files[i]);

                        dir_adapteer.checked_items.add(i,false);
                    }
                    dir_adapteer.notifyDataSetChanged();
                }
                current_path_string.setText(current_path);
            }
        });*/

    }
    public class FileNameComparator implements Comparator<Object>
    {
        public int compare(Object left,Object right) {
            Class leftClass=left.getClass();
            Class rightClass=right.getClass();

            if(leftClass==File.class&rightClass==File.class){
                if(((File)left).isDirectory()&((File)right).isDirectory()){
                    return ((File)left).getName().toLowerCase().compareTo(((File)right).getName().toLowerCase());
                }else if(((File)left).isDirectory()){
                    return -1;
                }else if(((File)right).isDirectory()){
                    return 1;
                }else{
                    return ((File)left).getName().toLowerCase().compareTo(((File)right).getName().toLowerCase());
                }

            }else{
                if(leftClass!=File.class){
                    return -1;
                }else{
                    return 1;
                }
            }
        }
    }
    class File_adapteer extends ArrayAdapter {
        LayoutInflater inflater;
        int resource;
        ArrayList checked_items;

        File_adapteer(Context context, int resource, ArrayList<String> objects) {

            super(context, resource, objects);
            this.resource = resource;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            checked_items=new ArrayList();
            for(int i=0;i<objects.size();i++){
                checked_items.add(i,false);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            view = inflater.inflate(R.layout.item, parent, false);
            TextView text = view.findViewById(R.id.item_checked);
            // LinearLayout layoutview = (LinearLayout) view;
            CheckBox checkBox = view.findViewById(R.id.checkBox);
            ImageView icon=view.findViewById(R.id.icon);
            final Object item = getItem(position);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checked_items.set(position,checkBox.isChecked());
                }
            });

            checkBox.setChecked((boolean)checked_items.get(position));

            if (item.getClass() == File.class) {
                File itm = (File) item;
                if (itm.isDirectory() == true) {
                    //layoutview.removeView(checkBox);
                    icon.setImageResource(R.drawable.folder);
                    //checkBox.setVisibility(View.INVISIBLE);
                    text.setText(itm.getName());
                } else {
                /* checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                         buttonView.toggle();
                     }
                 });*/
                    boolean supported=false;
                    ArrayList file_types=new ArrayList();
                    if(type==add_song_to_playlist_type){
                        file_types.add(".mp3");
                        file_types.add(".wav");
                        file_types.add(".flac");
                    }else{
                        file_types.add(".m3u");
                    }

                    //{".mp3",".wav",".flac"};
                    String extension="";
                    if(itm.getName().lastIndexOf(".")!=-1){
                        extension = itm.getName().substring(itm.getName().lastIndexOf(".")).toLowerCase();
                    }

                    for(int i=0;i<file_types.size();i++){
                        if(extension.equals(file_types.get(i))){
                            supported=true;
                            break;
                        }
                    }
                    if(supported){
                        icon.setImageResource(R.drawable.file_music);
                    }else{
                        checkBox.setVisibility(View.INVISIBLE);
                        icon.setImageResource(R.drawable.file);
                    }
                    //text.setTextColor(Color.parseColor("#00ff00"));
                    //checkBox.setChecked(true);
                    text.setText(itm.getName());
                }
                // text.setText(item.getClass().getName());
            } else {
                String itm = (String) item;
                if(itm.equals("..")){
                    icon.setImageResource(R.drawable.subdirectory_arrow_right);
                }else{
                    icon.setVisibility(View.GONE);
                }

                checkBox.setVisibility(View.INVISIBLE);//gone
                //layoutview.removeView(checkBox);
                text = view.findViewById(R.id.item_checked);
                text.setText(itm);
                //text.setTextColor(Color.parseColor("#0000ff"));
            }

            return view;
        }
    }
    public interface ItemTouchHelperAdapter {
        boolean onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
    }
    public interface ItemTouchHelperViewHolder {
        void onItemSelected();
        void onItemClear();
    }
    public interface RecyclerViewClickListener {

        void onClick(View view, Object position);
    }
    class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback  {
        private final ItemTouchHelperAdapter mAdapter;
        public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = 0;
            int swipeFlags = 0;
            if(((RecyclerListAdapter.ItemViewHolder)viewHolder).removable){
                swipeFlags = ItemTouchHelper.END;
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
        }
        @Override
        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
            super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            View itemView = viewHolder.itemView;

            int itemHeight = itemView.getBottom() -  itemView.getTop();
            Drawable icon;
            ///////////////////
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                icon = VectorDrawableCompat.create(context.getResources(), R.drawable.trash_can_outline, null);
            } else {
                icon = ContextCompat.getDrawable(context, R.drawable.trash_can_outline);
            }
            ////////////////////
            int color = context.getResources().getColor(R.color.blue);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                icon.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
            } else {
                icon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
            icon.setBounds(itemHeight/4, itemView.getTop()+itemHeight/4, itemHeight/4*3,  itemView.getBottom()-itemHeight/4);
            icon.draw(canvas);
        }


    }
    class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>implements ItemTouchHelperAdapter {
        ArrayList pathContentNames;

        private RecyclerViewClickListener mListener;
        View view;
        LayoutInflater layoutInflater;
        int lastChecked=0;
        public RecyclerListAdapter(Context context, ArrayList dataSet,RecyclerViewClickListener listener) {
            pathContentNames = dataSet;

            mListener = listener;
            layoutInflater = LayoutInflater.from(context);

        }
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            view = layoutInflater.inflate(R.layout.item, parent, false);
            RecyclerListAdapter.ItemViewHolder viewHolder = new RecyclerListAdapter.ItemViewHolder(view,mListener);
            ////////////////////////////
           /* final ViewGroup.LayoutParams lp = view.getLayoutParams();
            lp.width = parent.getWidth();
           // lp.height = parent.getHeight();
            view.setLayoutParams(lp);*/

            //////////////////////////////
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerListAdapter.ItemViewHolder holder, int position) {
           /* holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checked_items.set(position,checkBox.isChecked());
                }
            });*/
            holder.checkBox.setChecked((boolean)checkedItems.get(position));
            holder.removable=false;
            //  holder.checkBox.setChecked((boolean)checkedItems.get(position));
            if (pathContentNames.get(position).getClass() == File.class) {
                holder.text.setGravity(Gravity.LEFT);
                holder.text.setTextColor(context.getResources().getColor(R.color.text_gray));
                holder.icon.setVisibility(View.VISIBLE);
                File itm = (File) pathContentNames.get(position);
                if (itm.isDirectory() == true) {
                    //layoutview.removeView(checkBox);
                    if(rootFileInternal!=null&&itm.getAbsolutePath().equals(rootFileInternal.getAbsolutePath())){
                        holder.text.setText(R.string.InternalStorage);
                        holder.icon.setImageResource(R.drawable.cellphone_arrow_down);
                    }else if(rootFileSDcard!=null&&itm.getAbsolutePath().equals(rootFileSDcard.getAbsolutePath())){
                        holder.text.setText(R.string.SDCard);
                        holder.icon.setImageResource(R.drawable.sd);
                    }else{
                        holder.icon.setImageResource(R.drawable.folder);
                        holder.text.setText(itm.getName());
                    }
                    if(type==add_song_to_playlist_type){
                        holder.checkBox.setVisibility(View.VISIBLE);
                    }else{
                        holder.checkBox.setVisibility(View.INVISIBLE);
                    }


                } else {
                /* checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                         buttonView.toggle();
                     }
                 });*/
                    boolean supported=false;
                    ArrayList file_types=new ArrayList();
                    if(type==add_song_to_playlist_type){
                        file_types.add(".mp3");
                        file_types.add(".wav");
                        file_types.add(".flac");

                    }else{
                        file_types.add(".m3u");

                    }

                    //{".mp3",".wav",".flac"};
                    String extension="";
                    if(itm.getName().lastIndexOf(".")!=-1){
                        extension = itm.getName().substring(itm.getName().lastIndexOf(".")).toLowerCase();
                    }

                    for(int i=0;i<file_types.size();i++){
                        if(extension.equals(file_types.get(i))){
                            supported=true;
                            break;
                        }
                    }
                    if(supported){
                        if(extension.equals(".m3u")){
                            holder.removable=true;
                            holder.icon.setImageResource(R.drawable.ic_m3u_file_format_variant);
                        }else{
                            holder.icon.setImageResource(R.drawable.file_music);
                        }

                        holder.checkBox.setVisibility(View.VISIBLE);
                        /////avoiding to delete current playlist
                        if(MyService.thread.settings.playlist_path.equals(itm.getAbsolutePath())){
                            holder.removable=false;
                            holder.checkBox.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        holder.checkBox.setVisibility(View.INVISIBLE);
                        holder.icon.setImageResource(R.drawable.file);
                    }
                    //text.setTextColor(Color.parseColor("#00ff00"));
                    //checkBox.setChecked(true);
                    holder.text.setText(itm.getName());
                }
                // text.setText(item.getClass().getName());
            }else if(pathContentNames.get(position).getClass() == Media.class){
                Media item = (Media) pathContentNames.get(position);
                if(item.contains!=""){
                    holder.icon.setImageResource(R.drawable.folder);
                }else{
                    holder.icon.setImageResource(R.drawable.file_music);
                }
                if(item.checkable){
                    holder.checkBox.setVisibility(View.VISIBLE);
                }else{
                    holder.checkBox.setVisibility(View.GONE);
                }

                holder.icon.setVisibility(View.VISIBLE);
                holder.text.setText(item.displayName);
                holder.text.setGravity(Gravity.LEFT);
                holder.text.setTextColor(context.getResources().getColor(R.color.text_gray));
            } else {
                String itm = (String) pathContentNames.get(position);
                if(itm.equals("..")){
                    holder.icon.setImageResource(R.drawable.subdirectory_arrow_right);
                    holder.text.setText(itm);
                    holder.text.setGravity(Gravity.LEFT);
                    holder.text.setTextColor(context.getResources().getColor(R.color.text_gray));
                }else{
                    holder.icon.setVisibility(View.GONE);
                    holder.text.setText(itm.substring(11));
                    holder.text.setGravity(Gravity.CENTER_HORIZONTAL);
                    holder.text.setTextColor(context.getResources().getColor(R.color.blue));

                }

                holder.checkBox.setVisibility(View.INVISIBLE);//gone
                //layoutview.removeView(checkBox);
                //  text = view.findViewById(R.id.item_checked);

                //text.setTextColor(Color.parseColor("#0000ff"));
            }
          /*  holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        switch (type){
                            case add_song_to_playlist_type:

                                    checkedItems.set(position,isChecked);
                                break;
                            case  options_type:
                                if(isChecked){
                                    checkedItems.set(lastChecked,false);
                                    notifyItemChanged(lastChecked);
                                    lastChecked=position;
                                    checkedItems.set(position,true);

                                }
                              //  checkedItems.set(lastChecked,false);
                                //recyclerListAdapter.notifyItemChanged(lastChecked);


                                break;
                            default:
                                break;
                        }
                    }
                });*/

        }

        @Override
        public int getItemCount() {

            return pathContentNames.size();
        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            return false;
        }

        @Override
        public void onItemDismiss(int position) {
            File tmp = new File(pathContentNames.get(position).toString());
            tmp.delete();
            pathContentNames.remove(position);
            checkedItems.remove(position);
            recyclerListAdapter.notifyItemRemoved(position);
            //  recyclerListAdapter.notifyDataSetChanged();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView text;
            CheckBox checkBox;
            ImageView icon;
            boolean removable=false;

            private RecyclerViewClickListener mListener;
            public ItemViewHolder(@NonNull View view,RecyclerViewClickListener mListener) {
                super(view);

                this.mListener=mListener;
                text = view.findViewById(R.id.item_checked);
                checkBox = view.findViewById(R.id.checkBox);
                icon=view.findViewById(R.id.icon);
                view.setOnClickListener(this);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        switch (type){
                            case add_song_to_playlist_type:

                                checkedItems.set(getAdapterPosition(),isChecked);
                                break;
                            case  options_type:
                                if(isChecked&lastChecked!=getAdapterPosition()){
                                    checkedItems.set(lastChecked,false);
                                    notifyItemChanged(lastChecked);
                                    lastChecked=getAdapterPosition();
                                    checkedItems.set(getAdapterPosition(),true);

                                }
                                //  checkedItems.set(lastChecked,false);
                                //recyclerListAdapter.notifyItemChanged(lastChecked);


                                break;
                            default:
                                break;
                        }
                    }
                });
              /*  checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        switch (type){
                            case add_song_to_playlist_type:

                                    checkedItems.set(getAdapterPosition(),isChecked);
                                break;
                            case  options_type:
                                if(isChecked){
                                    checkedItems.set(lastChecked,false);
                                    lastChecked=getAdapterPosition();
                                    checkedItems.set(getAdapterPosition(),true);
                                }else{

                                }
                              //  checkedItems.set(lastChecked,false);
                                //recyclerListAdapter.notifyItemChanged(lastChecked);
                              /*  lastChecked=getAdapterPosition();
                                checkedItems.set(getAdapterPosition(),isChecked);*/

                            /*    break;
                            default:
                                break;
                        }



                    }
                });*/
            }

            @Override
            public void onClick(View v) {

                mListener.onClick(v, pathContentNames.get(getAdapterPosition()));
            }
        }
    }
}
