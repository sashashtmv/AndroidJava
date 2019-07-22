package com.sashashtmv.waterorder.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sashashtmv.waterorder.PreferenceHelper;
import com.sashashtmv.waterorder.R;
import com.sashashtmv.waterorder.model.Customer;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment {
    private Customer mCustomer;
    private EditText mName;
    private EditText mStreet;
    private EditText mNumberHouse;
    private EditText mCompanyName;
    private EditText mTelephone;
    private EditText mFlatNumber;
    private Button mButton;
    private Callbacks mCallbacks;

    private String name;
    private String companyName;
    private String telephone;
    private String street;
    private String house;
    private String flatNumber;
    private PreferenceHelper mPreferenceHelper;
    private ImageView mBrend;


    public DataFragment() {
        // Required empty public constructor
    }

    public interface Callbacks{
        void onCreatCustomer(Customer customer);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        setHasOptionsMenu(true);

        Toolbar toolbar = view.findViewById(R.id.toolbar_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        PreferenceHelper.getInstance().init(getActivity());
        mPreferenceHelper = PreferenceHelper.getInstance();

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        activity.getSupportActionBar().setTitle("");

        mName = view.findViewById(R.id.et_name);
        mStreet = view.findViewById(R.id.et_street);
        mNumberHouse = view.findViewById(R.id.et_house);
        mFlatNumber = view.findViewById(R.id.et_flat_number);
        mCompanyName = view.findViewById(R.id.et_name_company);
        mTelephone = view.findViewById(R.id.et_telephone);
        mButton = view.findViewById(R.id.bt_order);
        mBrend = view.findViewById(R.id.iv_brand);

        mBrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aquacity.zp.ua"));
                startActivity(intent);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = mName.getText().toString();
                companyName = mCompanyName.getText().toString();
                telephone = mTelephone.getText().toString();
                street = mStreet.getText().toString();
                house = mNumberHouse.getText().toString();
                flatNumber = mFlatNumber.getText().toString();
                if(name.length() > 0 && telephone.length() > 0 && street.length() > 0 && house.length() > 0){
                    mCustomer = new Customer(name, companyName, telephone,street, house, flatNumber);
                    mPreferenceHelper.putString("name", name);
                    mPreferenceHelper.putString("companyName", companyName);
                    mPreferenceHelper.putString("telephone", telephone);
                    mPreferenceHelper.putString("street", street);
                    mPreferenceHelper.putString("house", house);
                    mPreferenceHelper.putString("flatNumber", flatNumber);

                    //Toast.makeText(getContext(), name + " " + lastName, Toast.LENGTH_LONG).show();
                    mCallbacks.onCreatCustomer(mCustomer);
                    getActivity().onBackPressed();

                }else {
                    Toast.makeText(getActivity(), R.string.toast , Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent intent;
        if (id == R.id.icon_call) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aquacity.zp.ua"));
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
}
