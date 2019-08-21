package com.sashashtmv.taskmanager.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.sashashtmv.taskmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    Toolbar toolbar;

    public StartFragment() {
        // Required empty public constructor
    }
    public static StartFragment newInstance() {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_start, container, false);
        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbar_fragment); //находишь тулбар
        //toolbar.setNavigationIcon(R.drawable.ic_launcher_background); //помещаешь иконку слева
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");

        // Inflate the layout for this fragment
        return view;
    }

}
