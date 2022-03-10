package com.ys.musicplayer.utils;

import com.ys.musicplayer.models.TrackManager;

import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class PlayBackMode {
    private int itemsCount;
    private int currentItem;
    private Mode mode;
    private TrackManager trackManager;
    private BehaviorSubject subjectModeDrawable;
    public PlayBackMode(TrackManager trackManager){
        subjectModeDrawable = BehaviorSubject.create();
        mode=new SequentiallyMode();
        trackManager.subscribeTracks()
                .flatMap(
                     tracks->{
                         itemsCount=tracks.size();
                         return trackManager.subscribeCurrentTrack();
                     }
                )
                .subscribe(
                        currentTrack->{
                            currentItem=currentTrack;
                        }
                );

    }
    public Observable<Integer> subscribeModeDrawable(){
        return subjectModeDrawable;
    }
    public int getNext(){
        return mode.getNext();
    }
    public int getPrevious(){
        return mode.getPrevious();
    }
    public void changeMode(){
        mode.changeMode();
    }
    interface Mode{
        int getNext();
        int getPrevious();
        void changeMode();
    }
    class SequentiallyMode implements Mode{
        public SequentiallyMode() {
            subjectModeDrawable.onNext(1);
        }

        @Override
        public int getNext() {
            if(currentItem++<itemsCount){
                return currentItem++;
            }else{
                return 0;
            }

        }
        @Override
        public int getPrevious() {
            if(currentItem-->0){
                return currentItem--;
            }else{
                return itemsCount--;
            }
        }
        @Override
        public void changeMode() {
            mode=new RandomMode();
        }
    }
    class RandomMode implements Mode{

        private Random random;
        public RandomMode() {
            random=new Random();
            subjectModeDrawable.onNext(2);
        }

        @Override
        public int getNext() {
            return random.nextInt(itemsCount);

        }

        @Override
        public int getPrevious() {
            return random.nextInt(itemsCount);
        }

        @Override
        public void changeMode() {
            mode=new LoopMode();
        }
    }
    class LoopMode implements Mode{
        public LoopMode() {
            subjectModeDrawable.onNext(3);
        }

        @Override
        public int getNext() {
            return currentItem;

        }

        @Override
        public int getPrevious() {
            return currentItem;
        }

        @Override
        public void changeMode() {
            mode=new SequentiallyMode();
        }
    }
}
