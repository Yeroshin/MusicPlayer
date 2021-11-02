package com.ys.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ys.musicplayer.di.App;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.MainView{
    @Inject
    MainContract.MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.get(this).getInjector().inject(this);
        mainPresenter.onAttachView(this);
        mainPresenter.onClickPlay();

    }

    @Override
    public void setArtist(String text) {
      //  ((TextView)findViewById(R.id.text)).setText("text changed YEA!");
    }
}