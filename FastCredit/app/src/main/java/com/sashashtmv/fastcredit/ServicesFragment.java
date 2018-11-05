package com.sashashtmv.fastcredit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sashashtmv.fastcredit.cards.CardListActivity;
import com.sashashtmv.fastcredit.credits.CalculatorActivity;
import com.sashashtmv.fastcredit.credits.CreditListActivity;
import com.sashashtmv.fastcredit.loans.LoanListActivity;
import com.sashashtmv.fastcredit.other.OtherListActivity;


public class ServicesFragment extends Fragment {
    private Button bCredits;
    private Button bLoans;
    private Button bCards;
    private Button bCalculator;
    private Button bOther;

    public static ServicesFragment newInstance() {
        ServicesFragment fragment = new ServicesFragment();
        return fragment;
    }

    //private OnFragmentInteractionListener mListener;

    public ServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_services, container, false);
        bCredits = v.findViewById(R.id.bCredits);
        bLoans = v.findViewById(R.id.bLoans);
        bCards = v.findViewById(R.id.bCards);
        bCalculator = v.findViewById(R.id.bCalculator);
        bOther = v.findViewById(R.id.bAnother);

        bCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CreditListActivity.newIntent(getActivity());//запуск активности из фрагмента
                startActivity(intent);
            }
        });
        bLoans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoanListActivity.newIntent(getActivity());//запуск активности из фрагмента
                startActivity(intent);
            }
        });
        bCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CardListActivity.newIntent(getActivity());//запуск активности из фрагмента
                startActivity(intent);
            }
        });
        bCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CalculatorActivity.newIntent(getActivity());//запуск активности из фрагмента
                startActivity(intent);
            }
        });

        bOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OtherListActivity.newIntent(getActivity());//запуск активности из фрагмента
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }


//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
