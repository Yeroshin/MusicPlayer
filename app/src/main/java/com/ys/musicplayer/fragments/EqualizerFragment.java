package com.ys.musicplayer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ys.musicplayer.R;
import com.ys.musicplayer.custom_views.RotaryKnob;
import com.ys.musicplayer.custom_views.VerticalSeekBar;
import com.ys.musicplayer.presenters.IEqualizerPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

public class EqualizerFragment extends Fragment implements AdapterView.OnItemSelectedListener,IEqualizerFragment{

    private Context context;//
    @Inject
    public IEqualizerPresenter equalizerFragmentPresenter;
    private View equalizer_fragment_view;
    private short level[];
    private LinearLayout equalizers_panel;
    private ArrayList<VerticalSeekBar> seekbars;
    private ArrayList<TextView> eq_value;
    private short bandLevels[]=new short[5];

    private VerticalSeekBar seekBar60hz;
    private VerticalSeekBar seekBar230hz;
    private VerticalSeekBar seekBar910hz;
    private VerticalSeekBar seekBar3khz;
    private VerticalSeekBar seekBar14khz;

    private TextView eq_value_1;
    private TextView eq_value_2;
    private TextView eq_value_3;
    private TextView eq_value_4;
    private TextView eq_value_5;

    private Switch equalizerSwitch, loudnesSwitch;

    private RotaryKnob loudness_enhancer_knob;
    private Spinner presetsSpinner;
    private ArrayAdapter<String> adapterPresets;
    // Equalizer.Settings settings;
    // RotaryKnob bass_boost_knob;
    public EqualizerFragment(){

    }
    public void showPresetsAdapter(ArrayList<String>equalizerPresets){

        adapterPresets = new ArrayAdapter(context, R.layout.item_presets_spinner, equalizerPresets);
        adapterPresets.setDropDownViewResource(android.R.layout.simple_spinner_item);
        presetsSpinner.setAdapter(adapterPresets);
        presetsSpinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;

        //equalizerFragmentPresenter.setView(this);


    }
    public void setSeekbars(){
        /////////////////////////////////setSeekbars

    }
    void equalizerInit(){



            ////////////////set seekbars/////////////////////////////////////////////////////////

           // presetsSpinner.setSelection(MyService.thread.settings.presetPosition);
            /////////////////////////////////////////////////////////////////////////
    }
    @Override
    public void setEqualizerSeekbarsValue(){

    }


    @Override
    public void setEqualizerSwitch(boolean isEnabled){

        equalizerSwitch.setEnabled(isEnabled);
      /*  MyService.thread.equalizer.setEnabled(isChecked);
        MyService.thread.settings.equalizer_enabled=isChecked;*/
    }

    public void showError(){
        Toast.makeText(context,R.string.notSupported, Toast.LENGTH_LONG).show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //////////////////
        equalizer_fragment_view=inflater.inflate(R.layout.fragment_equalizer, container, false);
        ////////////////////////////////
        loudness_enhancer_knob=equalizer_fragment_view.findViewById(R.id.loudness_enhancer_knob);
        presetsSpinner = equalizer_fragment_view.findViewById(R.id.presetsSpinner);
        //////////////////////
        /*Drawable knob = getResources().getDrawable(R.drawable.rotaryknob);
        loudness_enhancer_knob.setKnob(knob);*/
        ////////////////////////////////SwitchBars
        equalizerSwitch=equalizer_fragment_view.findViewById(R.id.equalizerSwitch);

        equalizerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(int i=0;i<seekbars.size();i++){
                    seekbars.get(i).setEnabled(isChecked);
                }

                equalizerFragmentPresenter.onEqualizerSwitchChanged(isChecked);
            }
        });

        ///////////////////////////////////loudnes
      /*  loudnesSwitch=equalizer_fragment_view.findViewById(R.id.loudnesSwitch);
        loudnesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(MyService.thread.supports_loudnessEnhancer){
                    if(isChecked){
                        MyService.thread.loudnessEnhancer.setEnabled(true);
                        double angle=-(loudness_enhancer_knob.knobAngle-loudness_enhancer_knob.MAX);
                        MyService.thread.loudnessEnhancer.setTargetGain((int) (angle*MyService.thread.MaxLoud/loudness_enhancer_knob.MAX));
                        loudness_enhancer_knob.setOnRotaryKnobChangeListener(new RotaryKnob.OnRotaryKnobChangeListener() {
                            @Override
                            public void onProgress(RotaryKnob rotaryKnob) {
                                double angle=-(rotaryKnob.knobAngle-rotaryKnob.MAX);
                                MyService.thread.settings.loudness_enhancer_angle=angle;
                                MyService.thread.loudnessEnhancer.setTargetGain((int) (angle*MyService.thread.MaxLoud/rotaryKnob.MAX));
                            }

                            @Override
                            public void onStopTrackingTouch(RotaryKnob rotaryKnob) {

                            }
                        });
                        loudness_enhancer_knob.enabled=true;
                        loudness_enhancer_knob.invalidate();
                    }else{
                        loudness_enhancer_knob.removeOnRotaryKnobChangeListener();
                        MyService.thread.loudnessEnhancer.setEnabled(false);
                        loudness_enhancer_knob.enabled=false;
                        loudness_enhancer_knob.invalidate();
                    }
                    MyService.thread.settings.loudness_enhancer_enabled=isChecked;
                }else{
                    Toast.makeText(context,"Not supported", Toast.LENGTH_LONG).show();
                    //  loudness_enhancer_knob.invalidate();
                    // int a=0;
                }
            }
        });
        loudnesSwitch.setChecked(MyService.thread.settings.loudness_enhancer_enabled);
        loudness_enhancer_knob.knobAngle=loudness_enhancer_knob.MAX-MyService.thread.settings.loudness_enhancer_angle;
        loudness_enhancer_knob.invalidate();*/
        ////////////////////////////////equalizers
        equalizers_panel=equalizer_fragment_view.findViewById(R.id.equalizers_panel);


        String eq_names[]={"60Hz","230Hz","910Hz","3kHz","14kHz"};
        ////////////setSeekbars
        seekbars=new ArrayList<>();
        for(int i=0;i<5;i++){
            seekbars.add(equalizers_panel.getChildAt(i).findViewById(R.id.SeekBar));
            seekbars.get(i).setEnabled(false);
            seekbars.get(i).setTag(i);
        }

        eq_value=new ArrayList<>();
        for(int i=0;i<5;i++){
            eq_value.add(equalizers_panel.getChildAt(i).findViewById(R.id.eq_value));
            eq_value.get(i).setText(String.valueOf(seekbars.get(i).getProgress()-100));
        }
        for(int i=0;i<5;i++){
            ((TextView)equalizers_panel.getChildAt(i).findViewById(R.id.eq_name)).setText(eq_names[i]);
        }
        ///////////////////////////////////
        for(int i=0;i<5;i++){
           // seekbars.get(i).setMax(level[1]-level[0]);
            seekbars.get(i).setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgress(VerticalSeekBar seekBar) {
                    ((TextView)((LinearLayout)seekBar.getParent()).findViewById(R.id.eq_value)).setText(String.valueOf(seekBar.getProgress()-100));
                   equalizerFragmentPresenter.onSetBandLevel((int)seekBar.getTag(),seekBar.getProgress());
                    for(int i=0;i<5;i++) {

                         bandLevels[i]=(short) seekbars.get(i).getProgress();
                    }
                    /* int tag=(int)seekBar.getTag();
                    eq_value[tag].setText( String.valueOf((seekBar.getProgress()-seekBar.getMax()/2)/100));*/
                }
                @Override
                public void onStopTrackingTouch(VerticalSeekBar seekBar) {
                   /* if(presetsSpinner.getSelectedItemPosition()!=0){
                        for(int i=0;i<5;i++){

                            customPreset[i]=seekbars[i].getProgress();
                            //seekbars[i].setValue(MyService.thread.settings.customPreset[i]);
                            MyService.thread.equalizer.setBandLevel(band[i],(short)(level1[0]+seekbars[i].getProgress()));
                            presetsSpinner.setSelection(0);
                            MyService.thread.settings.presetPosition=0;
                        }
                    }else{
                        int progress=seekBar.getProgress();
                        int tag=(int)seekBar.getTag();
                        MyService.thread.settings.customPreset[tag]=progress;
                        MyService.thread.equalizer.setBandLevel(band[tag],(short)(level1[0]+seekbars[tag].getProgress()));
                    }*/

                    //MyService.thread.equalizer.setBandLevel(band[tag],(short)(level1[0]+progress));
                }
            });
        }
        ////////////////////////////////////
        //equalizer_fragment_view.findViewById(R.id.seekBar60hz).findViewById(R.id.eq_name).setText();
        //seekbars.get(0).getBackground().setLevel(2000);

        /////////////////////////
       /* seekbars=new VerticalSeekBar[5];
        eq_value=new TextView[5];
        View child[]=new View[5];*/
       /* for(int i=0;i<5;i++){
            child[i] = (LinearLayout)inf.inflate(R.layout.seekbar_vertical, null);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            child[i] .setLayoutParams(param);

            equalizers_panel.addView(child[i]);
            seekbars[i]=child[i].findViewById(R.id.SeekBar);


            ///////////////////////
            seekbars[i].getProgressDrawable().setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN);
            seekbars[i].enabled=MyService.thread.settings.equalizer_enabled;
            seekbars[i].setTag(i);
            seekbars[i].initValue=MyService.thread.settings.customPreset[i];

            eq_value[i]=child[i].findViewById(R.id.eq_value);
            eq_value[i].setText( String.valueOf((seekbars[i].getProgress()-seekbars[i].getMax()/2)/100));
            //eq_value[i].setText("0");
            eq_value[i].setTag(i);

            TextView eqn=child[i].findViewById(R.id.eq_name);
            eqn.setText(eq_names[i]);
        }*/
       /* if(MyService.thread.supports_equalizer){
            equalizerInit();
            //////////////////////////////////
            equalizerSwitch.setChecked(MyService.thread.settings.equalizer_enabled);
        }*/

        ////////////////////////////////
        return equalizer_fragment_view;
    }

    @Override
    public  void onResume(){
        super.onResume();
        //////////////////////////////////
      /*  if(MyService.thread.settings.presetPosition>0){
            Equalizer.Settings settings=MyService.thread.equalizer.getProperties();
            for(int i=0;i<5;i++){
                seekbars[i].setValue(((level1[1]-level1[0])/2)+settings.bandLevels[i]);
            }

        }else{
            for(int i=0;i<5;i++){
                seekbars[i].setValue(MyService.thread.settings.customPreset[i]);
            }
        }*/
        /////////////////////////////////




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /* if(position==0){
                for(int i=0;i<5;i++){
                    seekbars[i].setValue(MyService.thread.settings.customPreset[i]);
                }
            }else{
                MyService.thread.equalizer.usePreset((short) (position-1));
                Equalizer.Settings settings=MyService.thread.equalizer.getProperties();

                for(int i=0;i<5;i++){
                    seekbars[i].setValue(((level1[1]-level1[0])/2)+settings.bandLevels[i]);
                }
            }
            MyService.thread.settings.presetPosition=position;*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}