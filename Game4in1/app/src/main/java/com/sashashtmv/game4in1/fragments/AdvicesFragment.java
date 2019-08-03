package com.sashashtmv.game4in1.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sashashtmv.game4in1.MainActivity;
import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.model.PreferenceHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvicesFragment extends android.app.Fragment {
    private RelativeLayout mOneLetter;
    private RelativeLayout mAllWord;
    private int countCoins;
    private PreferenceHelper mPreferenceHelper;
    private LevelFragment mLevelFragment;
    private MainActivity mMainActivity;

    public static AdvicesFragment newInstance() {
        AdvicesFragment fragment = new AdvicesFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, fragment.mDate);
//        args.putString(ARG_PARAM2, fragment.mTime);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            mMainActivity = (MainActivity) getActivity();
        }
    }



    public AdvicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advices, container, false);
        mOneLetter = view.findViewById(R.id.rl_show_letter);
        mAllWord = view.findViewById(R.id.rl_full_solve);

        mLevelFragment = (LevelFragment) getFragmentManager().findFragmentByTag("level");
        PreferenceHelper.getInstance().init(getActivity());
        mPreferenceHelper = PreferenceHelper.getInstance();
        countCoins = mPreferenceHelper.getInt("gold");

        mOneLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countCoins > 90) {
                    mPreferenceHelper.putInt("gold", countCoins - 90);
                    mLevelFragment.showLetter();
                }
                //mLevelFragment.showLetter();
                getActivity().onBackPressed();
            }
        });

        mAllWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countCoins > 390) {
                    mPreferenceHelper.putInt("gold", countCoins - 390);
                    mMainActivity.onCreatResult(mLevelFragment.getItem());
                }
                getActivity().onBackPressed();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
