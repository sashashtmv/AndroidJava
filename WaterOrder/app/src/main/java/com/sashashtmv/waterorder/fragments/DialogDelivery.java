package com.sashashtmv.waterorder.fragments;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.sashashtmv.waterorder.R;


public class DialogDelivery extends DialogFragment implements OnClickListener{
    FragmentManager mFragmentManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, null);
        getDialog().setTitle(R.string.make_order);
        view.findViewById(R.id.bt_address_of_order).setOnClickListener(this);
        view.findViewById(R.id.bt_number_of_customer).setOnClickListener(this);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        mFragmentManager = activity.getFragmentManager();
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == view.findViewById(R.id.bt_address_of_order)){
            DataFragment dataFragment = new DataFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, dataFragment)
                    .addToBackStack(null)
                    .commit();
            dismiss();
        }
        if(view == view.findViewById(R.id.bt_number_of_customer)){
            PersonalFragment personalFragment = new PersonalFragment();
            mFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, personalFragment)
                    .addToBackStack(null)
                    .commit();
            dismiss();
        }
    }
}
