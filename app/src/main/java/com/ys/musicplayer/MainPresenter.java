package com.ys.musicplayer;

import android.content.Context;

import com.ys.musicplayer.di.App;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainPresenter implements MainContract.MainPresenter{
    boolean constructed=false;
    ArrayList<MainContract.MainView> views;
    @Inject
    MainContract.Model model;
    public MainPresenter(MainContract.Model model){
        constructed=true;
        views=new ArrayList<>();
        this.model=model;

    }

    @Override
    public void onAttachView(MainContract.MainView mainView){
        this.views.add(mainView);
    }
    @Override
    public void onClickPlay() {

        for(int i=0;i<views.size();i++){
            views.get(i).setArtist(model.getPlaylist());
        }

    }
}
