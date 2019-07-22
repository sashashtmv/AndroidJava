package com.sashashtmv.shoppinglist.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sashashtmv.shoppinglist.R;
import com.sashashtmv.shoppinglist.adapter.ShoppingListAdapter;
import com.sashashtmv.shoppinglist.model.ModelPurchase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListFragment extends PurchaseFragment {

    public interface ShoppingListFragmentCallback {
        public FloatingActionButton getFab();
        void onPurchasedPurchases(ModelPurchase modelPurchase);
    }

    private ShoppingListFragmentCallback callback;
    public void onAttach(Activity activity){
        callback = (ShoppingListFragmentCallback) activity;
        super.onAttach(activity);
    }




    public ShoppingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        //View fab = inflater.inflate(R.layout.activity_main, container, false);
        //View fab = getActivity().getLayoutInflater().inflate(R.layout.activity_main, null);
        mRecyclerView = view.findViewById(R.id.rvShoppingList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mFloatingActionButton = fab.findViewById(R.id.float_button);

        mAdapter = new ShoppingListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mFloatingActionButton = callback.getFab();
        return view;
    }

    public void choosePurchase(){
        //final List <ModelPurchase> list = mAdapter.getItemList();

        //Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.coordinator), R.string.purchased_purchases, Snackbar.LENGTH_INDEFINITE);

                for (int i = 0; i < mRecyclerView.getChildCount(); i++){
                    final RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
                    holder.itemView.findViewById(R.id.cb_check_box).setVisibility(View.VISIBLE);
                }
//        snackbar.setAction(R.string.Ok, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        snackbar.setAction(R.string.select_all, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        snackbar.show();
        // Create the Snackbar
        //mFloatingActionButton.hide();
        LinearLayout.LayoutParams objLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.coordinator), R.string.purchased_purchases, Snackbar.LENGTH_INDEFINITE);

        // Get the Snackbar layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        // Set snackbar layout params
        int navbarHeight = getNavBarHeight(getActivity());
        CoordinatorLayout.LayoutParams parentParams = (CoordinatorLayout.LayoutParams) layout.getLayoutParams();
        parentParams.setMargins(0, 0, 0, 0 - navbarHeight + 120);
        layout.setLayoutParams(parentParams);
        layout.setPadding(0, 0, 0, 0);
        layout.setLayoutParams(parentParams);

        // Inflate our custom view
        View snackView = getActivity().getLayoutInflater().inflate(R.layout.my_snackbar, null);

        // Configure our custom view
        TextView messageTextView = (TextView) snackView.findViewById(R.id.message_text_view);
        messageTextView.setText(R.string.purchased_purchases);

        TextView textViewOne = (TextView) snackView.findViewById(R.id.first_text_view);
        textViewOne.setText(R.string.select_all);
        textViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Allow", "showTwoButtonSnackbar() : allow clicked");
                for (int i = 0; i < mRecyclerView.getChildCount(); i++){
                    final RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
                    CheckBox checkBox = holder.itemView.findViewById(R.id.cb_check_box);
                    if(!checkBox.isChecked()) {
                        checkBox.setChecked(true);
                    }
                }
                //mFloatingActionButton.show();
                //snackbar.dismiss();
            }
        });

        TextView textViewTwo = (TextView) snackView.findViewById(R.id.second_text_view);
        textViewTwo.setText(R.string.Ok);
        textViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mRecyclerView.getChildCount(); i++){
                    final RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
                    holder.itemView.findViewById(R.id.cb_check_box).setVisibility(View.GONE);
                    CheckBox checkBox = holder.itemView.findViewById(R.id.cb_check_box);
                    if(checkBox.isChecked()) {
                        callback.onPurchasedPurchases(mModelPurchase);
                        mAdapter.removeItem(holder.getPosition());

                    }
                }
                Log.d("Deny", "showTwoButtonSnackbar() : deny clicked");
                //mFloatingActionButton.show();
                snackbar.dismiss();
            }
        });

        // Add our custom view to the Snackbar's layout
        layout.addView(snackView, objLayoutParams);

        // Show the Snackbar
        snackbar.show();
    }

    public static int getNavBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
