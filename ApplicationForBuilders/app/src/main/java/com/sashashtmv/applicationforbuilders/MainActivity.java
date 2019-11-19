package com.sashashtmv.applicationforbuilders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.sashashtmv.applicationforbuilders.fragments.StartFragment;

public class MainActivity extends AppCompatActivity {
    FragmentManager mFragmentManager;
    StartFragment startFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();
        startFragment = StartFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.container, startFragment)
                .addToBackStack(null)
                .commit();
    }
}
