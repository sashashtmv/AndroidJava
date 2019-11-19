package com.sashashtmv.applicationforbuilders.fragments;


import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sashashtmv.applicationforbuilders.R;


public class StartFragment extends Fragment {
    Toolbar toolbar;
    private Callbacks mCallbacks;
    private Button mEnter;
    private Button mAboutUs;
    private Button mNewUser;
    private Button mRule;


    public StartFragment() {
        // Required empty public constructor
    }

    public interface Callbacks{
        void onCreatStartScreen();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //mCallbacks = (Callbacks)activity;
    }

    public static StartFragment newInstance() {
        StartFragment fragment = new StartFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        setHasOptionsMenu(true);

        toolbar = view.findViewById(R.id.toolbar_fragment);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);

        //activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        //activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.call_toolbar);
        activity.getSupportActionBar().setTitle(getString(R.string.app_for_builders));
        return view;
    }

}
