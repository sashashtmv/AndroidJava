package com.sashashtmv.admin.myproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.content.SharedPreferences;



public class SearchFragment extends Fragment {
    private EditText mInput_text;
    private Button mSearch_button;

    private SharedPreferences mSharedPreferencesHelper;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    private int loadId() {
        mSharedPreferencesHelper = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        int id_button = mSharedPreferencesHelper.getInt(SettingsFragment.SAVED_TEXT, 0);
        return id_button;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_serch, container, false);

        mInput_text = view.findViewById(R.id.input_text);
        mSearch_button = view.findViewById(R.id.search_button);

        mSearch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mInput_text.getText())) {
                    if(R.id.google_button==loadId()) {
                        Uri address = Uri.parse("http://google.com/search?q=" + mInput_text.getText());
                        Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
                        startActivity(openlinkIntent);
                    }
                    if(R.id.yandex_button==loadId()) {
                        Uri address = Uri.parse("http://yandex.com/search?text=" + mInput_text.getText());
                        Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
                        startActivity(openlinkIntent);
                    }if(R.id.bing_button==loadId()) {
                        Uri address = Uri.parse("http://bing.com/search?q=" + mInput_text.getText());
                        Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
                        startActivity(openlinkIntent);
                    }

                }

            }
        });
        return view;
    }
}
