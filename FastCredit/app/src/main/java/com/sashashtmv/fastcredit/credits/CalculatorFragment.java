package com.sashashtmv.fastcredit.credits;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sashashtmv.fastcredit.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorFragment extends Fragment {

    private EditText etCredit_amount;
    private EditText etNumber_of_months;
    private Button bClick;
    private Button bReset;
    private TextView etMonthly_payment;
    private TextView etTotal_sum;
    private EditText etInterest_rate;

    public static CalculatorFragment newInstance() {
        CalculatorFragment fragment = new CalculatorFragment();
        return fragment;
    }


    public CalculatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculator, container, false);

        etCredit_amount = v.findViewById(R.id.etCredit_amount);
        etNumber_of_months = v.findViewById(R.id.etNumber_of_months);
        bClick = v.findViewById(R.id.bClick);
        bReset = v.findViewById(R.id.bReset);
        etMonthly_payment = v.findViewById(R.id.etMonthly_payment);
        etInterest_rate = v.findViewById(R.id.etInterest_rate);
        etTotal_sum = v.findViewById(R.id.etTotal_sum);


        bClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = 0;
                int month = 1;
                double rate = 0;
                try {
                    sum = Integer.parseInt(etCredit_amount.getText().toString());
                    month = Integer.parseInt(etNumber_of_months.getText().toString());
                    rate = Double.parseDouble(etInterest_rate.getText().toString());
                } catch (Exception e) {
                }

                double result = sum / month + (sum * rate / 12 / 100);
                etMonthly_payment.setText(Math.round(result * 100.0) / 100.0 + "");
                etTotal_sum.setText(Math.round((sum * (rate / 100 +1))*100.0) / 100.0 + "");
                etInterest_rate.setText(rate + "%");

            }
        });

        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etMonthly_payment.setText("");
                etInterest_rate.setText("");
                etCredit_amount.setText("");
                etNumber_of_months.setText("");
                etTotal_sum.setText("");
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
