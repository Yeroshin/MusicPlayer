package com.ys.musicplayer.dialogs;

import com.ys.musicplayer.fragments.IMediaDialogPresenter;
import com.ys.musicplayer.media.IMediaItem;
import com.ys.musicplayer.media.MediaItemFactory;
import com.ys.musicplayer.models.TrackManager;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class MediaDialogPresenter implements IMediaDialogPresenter {
    private TrackManager trackManager;
    private ArrayList<IMediaItem> items;
    private ArrayList<Boolean> selectedItems;
    private ArrayList<Boolean> activatedItems;
    private ArrayList<Boolean> checkedItems;
    private IMediaDialog mediaDialog;
    public MediaDialogPresenter(IMediaDialog mediaDialog, TrackManager trackManager, MediaItemFactory.Factory mediaItemFactory) {
        this.mediaDialog=mediaDialog;
        this.trackManager = trackManager;
        mediaItemFactory.createRootMedia().subscribeContent()
                .subscribe(
                        items->{
                            selectedItems=new ArrayList();
                            for (int i=0;i<items.size();i++){
                                selectedItems.add(false);
                            }
                            activatedItems=new ArrayList();
                            for (int i=0;i<items.size();i++){
                                activatedItems.add(false);
                            }
                            checkedItems=new ArrayList();
                            for (int i=0;i<items.size();i++){
                                checkedItems.add(false);
                            }
                            this.items=items;
                            mediaDialog.setItems(items,selectedItems,activatedItems,checkedItems);
                        }
                );




    }
    public void setData(){

    }
    @Override
    public void onAccept(){
      /*  ArrayList selectedObservables=new ArrayList();
        for(int i=0;i<adapter.selectedItems.size();i++){
            if((boolean)adapter.selectedItems.get(i)){
                selectedObservables.add((Object) ((IMediaItem)adapter.items.get(i)).getContent());
            }
        }

        Observable.concat(selectedObservables)
                .flatMapCompletable(
                        data->{
                            return trackManager.addTracks((ArrayList) data);
                        }
                )
                .subscribeOn(Schedulers.io())
                .subscribe(
                        ()->{
                            adapter.clearItems();//checkIt!
                        },//complete
                        e->{}//error

                );*/
    }

    @Override
    public void onClick(int position) {
        mediaDialog.setLoading(true);
        items.get(position).subscribeContent()
                .subscribe(
                        items->{
                            selectedItems=new ArrayList();
                            for (int i=0;i<items.size();i++){
                                selectedItems.add(false);
                            }
                            activatedItems=new ArrayList();
                            for (int i=0;i<items.size();i++){
                                activatedItems.add(false);
                            }
                            checkedItems=new ArrayList();
                            for (int i=0;i<items.size();i++){
                                checkedItems.add(false);
                            }
                            this.items=items;
                            mediaDialog.setItems(items,selectedItems,activatedItems,checkedItems);
                        },
                        e->{},
                        ()->{
                            mediaDialog.setLoading(false);
                        }
                );
    }


}
