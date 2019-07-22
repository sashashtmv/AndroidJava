package com.sashashtmv.shoppinglist.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sashashtmv.shoppinglist.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchasedPurchasesFragment extends Fragment {


    public PurchasedPurchasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purchased_purchases, container, false);
    }

}
