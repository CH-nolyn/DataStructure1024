package com.qxy.DataStructure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RankActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private Fragment[] fragments;
    private int lastFragmentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        initView();
    }

    protected void initView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.rank_buttom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        fragments =new Fragment[]{
                new MovieFragment(),
                new VarietyFragment(),
                new TvFragment()
        };
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rank_frame,fragments[0])
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.buttom_movie:
                switchFragment(0);
                break;
            case R.id.buttom_variety :
                switchFragment(1);
                break;
            case R.id.buttom_tv:
                switchFragment(2);
                break;
        }
        return false;
    }

    private void switchFragment(int to) {
        if (lastFragmentIndex == to) {
            return;
        }
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        if (!fragments[to].isAdded()) {
            fragmentTransaction.add(R.id.rank_frame, fragments[to]);
        } else {
            fragmentTransaction.show(fragments[to]);
        }
        fragmentTransaction.hide(fragments[lastFragmentIndex])
                .commitAllowingStateLoss();
        lastFragmentIndex = to;
    }
}
