package com.ys.musicplayer.di.modules;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ys.musicplayer.R;
import com.ys.musicplayer.db.AppDatabase;
import com.ys.musicplayer.db.PlaylistDAO;
import com.ys.musicplayer.models.Settings;
import com.ys.musicplayer.models.TrackManager;
import com.ys.musicplayer.utils.PlayBackMode;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppModule.class)
public class ModelModule {
   /* Context context;
    public ModelModule(Context context) {
        this.context=context;
    }*/

    @Singleton
    @Provides
    PlayBackMode providePlayBackMode(TrackManager trackManager){
        return new PlayBackMode(trackManager);
    }

    @Singleton
    @Provides
    AppDatabase provideDatabase (Context context){
        String defPlaylistName=context.getResources().getString(R.string.def);
        return  Room.databaseBuilder(context, AppDatabase.class, "YSDatabase")
                //  .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        db.execSQL("INSERT INTO playlist(name) VALUES('"+defPlaylistName+"');");
                    }
                })
                .build();
    };
    @Singleton
    @Provides
    PlaylistDAO providePlaylistDAO (AppDatabase appDatabase){
        return  appDatabase.playlistDao();
    };
    @Singleton
    @Provides
    Settings provideSettings(Context context){
        return new Settings(context);
    };
    @Singleton
    @Provides
    TrackManager provideTrackManager(Settings settings, PlaylistDAO playlistDAO){
        return new TrackManager(settings,playlistDAO);
    }
}
