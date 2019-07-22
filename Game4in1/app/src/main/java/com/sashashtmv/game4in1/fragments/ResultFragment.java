package com.sashashtmv.game4in1.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.model.ModelLevel;

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

        mWord.setText(mModelLevel.getWord().toUpperCase());
        mImage1.setImageBitmap(mModelLevel.getBitmap1());
        mImage2.setImageBitmap(mModelLevel.getBitmap2());
        mImage3.setImageBitmap(mModelLevel.getBitmap3());
        mImage4.setImageBitmap(mModelLevel.getBitmap4());
        // Inflate the layout for this fragment
        return view;
    }

    public void updateResult(ModelLevel item) {
        mModelLevel = item;
    }
}
