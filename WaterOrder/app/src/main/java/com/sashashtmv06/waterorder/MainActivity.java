package com.sashashtmv06.waterorder;

import android.app.FragmentManager;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sashashtmv06.waterorder.fragments.DataFragment;
import com.sashashtmv06.waterorder.fragments.InformationFragment;
import com.sashashtmv06.waterorder.fragments.PersonalFragment;
import com.sashashtmv06.waterorder.fragments.StartFragment;
import com.sashashtmv06.waterorder.model.Customer;
import com.sashashtmv06.waterorder.model.Order;


public class MainActivity extends AppCompatActivity implements PersonalFragment.Callbacks, DataFragment.Callbacks, StartFragment.Callbacks {

    FragmentManager mFragmentManager;
    StartFragment startFragment;
    Customer mCustomer;
    Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mFragmentManager = getFragmentManager();
        startFragment = StartFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, startFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreatCustomerNumber(Customer customer) {
        Fragment information = mFragmentManager.findFragmentByTag("information");
        mCustomer = customer;
        if(information != null) {
            //Toast.makeText(this, customer.getTelephoneNumber(), Toast.LENGTH_LONG).show();
            ((InformationFragment) information).updateUI(customer);
            ((InformationFragment) information).updateOrder(mOrder);
        }
    }

    @Override
    public void onCreatCustomer(Customer customer) {
        Fragment information = mFragmentManager.findFragmentByTag("information");
        if(information != null) {
            //Toast.makeText(this, number, Toast.LENGTH_LONG).show();
            ((InformationFragment) information).updateUI(customer);
            ((InformationFragment) information).updateOrder(mOrder);
        }
    }

    @Override
    public void onCreatOrder(Order order) {
        mOrder = order;
    }
}
