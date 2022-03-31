package com.ys.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ys.musicplayer.adapters.YSFragmentStateAdapter;
import com.ys.musicplayer.di.App;

import com.ys.musicplayer.di.components.DaggerMainActivityComponent;
import com.ys.musicplayer.di.modules.MainActivityModule;
import com.ys.musicplayer.fragments.EqualizerFragment;
import com.ys.musicplayer.fragments.TrackFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    static{
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Inject
    YSFragmentStateAdapter fragmentStateAdapter;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //App.get(this).getInjector().inject(this);
        DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);
        super.onCreate(savedInstanceState);
        ///////////////////////
        permissions();
        ///////////////////////


    }
    void permissions(){
        /////////////////////
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String[] perm = info.requestedPermissions;
        /////////////////////
        ArrayList<String> permissionsDenied=new ArrayList();
        List<String> list = new ArrayList<String>();
//add some stuff
        list.add(Manifest.permission.RECORD_AUDIO);
        //  if(android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT){
        list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //  }
        String[] permissions = list.toArray(new String[0]);
        //  String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO};
        for(int i=0;i<permissions.length;i++){
            if(ContextCompat.checkSelfPermission(this, permissions[i]) == PackageManager.PERMISSION_DENIED){
                permissionsDenied.add(permissions[i]);
                if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) this,permissions[i])){

                }
            }
        }
        if(permissionsDenied.size()>0){
            String[] permissionsArray=  permissionsDenied.toArray(new String[permissionsDenied.size()]);;

            ActivityCompat.requestPermissions((Activity) this, permissionsArray, 1);
        }else{
            init(); //show explain why you need permission
        }
           /* if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                init();

            } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                ActivityCompat.requestPermissions((Activity) context, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                // You can directly ask for the permission.
                ActivityCompat.requestPermissions((Activity) context, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }*/

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int granted = 0;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted++;
            }
        }
        if (granted != grantResults.length) {
            this.finish();
        } else {
            init(); //show explain why you need permission
        }
    }
    @Override
    public void onResume(){
        super.onResume();
    }
    void init(){
        setContentView(R.layout.activity_main);

        ////////////////////////////
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(fragmentStateAdapter);

        TabLayout tabLayout = findViewById(R.id.tabsLayout);
        new TabLayoutMediator(tabLayout, viewPager,

                (tab, position) -> {
                    switch (position){
                        case 0:
                            tab.setText(getString(R.string.playList));
                            break;
                        case 1:
                            tab.setText(getString(R.string.equalizer));
                            break;
                        case 2:
                            tab.setText(getString(R.string.alarm));
                            break;
                    }
                }
        ).attach();


    }


    @Override
    protected void onStop (){
        super.onStop();
    }
}