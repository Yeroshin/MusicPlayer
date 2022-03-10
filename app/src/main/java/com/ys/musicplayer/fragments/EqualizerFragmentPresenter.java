package com.ys.musicplayer.fragments;

import com.ys.musicplayer.audio_effects.EqualizerModel;
import com.ys.musicplayer.models.Settings;
import com.ys.musicplayer.models.SystemPlayer;

public class EqualizerFragmentPresenter {
    private EqualizerFragment equalizerFragment;
    private EqualizerModel equalizerModel;
    private Settings settings;

    public EqualizerFragmentPresenter(Settings settings, EqualizerModel equalizerModel){
        this.settings=settings;
        this.equalizerModel = equalizerModel;
        equalizerModel.subscribeEqualizerInit()
                .flatMap(
                        init->{
                            return settings.subscribeEqualizerEnabled();
                        }
                )

                .subscribe(
                        init->{

                        }
                );
    }
    private void setViewEqulizerEnabled(boolean enabled){
        if(equalizerFragment!=null){
            equalizerFragment.setEquilizerSeekbarsEnabled(enabled);
        }
    }
    public void setSeekBarsProgress(){

    }
    public void setView(EqualizerFragment equalizerFragment){
        this.equalizerFragment=equalizerFragment;
    }
    public void onEqualizerSwitchChanged(boolean isChecked){

    }
}
