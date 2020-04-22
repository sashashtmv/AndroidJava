package com.sashashtmv06.waterorder.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sashashtmv06.waterorder.PreferenceHelper;
import com.sashashtmv06.waterorder.R;
import com.sashashtmv06.waterorder.model.Customer;


public class PersonalFragment extends Fragment {
    private Customer mCustomer;
    private PreferenceHelper mPreferenceHelper;
    private TextView mAdress;
    private TextView mContact;
    private TextView mTelephone;
    private Button mOk;
    private Callbacks mCallbacks;
    private ImageView mBrend;


    public PersonalFragment() {
        // Required empty public constructor
    }
    public interface Callbacks{
        void onCreatCustomerNumber(Customer customer);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (PersonalFragment.Callbacks)activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        setHasOptionsMenu(true);

        Toolbar toolbar = view.findViewById(R.id.toolbar_fragment);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        activity.getSupportActionBar().setTitle("");

        PreferenceHelper.getInstance().init(getActivity());
        mPreferenceHelper = PreferenceHelper.getInstance();

        mAdress = view.findViewById(R.id.tx_number);
        mContact = view.findViewById(R.id.tx_name_person);
        mTelephone = view.findViewById(R.id.tx_telephone_number);
        mOk = view.findViewById(R.id.bt_order);
        mBrend = view.findViewById(R.id.iv_brand);
        mBrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aquacity.zp.ua"));
                startActivity(intent);
            }
        });

        if(mPreferenceHelper.getString("street").length() > 0){
            mAdress.setText(mPreferenceHelper.getString("street") + ", " + mPreferenceHelper.getString("house"));
        }
        if(mPreferenceHelper.getString("name").length() > 0) {
            mContact.setText(mPreferenceHelper.getString("name"));
        }
        if(mPreferenceHelper.getString("telephone").length() > 0) {
            mTelephone.setText(mPreferenceHelper.getString("telephone"));
        }

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onCreatCustomerNumber(new Customer(mPreferenceHelper.getString("name"),
                        mPreferenceHelper.getString("nameCompany"), mPreferenceHelper.getString("telephone"),
                        mPreferenceHelper.getString("street"), mPreferenceHelper.getString("house"), mPreferenceHelper.getString("flatNumber")));
                //Toast.makeText(getActivity(), mPreferenceHelper.getString("telephone"), Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();
            }
        });

        return view;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent intent;
        if (id == R.id.icon_call) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://oasiswater.com.ua/ru/content/74-oasis-android-app"));
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void updateUI(Customer customer){
        mCustomer = customer;
        //Toast.makeText(getActivity(), "PersonalFragment", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

}
