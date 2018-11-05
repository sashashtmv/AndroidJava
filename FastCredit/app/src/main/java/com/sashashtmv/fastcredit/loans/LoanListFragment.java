package com.sashashtmv.fastcredit.loans;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sashashtmv.fastcredit.R;
import com.sashashtmv.fastcredit.credits.BankLab;

import java.util.List;

public class LoanListFragment extends Fragment {
    private RecyclerView mLoansRecyclerView;
    private LoanerAdapter mAdapter;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;


//    public CreditListFragment() {
//    }

//    public static CreditListFragment newInstance() {
//        CreditListFragment fragment = new CreditListFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list_loan, container, false);

        mLoansRecyclerView = (RecyclerView) view.findViewById(R.id.list_loans);
        mLoansRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    private void updateUI() {
        LoanerLab loanerLab = LoanerLab.get(getActivity());
        List<Loaner> crimes = loanerLab.getLoans();
        mAdapter = new LoanerAdapter(crimes);
        mLoansRecyclerView.setAdapter(mAdapter);
    }

    private class LoanerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Loaner mLoaner;
        private ImageView mIcon;
        private TextView mApply;
        private TextView mTitle;
        private TextView mDescription;

        public LoanerHolder(View itemView) {
            super(itemView);
//            mIcon = itemView.findViewById(R.id.icon_bank);
//            mSum = itemView.findViewById(R.id.twSum);
//            mRate = itemView.findViewById(R.id.twRate);
//            mTerm = itemView.findViewById(R.id.twTerm);
            mTitle = itemView.findViewById(R.id.twTitle);
            mDescription = itemView.findViewById(R.id.twDescription);

            itemView.setOnClickListener(this);
        }

        public void bindLoaner(Loaner loaner) {
            mLoaner = loaner;
            mDescription.setText(mLoaner.getDescription());
            mTitle.setText(mLoaner.getTitle());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Uri address = LoanerLab.get(getActivity()).getLoans().get(position).getAdress();
            Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
            startActivity(openlinkIntent);
            //TODO
//            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());//запуск активности из фрагмента
//            startActivity(intent);
        }

    }

    private class LoanerAdapter extends RecyclerView.Adapter<LoanerHolder> {
        private List<Loaner> mLoaners;

        public LoanerAdapter(List<Loaner> loaners) {
            mLoaners = loaners;
        }

        @NonNull
        @Override
        public LoanerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_item_loan, parent, false);
            return new LoanerHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LoanerHolder holder, int position) {
            Loaner loaner = mLoaners.get(position);
            holder.bindLoaner(loaner);
        }

        @Override
        public int getItemCount() {
            return mLoaners.size();
        }
    }


}
