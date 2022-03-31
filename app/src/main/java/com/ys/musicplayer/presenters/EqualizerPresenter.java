package com.ys.musicplayer.presenters;

import com.ys.musicplayer.fragments.IEqualizerFragment;
import com.ys.musicplayer.models.EqualizerModel;
import com.ys.musicplayer.models.Settings;

public class EqualizerPresenter {

    private IEqualizerFragment equalizerFragment;
    private EqualizerModel equalizerModel;
    private Settings settings;
    private boolean equalizerSupported;
    private boolean equalizerEnabled=false;
    private int presetPosition;

    public EqualizerPresenter(IEqualizerFragment equalizerFragment, Settings settings, EqualizerModel equalizerModel){
        this.equalizerFragment=equalizerFragment;
        this.settings=settings;
        this.equalizerModel = equalizerModel;
        ///////////////////////////
        equalizerModel.subscribeEqualizerInit()
               .filter(
                     init->{
                         return equalizerSupported=init;
                     }
                )
                .flatMap(
                        init->{
                            return settings.subscribeEqualizerEnabled();
                        }
                )
                .filter(
                        enabled->{
                            if(equalizerFragment!=null){
                                equalizerFragment.setEqualizerSwitch(enabled);
                            }
                            return equalizerEnabled=enabled;
                        }
                )
                .flatMap(
                        enabled->{
                            return settings.subscribePresetPosition();
                        }
                )
                .filter(
                        position->{
                            if(position!=0){
                                equalizerModel.setPreset(position.shortValue());
                            }
                            return position==0;
                        }
                )
                .flatMap(
                        position->{
                            return settings.subscribeCustomPreset();
                        }
                )
                .subscribe(
                        customPreset->{
                            for(int i=0;i<customPreset.length;i++){
                                equalizerModel.setBandLevel((short) i,customPreset[i]);
                            }

                        }
                );
    }

    public void onSetBandLevel(int band,int level){
        //settings.setCustomPreset();

    }
    public void onEqualizerSwitchChanged(boolean isChecked){
        settings.setEqualizerEnabled(isChecked);
    }
}
