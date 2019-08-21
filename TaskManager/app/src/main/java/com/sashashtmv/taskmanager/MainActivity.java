package com.sashashtmv.taskmanager;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sashashtmv.taskmanager.fragments.SplashFragment;
import com.sashashtmv.taskmanager.fragments.StartFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getFragmentManager();
        runStartFragment();
        runSplash();

    }

    public void runSplash() {
        SplashFragment splashFragment = SplashFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, splashFragment, "dd")
                .addToBackStack(null)
                .commit();

    }

    public void runStartFragment() {
        StartFragment startFragment = StartFragment.newInstance();
        //StartFragment startFragment = new StartFragment();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, startFragment, "start fragment")
                .addToBackStack(null)
                .commit();

    }
}
