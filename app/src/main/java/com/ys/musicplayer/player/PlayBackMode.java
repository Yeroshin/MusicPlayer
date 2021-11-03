package com.ys.musicplayer.player;

import java.util.Random;

import javax.inject.Inject;

public class PlayBackMode {
    private int itemsCount;
    private int currentItem;
    private Mode mode;
    private int currentMode;
    public PlayBackMode(){

    }

    void setMode(int currentMode){
        switch (currentMode){
            case 1:
                mode = new SequentiallyMode();
            case 2:
                mode = new RandomMode();
            case 3:
                mode = new LoopMode();
        }
    }
    public int getNext(int currentItem, int itemsCount){
        this.itemsCount = itemsCount;
        this.currentItem = currentItem;
        return mode.getNext();
    }
    public int getPrevious(int currentItem, int itemsCount){
        this.itemsCount = itemsCount;
        this.currentItem = currentItem;
        return mode.getPrevious();
    }
    public void changeMode(){
        if(currentMode++>2){
            currentMode=0;
        }else {
            currentMode++;
        }
        setMode(currentMode);
    }
    interface Mode{
        int getNext();
        int getPrevious();
    }
    class SequentiallyMode implements Mode{

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
    }
    class RandomMode implements Mode{
        Random random=new Random();
        @Override
        public int getNext() {
            return random.nextInt(itemsCount);

        }

        @Override
        public int getPrevious() {
            return random.nextInt(itemsCount);
        }
    }
    class LoopMode implements Mode{
        @Override
        public int getNext() {
            return currentItem;

        }

        @Override
        public int getPrevious() {
            return currentItem;
        }
    }
}
