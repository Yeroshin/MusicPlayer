package com.ys.musicplayer.audio_effects;

import android.content.Context;
import android.media.audiofx.AudioEffect;
import android.media.audiofx.Equalizer;
import android.widget.ArrayAdapter;

import com.ys.musicplayer.R;
import com.ys.musicplayer.models.SystemPlayer;

import java.util.ArrayList;


import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class EqualizerModel {
    public final int supported = 1;
    private Context context;
    private Equalizer equalizer;
    private boolean supports_equalizer = false;
    private ArrayList<String> equalizerPresets;
    private BehaviorSubject<ArrayList<String>> subjectEqualizerPresets;
    private BehaviorSubject<Boolean> subjectEqualizerInit;

    public EqualizerModel(Context context, SystemPlayer systemPlayer) {
        this.context = context;

        subjectEqualizerPresets = BehaviorSubject.create();
        //////////////////////////
        AudioEffect.Descriptor[] effects = AudioEffect.queryEffects();
        for (AudioEffect.Descriptor lDescriptor : effects) {
            if (AudioEffect.EFFECT_TYPE_EQUALIZER.equals(lDescriptor.type)) {
                supports_equalizer = true;
            }
        }
        ///////////////// equalizer
        if (supports_equalizer) {
            systemPlayer.subscribeAudioSessionId()
                    .subscribe(
                            id -> {
                                equalizer = new Equalizer(0, id);
                                subjectEqualizerInit.onNext(true);
                            }
                    );
        } else {
            subjectEqualizerInit.onNext(false);
        }
        //////////////////////////

    }
    public Observable subscribeEqualizerInit() {
        return subjectEqualizerInit;
    }
    public Observable subscribeEqualizerPresets() {
        return subjectEqualizerPresets;
    }

    private ArrayList<String> getPresets() {

        int NumberOfPresets = equalizer.getNumberOfPresets();
        if (NumberOfPresets > 0) {
            equalizerPresets = new ArrayList();
            equalizerPresets.add(context.getResources().getString(R.string.custom));
            for (short i = 0; i < NumberOfPresets; i++) {
                equalizerPresets.add(equalizer.getPresetName(i));
            }

          /*  presetsSpinner = equalizer_fragment_view.findViewById(R.id.presetsSpinner);
            ArrayAdapter<String> adapterPresets = new ArrayAdapter<String>(context, R.layout.item_presets_spinner, MyService.thread.equalizerPresets);
            adapterPresets.setDropDownViewResource(android.R.layout.simple_spinner_item);
            presetsSpinner.setAdapter(adapterPresets);
            SpinnerOnItemSelectedListener spinnerOnItemSelectedListener = new SpinnerOnItemSelectedListener();
            presetsSpinner.setOnItemSelectedListener(spinnerOnItemSelectedListener);
            ///////////////////set bands/////////////////////////////////////////////////////
            int frequency[] = {60000, 230000, 910000, 3000000, 14000000};
            band = new short[5];
            for (int i = 0; i < 5; i++) {
                band[i] = MyService.thread.equalizer.getBand(frequency[i]);
            }

            int range[] = MyService.thread.equalizer.getBandFreqRange(band[0]);
            level1 = MyService.thread.equalizer.getBandLevelRange();*/

        }
        return equalizerPresets;
    }


}
