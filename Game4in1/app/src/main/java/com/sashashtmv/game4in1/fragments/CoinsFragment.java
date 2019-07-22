package com.sashashtmv.game4in1.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sashashtmv.game4in1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoinsFragment extends Fragment {
    private Toolbar toolbar;
    private int coins;

    public static CoinsFragment newInstance() {
        CoinsFragment fragment = new CoinsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, fragment.mDate);
//        args.putString(ARG_PARAM2, fragment.mTime);
        fragment.setArguments(args);
        return fragment;
    }


    public CoinsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coins, container, false);

        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbar_coins); //находишь тулбар
        //toolbar.setNavigationIcon(R.drawable.ic_launcher_background); //помещаешь иконку слева
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        activity.getSupportActionBar().setTitle("");
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        Intent intent;
//        if (id == R.id.icon_call) {
//            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aquacity.zp.ua"));
//            startActivity(intent);
//            return true;
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void updateCoins(int countCoins) {
        coins = countCoins;
    }
}
