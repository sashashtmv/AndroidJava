package com.sashashtmv.game4in1.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.model.ModelLevel;
import com.sashashtmv.game4in1.model.PreferenceHelper;
import com.sashashtmv.game4in1.utilities.AdsUtilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {


    private ModelLevel mModelLevel;
    private TextView mWord;
    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    private ImageView mImage4;
    private Button mContinues;
    private PreferenceHelper mPreferenceHelper;
    private int countCoins;


    public ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, fragment.mDate);
//        args.putString(ARG_PARAM2, fragment.mTime);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().hide();
        mWord = view.findViewById(R.id.tv_word);
        mImage1 = view.findViewById(R.id.image1);
        mImage2 = view.findViewById(R.id.image2);
        mImage3 = view.findViewById(R.id.image3);
        mImage4 = view.findViewById(R.id.image4);
        mContinues = view.findViewById(R.id.bt_continues);

        PreferenceHelper.getInstance().init(getActivity());
        mPreferenceHelper = PreferenceHelper.getInstance();
        countCoins = mPreferenceHelper.getInt("gold");

        mWord.setText(mModelLevel.getWord().toUpperCase());
        mImage1.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), getActivity().getResources().getIdentifier(mModelLevel.getBitmap1(), "drawable", getActivity().getPackageName())));
        mImage2.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), getActivity().getResources().getIdentifier(mModelLevel.getBitmap2(), "drawable", getActivity().getPackageName())));
        mImage3.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), getActivity().getResources().getIdentifier(mModelLevel.getBitmap3(), "drawable", getActivity().getPackageName())));
        mImage4.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), getActivity().getResources().getIdentifier(mModelLevel.getBitmap4(), "drawable", getActivity().getPackageName())));

        AdsUtilities.getInstance(getActivity()).loadFullScreenAd(getActivity());

        mContinues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                countCoins += 50;
//                mPreferenceHelper.putInt("gold", countCoins);
                AdsUtilities.getInstance(getActivity()).showFullScreenAd();

                FragmentManager fragmentManager = getActivity().getFragmentManager();
                Fragment fragment = getFragmentManager().findFragmentByTag("start fragment");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    public void updateResult(ModelLevel item) {
        mModelLevel = item;
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
