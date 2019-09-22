package com.sashashtmv.taskmanager.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.sashashtmv.taskmanager.MainActivity;
import com.sashashtmv.taskmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    Toolbar toolbar;
    private LinearLayout llDays;
    private LinearLayout llWeek;
    private LinearLayout llMonth;
    private LinearLayout llYear;
    private LinearLayout llFifeYears;
    private LinearLayout llRules;
    private MainActivity mMainActivity;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null){
            mMainActivity = (MainActivity)getActivity();
        }
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

        llDays = view.findViewById(R.id.ll_day);
        llWeek = view.findViewById(R.id.ll_week);
        llMonth = view.findViewById(R.id.ll_month);
        llYear = view.findViewById(R.id.ll_year);
        llFifeYears = view.findViewById(R.id.ll_fifeyear);
        llRules = view.findViewById(R.id.ll_rules);

        llDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DayFragment dayFragment = DayFragment.newInstance();
                //StartFragment startFragment = new StartFragment();
                mMainActivity.getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, dayFragment, "day fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        llWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeekFragment weekFragment = WeekFragment.newInstance();
                //StartFragment startFragment = new StartFragment();
                mMainActivity.getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, weekFragment, "week fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        llMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthFragment monthFragment = MonthFragment.newInstance();
                //StartFragment startFragment = new StartFragment();
                mMainActivity.getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, monthFragment, "month fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        llYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YearFragment yearFragment = YearFragment.newInstance();
                //StartFragment startFragment = new StartFragment();
                mMainActivity.getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, yearFragment, "year fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        llFifeYears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FifeYearsFragment fifeYearsFragment = FifeYearsFragment.newInstance();
                //StartFragment startFragment = new StartFragment();
                mMainActivity.getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fifeYearsFragment, "fifeYear fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        llRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RulesFragment rulesFragment = new RulesFragment();
                mMainActivity.getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, rulesFragment, "rules fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
