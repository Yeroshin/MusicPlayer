package com.ys.musicplayer.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class Settings{
    private Context context;
    private String settingsPath;
    private File settingsFile;
    private JSONObject settingsJSONObject;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    ////////////////////
    private int playlistId;
    private int currentTrack;
    private int seek_position;
    private String alarms;
    private boolean equalizerEnabled;
    private int presetPosition;
    private int[] customPreset;
    private boolean loudness_enhancer_enabled;
    private double loudness_enhancer_angle;
    private int appStartCount;
    ////////////////
    private BehaviorSubject<Integer> subjectPlaylistId;
    private BehaviorSubject<Integer> subjectCurrentTrack;
    private BehaviorSubject<Boolean> subjectEqualizerEnabled;
    private BehaviorSubject<Integer> subjectPresetPosition;
    private BehaviorSubject<int[]> subjectCustomPreset;
    ////////////////
    public void setCustomPreset(int[] customPreset){
        this.customPreset=customPreset;
        subjectCustomPreset.onNext(customPreset);
    }
    public Observable<int[]> subscribeCustomPreset(){
        return subjectCustomPreset;
    }
    ////////////////
    public void setPresetPosition(int position){
        presetPosition=position;
        subjectPresetPosition.onNext(position);
    }
    public Observable<Integer> subscribePresetPosition(){
        return subjectPresetPosition;
    }
    ////////////////
    public void setEqualizerEnabled(boolean enabled){
        equalizerEnabled=enabled;
        subjectEqualizerEnabled.onNext(enabled);
    }
    public Observable<Boolean>subscribeEqualizerEnabled(){
        return subjectEqualizerEnabled;
    }
    ////////////////
    public Settings(Context context) {
        this.context=context;
        subjectPlaylistId = BehaviorSubject.create();
        subjectCurrentTrack = BehaviorSubject.create();
        subjectEqualizerEnabled = BehaviorSubject.create();
        subjectPresetPosition = BehaviorSubject.create();
        subjectCustomPreset = BehaviorSubject.create();
        Log.d("TAG",  "Service LOADING!!!");
        init()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        ()->{},
                        throwable -> throwable.printStackTrace()
                );

    }
    ////////////////
    public void setPlaylistId(int number){
/*
        Observable<Integer> observable = Observable.create(subscriber -> {
           // TimeUnit.SECONDS.sleep(10);
            subscriber.onNext( number);
        });
        observable
                //.subscribeOn(Schedulers.io())
                .subscribe(
               // o->Log.d("TAG",  "First  : " + o);
                        data->{
                        subjectPlaylistId.onNext(data);
                    });*/
        playlistId=number;
        subjectPlaylistId.onNext(number);
    }
    public Observable<Integer> subscribePlaylistId(){
/*
        Observable<Integer> observable = Observable.create(subscriber -> {
            subscriber.onNext(playlistId);
        });
        observable
               // .subscribeOn(Schedulers.io())
                .subscribe(
                        // o->Log.d("TAG",  "First  : " + o);
                        data->subjectPlaylistId.onNext(data)
                );*/

        return subjectPlaylistId;

    }
    ////////////////
    public void setCurrentTrack(int id){
        currentTrack=id;
        subjectCurrentTrack.onNext(id);
       // subjectCurrentTrack.onComplete();//??????????
    }
    public Observable<Integer> subscribeCurrentTrack(){
        return subjectCurrentTrack;
    }
    ////////////////
    private Completable init(){

        return Completable.fromAction(()->{
           // TimeUnit.SECONDS.sleep(10);
            settingsPath=context.getFilesDir() + "/settings.txt";
            /////////////////////////////////////////////
            settingsFile = new File(settingsPath);
            try{
                if(settingsFile.createNewFile()){

                    ///////////////////////////////////////////
                    settingsJSONObject=new JSONObject();
                    settingsJSONObject.put("playlist",1);
                    settingsJSONObject.put("currentTrack",0);
                    settingsJSONObject.put("seek_position",0);
                    settingsJSONObject.put("alarms","[]");
                    settingsJSONObject.put("equalizer_enabled",false);
                    settingsJSONObject.put("presetPosition",0);
                     JSONArray jsonArray=new JSONArray(new int[]{0,0,0,0});////////CheckThis!
                    settingsJSONObject.put("customPreset",jsonArray);
                    settingsJSONObject.put("loudness_enhancer_enabled",false);
                    settingsJSONObject.put("loudness_enhancer_angle",0);
                    settingsJSONObject.put("appStartCount",0);
                    /////////////////////////////////////////////////////////////////

                    bufferedWriter = new BufferedWriter(new FileWriter(settingsFile));
                    bufferedWriter.write(settingsJSONObject.toString());
                    bufferedWriter.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            load();
        });

    }
    private void load(){
        try {
            bufferedReader = new BufferedReader(new FileReader(settingsFile));
            settingsJSONObject=new JSONObject(bufferedReader.readLine());

            seek_position=settingsJSONObject.getInt("seek_position");
            setCurrentTrack(settingsJSONObject.getInt("currentTrack"));
            setPlaylistId(settingsJSONObject.getInt("playlist"));
            alarms=settingsJSONObject.getString("alarms");
            setEqualizerEnabled(settingsJSONObject.getBoolean("equalizer_enabled"));
            setPresetPosition(settingsJSONObject.getInt("presetPosition"));
            /////////////////////////

            JSONArray jsonArray=new JSONArray(settingsJSONObject.getString("customPreset"));
            int[] customPresets=new int[jsonArray.length()];
            for(int i=0;i<jsonArray.length();i++){
                customPresets[i]=jsonArray.getInt(i);
            }
            setCustomPreset(customPresets);
            /////////////////////////
            loudness_enhancer_enabled=settingsJSONObject.getBoolean("loudness_enhancer_enabled");
            loudness_enhancer_angle=settingsJSONObject.getDouble("loudness_enhancer_angle");
            appStartCount=settingsJSONObject.getInt("appStartCount");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void save()  {
        try {
            ///////////////////////////////////////////////////////////
            settingsJSONObject.put("seek_position",seek_position);
            settingsJSONObject.put("currentTrack",currentTrack);
            settingsJSONObject.put("playlist",playlistId);
            settingsJSONObject.put("alarms",alarms);
            settingsJSONObject.put("equalizer_enabled",equalizerEnabled);
            settingsJSONObject.put("presetPosition",presetPosition);
            ///////////////////////
           /* String customPresetString="[";
            for(int i=0;i<customPreset.length;i++){
                customPresetString+=Integer.toString(customPreset[i]);
                if(i<customPreset.length-1){
                    customPresetString+=",";
                }
            }
            customPresetString+="]";*/
            settingsJSONObject.put("customPreset",new JSONArray(customPreset));
            //////////////////////
            settingsJSONObject.put("loudness_enhancer_enabled",loudness_enhancer_enabled);
            settingsJSONObject.put("loudness_enhancer_angle",loudness_enhancer_angle);
            settingsJSONObject.put("appStartCount",appStartCount);

            /////////////////////////////////////////////////////////
            bufferedWriter = new BufferedWriter(new FileWriter(settingsFile));
            bufferedWriter.write(settingsJSONObject.toString());
            bufferedWriter.close();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
