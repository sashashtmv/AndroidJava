package com.sashashtmv.game4in1.fragments;


import android.content.Intent;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sashashtmv.game4in1.R;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskFreandsFragment extends Fragment {

    private LinearLayout mViber;
    private LinearLayout mWhatsapp;
    private LinearLayout mTelegram;
    private LinearLayout mFacebookMessenger;


    public AskFreandsFragment() {
        // Required empty public constructor
    }

    public static AskFreandsFragment newInstance() {
        AskFreandsFragment fragment = new AskFreandsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, fragment.mDate);
//        args.putString(ARG_PARAM2, fragment.mTime);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask_freands, container, false);

        mViber = view.findViewById(R.id.ll_viber);
        mWhatsapp = view.findViewById(R.id.ll_whats_app);
        mTelegram = view.findViewById(R.id.ll_telegram);
        mFacebookMessenger = view.findViewById(R.id.ll_facebook_messenger);

        mViber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.setPackage("com.viber.voip");

                startActivity(Intent.createChooser(intent,
                        "Send message Using: "));
                getActivity().onBackPressed();
            }
        });
        mWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.setPackage("com.whatsapp");

                startActivity(Intent.createChooser(intent,
                        "Send message Using: "));
                getActivity().onBackPressed();

            }
        });
        mTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.setPackage("org.telegram.messenger");

                startActivity(Intent.createChooser(intent,
                        "Send message Using: "));
                getActivity().onBackPressed();
            }
        });
        mFacebookMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Привет");
                intent.setType("text/plain");
                intent.setPackage("com.facebook.orca");

                startActivity(Intent.createChooser(intent,
                        "Send message Using: hgjhj"));
                getActivity().onBackPressed();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


}
