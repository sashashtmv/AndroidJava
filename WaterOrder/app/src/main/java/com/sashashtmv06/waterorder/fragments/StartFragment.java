package com.sashashtmv06.waterorder.fragments;


import android.app.Activity;
import android.app.FragmentManager;
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
import android.widget.RadioButton;
import android.widget.TextView;

import com.sashashtmv06.waterorder.R;
import com.sashashtmv06.waterorder.model.Order;

public class StartFragment extends Fragment {
    private Button mMinusWater1;
    private Button mPlusWater1;
    private TextView mCountWater1;
    private Button mMinusWater2;
    private Button mPlusWater2;
    private TextView mCountWater2;
    private Button mMinusWater3;
    private Button mPlusWater3;
    private TextView mCountWater3;
    private TextView mCountWater;
    private RadioButton mTaraReturn;
    private RadioButton mTaraBuy;
    private TextView mSum;

    private Button mMinusPompa;
    private Button mPlusPompa;
    private TextView mCountPompa;

    private Button mNext;

    private int total;
    private int count1;
    private int count2;
    private int count3;
    private int tara;
    private int pompa;

    private int priceWater1 = 45;
    private int priceWater2 = 50;
    private int priceWater3 = 55;
    private int pricePompa = 120;
    private int priceTara = 160;

    FragmentManager mFragmentManager;
    Toolbar toolbar;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    InformationFragment informationFragment;
    private Order mOrder;
    private Callbacks mCallbacks;
    private ImageView mBrend;

    public StartFragment() {

    }

    public interface Callbacks{
        void onCreatOrder(Order order);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }
    public static StartFragment newInstance() {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, fragment.total);
        args.putInt(ARG_PARAM2, fragment.pompa);
        args.putInt(ARG_PARAM3, fragment.tara);
        args.putInt(ARG_PARAM4, fragment.count1);
        args.putInt(ARG_PARAM5, fragment.count2);
        args.putInt(ARG_PARAM6, fragment.count3);
        fragment.setArguments(args);

        return fragment;
    }

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            total = getArguments().getInt(ARG_PARAM1);
            pompa = getArguments().getInt(ARG_PARAM2);
            tara = getArguments().getInt(ARG_PARAM3);
            count1 = getArguments().getInt(ARG_PARAM4);
            count2 = getArguments().getInt(ARG_PARAM5);
            count3 = getArguments().getInt(ARG_PARAM6);

        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        setHasOptionsMenu(true);

        toolbar = view.findViewById(R.id.toolbar_fragment);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.call_toolbar);
        activity.getSupportActionBar().setTitle("");

        mFragmentManager = activity.getFragmentManager();

        mMinusWater1 = view.findViewById(R.id.bt_minus1);
        mPlusWater1 = view.findViewById(R.id.bt_plus1);
        mCountWater1 = view.findViewById(R.id.count1);
        mMinusWater2 = view.findViewById(R.id.bt_minus2);
        mPlusWater2 = view.findViewById(R.id.bt_plus2);
        mCountWater2 = view.findViewById(R.id.count2);
        mMinusWater3 = view.findViewById(R.id.bt_minus3);
        mPlusWater3 = view.findViewById(R.id.bt_plus3);
        mCountWater3 = view.findViewById(R.id.count3);
        mCountWater = view.findViewById(R.id.tv_count_water);
        mTaraBuy = view.findViewById(R.id.rb_buy_tara);
        mTaraReturn = view.findViewById(R.id.rb_tara_return);
        mSum = view.findViewById(R.id.tv_count_sum);
        mNext = view.findViewById(R.id.bt_next);

        mMinusPompa = view.findViewById(R.id.bt_minus_pompa);
        mPlusPompa = view.findViewById(R.id.bt_plus_pompa);
        mCountPompa = view.findViewById(R.id.tv_count_pompa);
        mBrend = view.findViewById(R.id.iv_brand);

        mBrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aquacity.zp.ua"));
                startActivity(intent);
            }
        });


        mMinusPompa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pompa > 0){
                    pompa--;
                    mCountPompa.setText("" + pompa);
                    updateUI();
                }
            }
        });

        mPlusPompa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pompa++;
                mCountPompa.setText("" + pompa);
                updateUI();
            }
        });

        mMinusWater1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count1 = 0;
                if(mCountWater1.getText().length() > 0) {
                    count1 = Integer.parseInt(mCountWater1.getText().toString());
                }
                if (count1 > 0) {
                    count1--;
                    total--;
                    updateUI();
                }
            }
        });

        mPlusWater1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count1 = 0;
                if(mCountWater1.getText().length() > 0) {
                    count1 = Integer.parseInt(mCountWater1.getText().toString());
                }
                count1++;
                total++;
                updateUI();
            }
        });

        mMinusWater2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count2 = 0;
                if(mCountWater2.getText().length() > 0) {
                    count2 = Integer.parseInt(mCountWater2.getText().toString());
                }
                if (count2 > 0) {
                    count2--;
                    total--;
                    updateUI();
                }
            }
        });

        mPlusWater2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count2 = 0;
                if(mCountWater2.getText().length() > 0) {
                    count2 = Integer.parseInt(mCountWater2.getText().toString());
                }
                count2++;
                total++;
                updateUI();
            }
        });

        mMinusWater3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count3 = 0;
                if(mCountWater3.getText().length() > 0) {
                    count3 = Integer.parseInt(mCountWater3.getText().toString());
                }
                if (count3 > 0) {
                    count3--;
                    total--;
                    updateUI();
                }
            }
        });

        mPlusWater3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count3 = 0;
                if(mCountWater3.getText().length() > 0) {
                    count3 = Integer.parseInt(mCountWater3.getText().toString());
                }
                count3++;
                total++;
                updateUI();
            }
        });

        mTaraReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mTaraBuy.setChecked(false);
                    updateUI();
            }
        });
        mTaraBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mTaraReturn.setChecked(false);
                    updateUI();
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum = 0;
                if(mTaraReturn.isChecked()) {
                    sum = count1 * priceWater1+ count2 * priceWater2 + count3 * priceWater3 + pompa * pricePompa;
                }else sum = count1 * priceWater1+ count2 * priceWater2 + count3 * priceWater3 + total * priceTara + pompa * pricePompa;
                mOrder = new Order(""+count1, "" + count2, "" + count3,
                        "" + pompa, "" + tara, "" + sum);
                mCallbacks.onCreatOrder(mOrder);

                informationFragment = InformationFragment.newInstance();
                mFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, informationFragment, "information")
                        .addToBackStack(null)
                        .commit();

            }
        });

        return view;
    }
    public void updateUI(){
        mCountPompa.setText("" + pompa);
        if(mTaraReturn.isChecked()) {
            mSum.setText("Итого: " + (count1 * priceWater1 + count2 * priceWater2 + count3 * priceWater3 +
                    pompa * pricePompa) + " грн");
            mSum.setTextColor(getResources().getColor(R.color.Black));
            mSum.setTextSize(16);
        }else {
            mSum.setText("Итого: " + (count1 * priceWater1 + count2 * priceWater2 + count3 * priceWater3 +
                    total * priceTara + pompa * pricePompa) + " грн");
        }
        mCountWater.setText("Количество воды - " + total + " бутыля");
        mCountWater.setElegantTextHeight(true);
        mCountWater3.setText("" + count3);
        mCountWater2.setText("" + count2);
        mCountWater1.setText("" + count1);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();


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
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:066-070-1000"));
            startActivity(intent);
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
