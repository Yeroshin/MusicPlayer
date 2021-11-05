package com.ys.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ys.musicplayer.di.App;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.MainView{
    FragmentsCollectionAdapter fragmentsCollectionAdapter;
    ViewPager2 viewPager;
    @Inject
    MainContract.MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.get(this).getInjector().inject(this);
        mainPresenter.onAttachView(this);
        mainPresenter.onClickPlay();
        ////////////////////////////
        fragmentsCollectionAdapter = new FragmentsCollectionAdapter(this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(fragmentsCollectionAdapter);



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
    public void setArtist(String text) {
      //  ((TextView)findViewById(R.id.text)).setText("text changed YEA!");
    }
    public class FragmentsCollectionAdapter extends FragmentStateAdapter {
        public FragmentsCollectionAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }


        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new PlaylistFragment();
                case 1:
                    return new EqualizerFragment();

            }
            return new PlaylistFragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}