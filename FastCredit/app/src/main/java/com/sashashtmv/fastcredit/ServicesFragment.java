package com.sashashtmv.fastcredit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sashashtmv.fastcredit.cards.CardLab;
import com.sashashtmv.fastcredit.cards.CardListActivity;
import com.sashashtmv.fastcredit.credits.BankLab;
import com.sashashtmv.fastcredit.credits.CalculatorActivity;
import com.sashashtmv.fastcredit.credits.CreditListActivity;
import com.sashashtmv.fastcredit.loans.LoanListActivity;
import com.sashashtmv.fastcredit.loans.Loaner;
import com.sashashtmv.fastcredit.loans.LoanerLab;
import com.sashashtmv.fastcredit.other.Other;
import com.sashashtmv.fastcredit.other.OtherLab;
import com.sashashtmv.fastcredit.other.OtherListActivity;


public class ServicesFragment extends Fragment {
    private Button bCredits;
    private Button bLoans;
    private Button bCards;
    private Button bCalculator;
    private Button bOther;

    private CardLab mCardLab = CardLab.get();
    private BankLab mBankLab = BankLab.get();
    private LoanerLab mLoaner = LoanerLab.get();
    private OtherLab mOther = OtherLab.get();

    public static ServicesFragment newInstance() {
        ServicesFragment fragment = new ServicesFragment();
        return fragment;
    }

    public ServicesFragment() {
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
                if(mBankLab.getBanks() != null) {
                    Intent intent = CreditListActivity.newIntent(getActivity());//запуск активности из фрагмента
                    startActivity(intent);
                }
            }
        });
        bLoans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLoaner.getLoans()!= null) {
                    Intent intent = LoanListActivity.newIntent(getActivity());//запуск активности из фрагмента
                    startActivity(intent);
                }
            }
        });
        bCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCardLab.getCards() != null) {
                    Intent intent = CardListActivity.newIntent(getActivity());//запуск активности из фрагмента
                    startActivity(intent);
                }
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
                if(mOther.getOthers() != null) {
                Intent intent = OtherListActivity.newIntent(getActivity());//запуск активности из фрагмента
                startActivity(intent);
                }
            }
        });

        return v;
    }



}
