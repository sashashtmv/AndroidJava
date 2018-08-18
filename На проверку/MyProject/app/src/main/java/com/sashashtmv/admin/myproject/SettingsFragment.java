package com.sashashtmv.admin.myproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.content.SharedPreferences;



public class SettingsFragment extends Fragment {
    private SharedPreferences mSharedPreferencesHelper;
    final static String SAVED_TEXT = "saved_text";

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    private void saveId(int id){
        mSharedPreferencesHelper = getActivity().getSharedPreferences("MyPref",  Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mSharedPreferencesHelper.edit();
        ed.putInt(SAVED_TEXT, id);
        ed.commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

       RadioGroup radioGroup =  view.findViewById(R.id.yourRadioGroup);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.google_button:
                        saveId(checkedId);
                        // switch to fragment 1
                        break;
                    case R.id.yandex_button:
                        saveId(checkedId);
                        // Fragment 2
                        break;
                    case R.id.bing_button:
                        saveId(checkedId);
                        // Fragment 3
                        break;
                }
            }
        });


        return view;
    }
}
