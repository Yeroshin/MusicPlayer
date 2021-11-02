package com.ys.musicplayer.di;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.ys.musicplayer.db.AppDatabase;
import com.ys.musicplayer.di.components.AppComponent;
import com.ys.musicplayer.di.components.DaggerAppComponent;
import com.ys.musicplayer.di.modules.AppModule;

public class App extends Application {
    private AppComponent appComponent;
    private AppDatabase database;

    public AppComponent getInjector(){
        if(appComponent==null){
            appComponent= DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
            database = Room.databaseBuilder(this, AppDatabase.class, "YSDatabase")
                  //  .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appComponent;

    }
    public static App get(Context context) {
        return (App)context.getApplicationContext();
    }
    public AppDatabase getDatabase() {
        return database;
    }
}
