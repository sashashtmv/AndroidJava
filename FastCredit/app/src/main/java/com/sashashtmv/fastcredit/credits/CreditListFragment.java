package com.sashashtmv.fastcredit.credits;

import android.app.Activity;
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

import java.util.List;
import java.util.UUID;

public class CreditListFragment extends Fragment {
    private RecyclerView mCreditsRecyclerView;
    private BankAdapter mAdapter;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private UUID mUUID;


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
        View view = inflater.inflate(R.layout.fragment_item_list_credit, container, false);

        mCreditsRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mCreditsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    private void updateUI() {
        BankLab bankLab = BankLab.get(getActivity());
        List<Bank> crimes = bankLab.getBanks();
        mAdapter = new BankAdapter(crimes);
        mCreditsRecyclerView.setAdapter(mAdapter);
    }

    private class BankHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Bank mBank;
        private ImageView mIcon;
        private TextView mSum;
        private TextView mRate;
        private TextView mTerm;
        private TextView mValueSum;
        private TextView mValueRate;
        private TextView mValueTerm;

        public BankHolder(View itemView) {
            super(itemView);
//            mIcon = itemView.findViewById(R.id.icon_bank);
//            mSum = itemView.findViewById(R.id.twSum);
//            mRate = itemView.findViewById(R.id.twRate);
//            mTerm = itemView.findViewById(R.id.twTerm);
            mValueSum = itemView.findViewById(R.id.twValue_sum);
            mValueRate = itemView.findViewById(R.id.twValue_rate);
            mValueTerm = itemView.findViewById(R.id.twValue_term);

            itemView.setOnClickListener(this);
        }

        public void bindBank(Bank bank) {
            mBank = bank;
            mValueSum.setText(mBank.getSum());
            mValueRate.setText(mBank.getRate());
            mValueTerm.setText(mBank.getTerm());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Uri address = BankLab.get(getActivity()).getBanks().get(position).getAdress();
            Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
            startActivity(openlinkIntent);
            //TODO
//            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());//запуск активности из фрагмента
//            startActivity(intent);
        }

    }

    private class BankAdapter extends RecyclerView.Adapter<BankHolder> {
        private List<Bank> mBanks;

        public BankAdapter(List<Bank> banks) {
            mBanks = banks;
        }

        @NonNull
        @Override
        public BankHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_item_credit, parent, false);
            return new BankHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BankHolder holder, int position) {
            Bank bank = mBanks.get(position);
            holder.bindBank(bank);
        }

        @Override
        public int getItemCount() {
            return mBanks.size();
        }
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnListFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onListFragmentInteraction(DummyItem item);
//    }
}
